CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    account_id VARCHAR(255) NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    risk_score INT NOT NULL,
    decision VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);