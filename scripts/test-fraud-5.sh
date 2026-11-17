#!/bin/bash

# unsusual time jam 2 pagi ada transaksi

curl -X POST http://localhost:8080/api/v1/transactions \
-H "Content-Type: application/json" \
-d '{
  "transactionId": "TXN-12351",
  "accountId": "ACC-997",
  "amount": 1000000.00,
  "currency": "USD",
  "timestamp": "2026-11-18T02:30:00",
  "ipAddress": "192.168.1.10",
  "deviceId": "device-xyz-01"
}'