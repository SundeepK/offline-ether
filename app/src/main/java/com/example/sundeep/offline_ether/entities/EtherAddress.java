package com.example.sundeep.offline_ether.entities;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToMany;

@Entity
public class EtherAddress {

    @Id
    long id;

    @Index
    String address;

    String balance;

    @Backlink
    ToMany<EtherTransaction> etherTransactions;

    public EtherAddress(String address, String balance) {
        this.address = address;
        this.balance = balance;
    }

    public EtherAddress() {
    }

    private EtherAddress(Builder builder) {
        id = builder.id;
        address = builder.address;
        balance = builder.balance;
        etherTransactions = builder.etherTransactions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EtherAddress copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.address = copy.getAddress();
        builder.balance = copy.getBalance();
        builder.etherTransactions = copy.getEtherTransactions();
        return builder;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public ToMany<EtherTransaction> getEtherTransactions() {
        return etherTransactions;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "EtherAddress{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", balance='" + balance + '\'' +
                ", etherTransactions=" + etherTransactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EtherAddress that = (EtherAddress) o;

        if (id != that.id) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        return etherTransactions != null ? etherTransactions.equals(that.etherTransactions) : that.etherTransactions == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (etherTransactions != null ? etherTransactions.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private long id;
        private String address;
        private String balance;
        private ToMany<EtherTransaction> etherTransactions;

        private Builder() {
        }

        public Builder setId(long val) {
            id = val;
            return this;
        }

        public Builder setAddress(String val) {
            address = val;
            return this;
        }

        public Builder setBalance(String val) {
            balance = val;
            return this;
        }

        public Builder setEtherTransactions(ToMany<EtherTransaction> val) {
            etherTransactions = val;
            return this;
        }

        public EtherAddress build() {
            return new EtherAddress(this);
        }
    }
}
