package com.amal.fds.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amal.fds.dto.TransactionRequest;
import com.amal.fds.service.RedisVelocityService;

public class VelocityCheckRuleTest {
    private RedisVelocityService redisVelocityService;
    private VelocityCheckRule velocityCheckRule;

    @BeforeEach
    void setUp() {
        redisVelocityService = Mockito.mock(RedisVelocityService.class);
        velocityCheckRule = new VelocityCheckRule(redisVelocityService);
    }

    @Test
    void testVelocityCheck_Triggered_WhenLimitExceeded() {
        // Arrange
        TransactionRequest request = new TransactionRequest(
            "TXN-003", "ACC-999", new BigDecimal("50.00"), 
            "USD", LocalDateTime.now(), "192.168.1.5", "device-xyz"
        );

        // Simulasi bahwa Redis mendeteksi batas terlampaui (mengembalikan true)
        when(redisVelocityService.isVelocityExceeded(eq("ACC-999"), any(Integer.class), any(Duration.class)))
            .thenReturn(true);

        // Act
        RuleResult result = velocityCheckRule.evaluate(request);

        // Assert
        assertTrue(result.isTriggered());
        assertEquals("VelocityCheckRule", result.ruleName());
        assertEquals(50, result.riskScoreIncrement());
    }

    @Test
    void testVelocityCheck_NotTriggered_WhenWithinLimit() {
        // Arrange
        TransactionRequest request = new TransactionRequest(
            "TXN-004", "ACC-999", new BigDecimal("50.00"), 
            "USD", LocalDateTime.now(), "192.168.1.5", "device-xyz"
        );

        // Simulasi bahwa velocity masih aman (mengembalikan false)
        when(redisVelocityService.isVelocityExceeded(eq("ACC-999"), any(Integer.class), any(Duration.class)))
            .thenReturn(false);

        // Act
        RuleResult result = velocityCheckRule.evaluate(request);

        // Assert
        assertFalse(result.isTriggered());
        assertEquals(0, result.riskScoreIncrement());
    }
}
