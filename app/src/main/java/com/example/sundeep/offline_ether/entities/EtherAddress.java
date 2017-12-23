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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ToMany<EtherTransaction> getEtherTransactions() {
        return etherTransactions;
    }

    public void setEtherTransactions(ToMany<EtherTransaction> etherTransactions) {
        this.etherTransactions = etherTransactions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
