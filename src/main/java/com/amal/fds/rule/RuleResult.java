package com.amal.fds.rule;

public record RuleResult(
    String ruleName,
    boolean isTriggered,
    int riskScoreIncrement,
    String message
) {}