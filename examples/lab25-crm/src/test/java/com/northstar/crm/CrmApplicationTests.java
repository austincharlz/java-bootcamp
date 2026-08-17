package com.northstar.crm;

import com.northstar.crm.model.Customer;
import com.northstar.crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CrmApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    void contextLoadsAndRestSeedVisible() {
        Customer customer = customerService.get("CUS-1001");

        assertNotNull(customer);
        assertEquals("CUS-1001", customer.getId());
        assertEquals("Amina Khan", customer.getName());
    }

}
