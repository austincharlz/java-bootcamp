import com.northstar.crm.model.Customer;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import com.northstar.crm.service.CustomerService;
import com.northstar.crm.service.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {
    @Test
    void createAndGetWithoutSpringContext() {
        var repo = new InMemoryCustomerRepository();
        var notify = new NotificationService();
        var service = new CustomerService(repo, notify);

        Customer created = service.create(Customer.amina(), "lab-request-001");
        Assertions.assertEquals("CUS-1001", created.getId());
        Assertions.assertEquals("Amina Khan", service.get("CUS-1001").getName());
    }
}