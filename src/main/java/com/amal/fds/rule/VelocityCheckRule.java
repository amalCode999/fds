package com.amal.fds.rule;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.amal.fds.dto.TransactionRequest;
import com.amal.fds.service.RedisVelocityService;

@Component
public class VelocityCheckRule implements FraudRule{
    private final RedisVelocityService velocityService;

    private static final int MAX_TRANSACTIONS = 3;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(5);

    public VelocityCheckRule(RedisVelocityService velocityService) {
        this.velocityService = velocityService;
    }

    @Override
    public RuleResult evaluate(TransactionRequest request) {
        
        boolean isExceeded = velocityService.isVelocityExceeded(
            request.accountId(), 
            MAX_TRANSACTIONS, 
            WINDOW_DURATION
        );

        if (isExceeded) {
            return new RuleResult(
                "VelocityCheckRule",
                true,
                50, 
                "Terdeteksi terlalu banyak transaksi dalam waktu singkat (Velocity limit exceeded)"
            );
        }

        return new RuleResult("VelocityCheckRule", false, 0, "Normal");
    }
    
}
