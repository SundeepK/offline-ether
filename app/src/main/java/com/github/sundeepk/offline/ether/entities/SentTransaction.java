package com.github.sundeepk.offline.ether.entities;

public class SentTransaction {

    private TransactionError error;
    private String result;

    public SentTransaction(){}

    public SentTransaction(TransactionError error, String result) {
        this.error = error;
        this.result = result;
    }

    public TransactionError getError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentTransaction that = (SentTransaction) o;

        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return result != null ? result.equals(that.result) : that.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = error != null ? error.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "SentTransaction{" +
                "error=" + error +
                ", result='" + result + '\'' +
                '}';
    }
}
