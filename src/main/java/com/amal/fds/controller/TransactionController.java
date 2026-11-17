package com.amal.fds.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amal.fds.dto.FraudEvaluationResult;
import com.amal.fds.dto.TransactionRequest;
import com.amal.fds.entity.TransactionEntity;
import com.amal.fds.repository.TransactionRepository;
import com.amal.fds.service.FraudDetectionEngine;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final FraudDetectionEngine fraudEngine;
    private final TransactionRepository transactionRepository;

    public TransactionController(FraudDetectionEngine fraudEngine, TransactionRepository transactionRepository) {
        this.fraudEngine = fraudEngine;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public ResponseEntity<FraudEvaluationResult> createAndEvaluateTransaction(@RequestBody TransactionRequest request) {
        
        FraudEvaluationResult evaluationResult = fraudEngine.evaluateTransaction(request);

        TransactionEntity entity = new TransactionEntity();
        entity.setTransactionId(request.transactionId());
        entity.setAccountId(request.accountId());
        entity.setAmount(request.amount());
        entity.setRiskScore(evaluationResult.totalRiskScore());
        entity.setDecision(evaluationResult.decision());
        entity.setTimestamp(request.timestamp());
        
        transactionRepository.save(entity);

        return ResponseEntity.ok(evaluationResult);
    }
    
}
