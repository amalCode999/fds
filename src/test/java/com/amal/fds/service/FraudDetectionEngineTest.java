package com.amal.fds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amal.fds.dto.TransactionRequest;
import com.amal.fds.rule.FraudRule;
import com.amal.fds.rule.RuleResult;

public class FraudDetectionEngineTest {

    private FraudDetectionEngine fraudDetectionEngine;
    private FraudRule mockRule1;
    private FraudRule mockRule2;
    

    @BeforeEach
    void setUp() {
        // Membuat mock untuk aturan-aturan fraud
        mockRule1 = Mockito.mock(FraudRule.class);
        mockRule2 = Mockito.mock(FraudRule.class);

        // Masukkan mock rules ke dalam engine
        fraudDetectionEngine = new FraudDetectionEngine(List.of(mockRule1, mockRule2));
    }


    @Test
    void testTransactionApproved_WhenNoRulesTriggered() {
        // Arrange
        TransactionRequest request = new TransactionRequest(
            "TXN-001", "ACC-123", new BigDecimal("150.00"), 
            "USD", LocalDateTime.now(), "192.168.1.1", "device-abc"
        );

        when(mockRule1.evaluate(any())).thenReturn(new RuleResult("Rule1", false, 0, "Normal"));
        when(mockRule2.evaluate(any())).thenReturn(new RuleResult("Rule2", false, 0, "Normal"));

        // Act
        var result = fraudDetectionEngine.evaluateTransaction(request);

        // Assert
        assertEquals("APPROVE", result.decision());
        assertEquals(0, result.totalRiskScore());
        assertTrue(result.triggeredRules().isEmpty());
    }

    @Test
    void testTransactionRejected_WhenHighRiskScoreTriggered() {
        // Arrange
        TransactionRequest request = new TransactionRequest(
            "TXN-002", "ACC-123", new BigDecimal("15000.00"), 
            "USD", LocalDateTime.now(), "192.168.1.1", "device-abc"
        );

        when(mockRule1.evaluate(any())).thenReturn(new RuleResult("HighAmountRule", true, 60, "Amount too high"));
        when(mockRule2.evaluate(any())).thenReturn(new RuleResult("UnusualTimeRule", false, 0, "Normal"));

        // Act
        var result = fraudDetectionEngine.evaluateTransaction(request);

        // Assert
        assertEquals("REJECT", result.decision());
        assertEquals(60, result.totalRiskScore());
        assertEquals(1, result.triggeredRules().size());
    }
}
