package com.amal.fds.rule;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.amal.fds.dto.TransactionRequest;

@Component
public class HighAmountRule implements FraudRule {

   private static final BigDecimal THRESHOLD = new BigDecimal("1000000.00");

    @Override
    public RuleResult evaluate(TransactionRequest request) {
        if (request.amount().compareTo(THRESHOLD) > 0) {
            return new RuleResult(
                "HighAmountRule", 
                true, 
                40, 
                "Transaksi melebihi batas kewajaran (Rp 1.000.000)"
            );
        }
        return new RuleResult("HighAmountRule", false, 0, "Normal");
    }
    
}
