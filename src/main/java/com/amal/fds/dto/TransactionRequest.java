package com.amal.fds.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
    String transactionId,
    String accountId,
    BigDecimal amount,
    String currency,
    LocalDateTime timestamp,
    String ipAddress,
    String deviceId
) {}
