package com.example.sundeep.offline_ether.entities;

public class TransactionError {

    private int code;
    private String message;

    public TransactionError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionError that = (TransactionError) o;

        if (code != that.code) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
