package com.example.sundeep.offline_ether.entities;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class EtherTransaction {

    @Id
    long id;

    String blockNumber;

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
    String cumulativeGasUsed;
    int gasUsed;
    int confirmations;

    public EtherTransaction(){

    }

    public EtherTransaction(String blockNumber, long timeStamp, String hash,
                            int nonce, String blockHash, String from,
                            String to, String value, String gas, String gasPrice,
                            int isError, String cumulativeGasUsed, int gasUsed,
                            int confirmations) {
        this.blockNumber = blockNumber;
        this.timeStamp = timeStamp;
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gas = gas;
        this.gasPrice = gasPrice;
        this.isError = isError;
        this.cumulativeGasUsed = cumulativeGasUsed;
        this.gasUsed = gasUsed;
        this.confirmations = confirmations;
    }

    private EtherTransaction(Builder builder) {
        blockNumber = builder.blockNumber;
        timeStamp = builder.timeStamp;
        hash = builder.hash;
        nonce = builder.nonce;
        blockHash = builder.blockHash;
        from = builder.from;
        to = builder.to;
        value = builder.value;
        gas = builder.gas;
        gasPrice = builder.gasPrice;
        isError = builder.isError;
        cumulativeGasUsed = builder.cumulativeGasUsed;
        gasUsed = builder.gasUsed;
        confirmations = builder.confirmations;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EtherTransaction copy) {
        Builder builder = new Builder();
        builder.blockNumber = copy.getBlockNumber();
        builder.timeStamp = copy.getTimeStamp();
        builder.hash = copy.getHash();
        builder.nonce = copy.getNonce();
        builder.blockHash = copy.getBlockHash();
        builder.from = copy.getFrom();
        builder.to = copy.getTo();
        builder.value = copy.getValue();
        builder.gas = copy.getGas();
        builder.gasPrice = copy.getGasPrice();
        builder.isError = copy.getIsError();
        builder.cumulativeGasUsed = copy.getCumulativeGasUsed();
        builder.gasUsed = copy.getGasUsed();
        builder.confirmations = copy.getConfirmations();
        return builder;
    }

    @Override
    public String toString() {
        return "EtherTransaction{" +
                "blockNumber=" + blockNumber +
                ", address=" + address +
                ", timeStamp=" + timeStamp +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                ", blockHash='" + blockHash + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value='" + value + '\'' +
                ", gas='" + gas + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", isError=" + isError +
                ", cumulativeGasUsed=" + cumulativeGasUsed +
                ", gasUsed=" + gasUsed +
                ", confirmations=" + confirmations +
                '}';
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public int getNonce() {
        return nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public String getGas() {
        return gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public int getIsError() {
        return isError;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public int getGasUsed() {
        return gasUsed;
    }

    public int getConfirmations() {
        return confirmations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EtherTransaction that = (EtherTransaction) o;

        if (id != that.id) return false;
        if (timeStamp != that.timeStamp) return false;
        if (nonce != that.nonce) return false;
        if (isError != that.isError) return false;
        if (gasUsed != that.gasUsed) return false;
        if (confirmations != that.confirmations) return false;
        if (blockNumber != null ? !blockNumber.equals(that.blockNumber) : that.blockNumber != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (blockHash != null ? !blockHash.equals(that.blockHash) : that.blockHash != null)
            return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (gas != null ? !gas.equals(that.gas) : that.gas != null) return false;
        if (gasPrice != null ? !gasPrice.equals(that.gasPrice) : that.gasPrice != null)
            return false;
        return cumulativeGasUsed != null ? cumulativeGasUsed.equals(that.cumulativeGasUsed) : that.cumulativeGasUsed == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (blockNumber != null ? blockNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + nonce;
        result = 31 * result + (blockHash != null ? blockHash.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (gas != null ? gas.hashCode() : 0);
        result = 31 * result + (gasPrice != null ? gasPrice.hashCode() : 0);
        result = 31 * result + isError;
        result = 31 * result + (cumulativeGasUsed != null ? cumulativeGasUsed.hashCode() : 0);
        result = 31 * result + gasUsed;
        result = 31 * result + confirmations;
        return result;
    }


    public static final class Builder {
        private String blockNumber;
        private long timeStamp;
        private String hash;
        private int nonce;
        private String blockHash;
        private String from;
        private String to;
        private String value;
        private String gas;
        private String gasPrice;
        private int isError;
        private String cumulativeGasUsed;
        private int gasUsed;
        private int confirmations;

        private Builder() {
        }

        public Builder setBlockNumber(String val) {
            blockNumber = val;
            return this;
        }

        public Builder setTimeStamp(long val) {
            timeStamp = val;
            return this;
        }

        public Builder setHash(String val) {
            hash = val;
            return this;
        }

        public Builder setNonce(int val) {
            nonce = val;
            return this;
        }

        public Builder setBlockHash(String val) {
            blockHash = val;
            return this;
        }

        public Builder setFrom(String val) {
            from = val;
            return this;
        }

        public Builder setTo(String val) {
            to = val;
            return this;
        }

        public Builder setValue(String val) {
            value = val;
            return this;
        }

        public Builder setGas(String val) {
            gas = val;
            return this;
        }

        public Builder setGasPrice(String val) {
            gasPrice = val;
            return this;
        }

        public Builder setIsError(int val) {
            isError = val;
            return this;
        }

        public Builder setCumulativeGasUsed(String val) {
            cumulativeGasUsed = val;
            return this;
        }

        public Builder setGasUsed(int val) {
            gasUsed = val;
            return this;
        }

        public Builder setConfirmations(int val) {
            confirmations = val;
            return this;
        }

        public EtherTransaction build() {
            return new EtherTransaction(this);
        }
    }
}
