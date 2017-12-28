package com.example.sundeep.offline_ether.entities;

class EtherTransactionJson {
    String blockNumber;
    long timeStamp;
    String hash;
    int nonce;
    String blockHash;
    String from;
    String to;
    String value;
    String gas;
    String gasPrice;
    int isError;
    String cumulativeGasUsed;
    int gasUsed;
    int confirmations;

    public String getBlockNumber() {
        return blockNumber;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public int getNonce() {
        return nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public String getGas() {
        return gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public int getIsError() {
        return isError;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public int getGasUsed() {
        return gasUsed;
    }

    public int getConfirmations() {
        return confirmations;
    }
}
