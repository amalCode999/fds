package com.amal.fds.rule;

import org.springframework.stereotype.Component;

import com.amal.fds.dto.TransactionRequest;

@Component
public class UnusualTimeRule implements FraudRule {
    @Override
    public RuleResult evaluate(TransactionRequest request) {
        int hour = request.timestamp().getHour();
        
        if (hour >= 1 && hour <= 4) {
            return new RuleResult(
                "UnusualTimeRule", 
                true, 
                20, 
                "Transaksi dilakukan pada jam dini hari"
            );
        }
        return new RuleResult("UnusualTimeRule", false, 0, "Normal");
    }
    
}
