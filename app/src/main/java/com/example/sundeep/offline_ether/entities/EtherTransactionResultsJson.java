package com.example.sundeep.offline_ether.entities;

import java.util.List;

public class EtherTransactionResultsJson {

    int status;
    String message;
    List<EtherTransaction> result;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<EtherTransaction> getResults() {
        return result;
    }
}
