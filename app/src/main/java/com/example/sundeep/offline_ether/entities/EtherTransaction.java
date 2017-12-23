package com.example.sundeep.offline_ether.entities;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class EtherTransaction {

    @Id
    long blockNumber;

    public ToOne<EtherAddress> address;

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
    int cumulativeGasUsed;
    int gasUsed;
    long confirmations;

}
