package com.example.sundeep.offline_ether.entities;

import java.util.List;

public class Balances {

    private List<Balance> result;

    public Balances(List<Balance> result) {
        this.result = result;
    }

    public List<Balance> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Balances{" +
                "result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balances balances = (Balances) o;

        return result != null ? result.equals(balances.result) : balances.result == null;
    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }
}
