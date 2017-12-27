package com.example.sundeep.offline_ether.entities;

import java.math.BigInteger;

public class Nonce {

    private BigInteger nonce;

    public Nonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nonce nonce1 = (Nonce) o;

        return nonce != null ? nonce.equals(nonce1.nonce) : nonce1.nonce == null;
    }

    @Override
    public int hashCode() {
        return nonce != null ? nonce.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Nonce{" +
                "nonce=" + nonce +
                '}';
    }

    public BigInteger getNonce() {
        return nonce;
    }
}
