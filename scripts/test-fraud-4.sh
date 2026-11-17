#!/bin/bash

# Amount nya lebih dari 1000000 ? 

curl -X POST http://localhost:8080/api/v1/transactions \
-H "Content-Type: application/json" \
-d '{
  "transactionId": "TXN-12350",
  "accountId": "ACC-998",
  "amount": 1500000.00,
  "currency": "USD",
  "timestamp": "2026-11-18T10:30:00",
  "ipAddress": "192.168.1.10",
  "deviceId": "device-xyz-01"
}'