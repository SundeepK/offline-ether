package com.github.sundeepk.offline.ether.entities;

public class Balance {

    private String account;
    private String balance;

    public Balance(String account, String balance) {
        this.account = account;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public String getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "account='" + account + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance1 = (Balance) o;

        if (account != null ? !account.equals(balance1.account) : balance1.account != null)
            return false;
        return balance != null ? balance.equals(balance1.balance) : balance1.balance == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
