package com.amal.fds.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.amal.fds.entity.TransactionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByTransactionId(String transactionId);
}
