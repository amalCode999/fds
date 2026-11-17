package com.amal.fds.dto;

import java.util.List;

public record FraudEvaluationResult(
    String transactionId, 
    int totalRiskScore, 
    String decision, 
    List<String> triggeredRules
) {}
