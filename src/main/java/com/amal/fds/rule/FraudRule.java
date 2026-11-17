package com.amal.fds.rule;

import com.amal.fds.dto.TransactionRequest;

public interface FraudRule {
    RuleResult evaluate(TransactionRequest request);
}