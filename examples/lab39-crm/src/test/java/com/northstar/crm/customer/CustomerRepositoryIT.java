package com.northstar.crm.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.northstar.crm.account.AccountEntity;
import com.northstar.crm.account.AccountRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
class CustomerRepositoryIT {

    @Autowired CustomerRepository repository;
    @Autowired AccountRepository accountRepository;
    @Autowired EntityManager entityManager;
    @Autowired PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @DynamicPropertySource
    static void registerDbProps(DynamicPropertyRegistry registry) {
        String username = System.getenv().getOrDefault("CRM_DB_USERNAME", "crm_app");
        String password = System.getenv().getOrDefault("CRM_DB_PASSWORD", "change-me");

        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/crm");
        registry.add("spring.datasource.username", () -> username);
        registry.add("spring.datasource.password", () -> password);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @BeforeEach
    void cleanDatabase() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        accountRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    void saveAndFindByPublicId() {
        CustomerEntity customer = new CustomerEntity();
        customer.setPublicId("CUS-1001");
        customer.setFullName("Amina Khan");
        customer.setEmail("amina@example.com");
        customer.setStatus("ACTIVE");

        CustomerEntity saved = repository.saveAndFlush(customer);

        assertThat(saved.getCustomerId()).isNotNull();
        assertThat(repository.findByPublicId("CUS-1001"))
                .isPresent()
                .get()
                .extracting(CustomerEntity::getEmail)
                .isEqualTo("amina@example.com");
    }

    @Test
    void duplicateEmailFails() {
        CustomerEntity first = new CustomerEntity();
        first.setPublicId("CUS-1001");
        first.setFullName("Amina Khan");
        first.setEmail("duplicate@example.com");
        first.setStatus("ACTIVE");
        repository.saveAndFlush(first);

        CustomerEntity second = new CustomerEntity();
        second.setPublicId("CUS-1002");
        second.setFullName("Ravi Singh");
        second.setEmail("duplicate@example.com");
        second.setStatus("PROSPECT");

        assertThatThrownBy(() -> repository.saveAndFlush(second))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void pagingStableByStatus() {
        repository.saveAndFlush(customer("CUS-1001", "Amina Khan", "ACTIVE", "amina@example.com"));
        repository.saveAndFlush(customer("CUS-1002", "Ravi Singh", "ACTIVE", "ravi@example.com"));
        repository.saveAndFlush(customer("CUS-1003", "Zoe Patel", "PROSPECT", "zoe@example.com"));

        PageRequest request = PageRequest.of(
                0,
                2,
                Sort.by("fullName").ascending().and(Sort.by("customerId").ascending())
        );

        Page<CustomerEntity> page = repository.findByStatus("ACTIVE", request);
        assertThat(page.getTotalElements()).isEqualTo(2L);
        assertThat(page.getContent())
                .extracting(CustomerEntity::getPublicId)
                .containsExactly("CUS-1001", "CUS-1002");
    }

    @Test
    void optimisticLockRaceFails() {
        CustomerEntity saved = transactionTemplate.execute(status -> repository.saveAndFlush(customer("CUS-9001", "Amina Khan", "ACTIVE", "race@example.com")));

        CustomerEntity first = transactionTemplate.execute(status -> repository.findById(saved.getCustomerId()).orElseThrow());
        CustomerEntity second = transactionTemplate.execute(status -> repository.findById(saved.getCustomerId()).orElseThrow());

        transactionTemplate.executeWithoutResult(status -> {
            first.setFullName("First writer");
            repository.saveAndFlush(first);
        });

        second.setFullName("Second writer");

        assertThatThrownBy(() -> transactionTemplate.executeWithoutResult(status -> repository.saveAndFlush(second)))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

    @Test
    @Transactional
    void accountEntityLoadsCustomerWithoutNPlusOne() {
        CustomerEntity customer = customer("CUS-1101", "Amina Khan", "ACTIVE", "account@example.com");
        CustomerEntity savedCustomer = repository.saveAndFlush(customer);

        AccountEntity account = new AccountEntity();
        account.setCustomer(savedCustomer);
        account.setBalanceCents(125050L);
        account.setStatus("ACTIVE");
        AccountEntity savedAccount = accountRepository.saveAndFlush(account);

        AccountEntity loaded = accountRepository.findById(savedAccount.getAccountId()).orElseThrow();
        assertThat(loaded.getBalanceCents()).isEqualTo(125050L);
        assertThat(loaded.getCustomer().getPublicId()).isEqualTo("CUS-1101");
        assertThat(List.of(loaded.getCustomer().getPublicId())).contains("CUS-1101");
    }

    private static CustomerEntity customer(String publicId, String fullName, String status, String email) {
        CustomerEntity customer = new CustomerEntity();
        customer.setPublicId(publicId);
        customer.setFullName(fullName);
        customer.setStatus(status);
        customer.setEmail(email);
        return customer;
    }
}