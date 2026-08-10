package com.northstar.crm;

import com.northstar.crm.account.Account;
import com.northstar.crm.account.AccountRepository;
import com.northstar.crm.account.TransactionLogRepository;
import com.northstar.crm.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferServiceTest {
    @Autowired TransferService transferService;
    @Autowired AccountRepository accounts;
    @Autowired TransactionLogRepository logs;

    @BeforeEach
    void resetData() {
        accounts.save(new Account("ACC-MAIN-1001", "CUS-1001", "MAIN", new BigDecimal("1000.00")));
        accounts.save(new Account("ACC-LOYALTY-1001", "CUS-1001", "LOYALTY", new BigDecimal("50.00")));
        logs.deleteAll();
    }

    @Test
    void forceFailRollsBack() {
        BigDecimal before = accounts.findById("ACC-MAIN-1001").orElseThrow().getBalance();
        long logsBefore = logs.count();
        assertThrows(IllegalStateException.class, () ->
                transferService.transfer("ACC-MAIN-1001", "ACC-FORCE-FAIL", new BigDecimal("10.00")));
        assertEquals(before, accounts.findById("ACC-MAIN-1001").orElseThrow().getBalance());
        assertEquals(logsBefore, logs.count());
    }

    @Test
    void happyPathMovesFunds() {
        BigDecimal amount = new BigDecimal("5.00");
        BigDecimal mainBefore = accounts.findById("ACC-MAIN-1001").orElseThrow().getBalance();
        BigDecimal loyaltyBefore = accounts.findById("ACC-LOYALTY-1001").orElseThrow().getBalance();
        long logsBefore = logs.count();

        transferService.transfer("ACC-MAIN-1001", "ACC-LOYALTY-1001", amount);

        BigDecimal mainAfter = accounts.findById("ACC-MAIN-1001").orElseThrow().getBalance();
        BigDecimal loyaltyAfter = accounts.findById("ACC-LOYALTY-1001").orElseThrow().getBalance();
        assertEquals(0, mainBefore.subtract(amount).compareTo(mainAfter));
        assertEquals(0, loyaltyBefore.add(amount).compareTo(loyaltyAfter));
        assertEquals(logsBefore + 1, logs.count());
    }
}