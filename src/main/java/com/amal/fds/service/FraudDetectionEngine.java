package com.amal.fds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amal.fds.dto.FraudEvaluationResult;
import com.amal.fds.dto.TransactionRequest;
import com.amal.fds.rule.FraudRule;
import com.amal.fds.rule.RuleResult;

@Service
public class FraudDetectionEngine {
    private final List<FraudRule> fraudRules;

    public FraudDetectionEngine(List<FraudRule> fraudRules) {
        this.fraudRules = fraudRules;
    }

    public FraudEvaluationResult evaluateTransaction(TransactionRequest request) {
        int totalRiskScore = 0;
        List<String> triggeredRules = new ArrayList<>();

        for (FraudRule rule : fraudRules) {
            RuleResult result = rule.evaluate(request);
            if (result.isTriggered()) {
                totalRiskScore += result.riskScoreIncrement();
                triggeredRules.add(result.ruleName() + ": " + result.message());
            }
        }

        String decision = determineDecision(totalRiskScore);

        return new FraudEvaluationResult(
            request.transactionId(), 
            totalRiskScore, 
            decision, 
            triggeredRules
        );
    }

    private String determineDecision(int score) {
        if (score >= 50) return "REJECT";
        if (score >= 25) return "REVIEW"; 
        return "APPROVE";
    }
}


