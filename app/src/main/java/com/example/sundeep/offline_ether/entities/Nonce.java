package com.example.sundeep.offline_ether.entities;

public class Nonce {

    private String result;

    public Nonce(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nonce nonce1 = (Nonce) o;

        return result != null ? result.equals(nonce1.result) : nonce1.result == null;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Nonce{" +
                "result=" + result +
                '}';
    }

    public String getNonce() {
        return result;
    }
}
