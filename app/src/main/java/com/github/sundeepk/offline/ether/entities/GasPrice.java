package com.github.sundeepk.offline.ether.entities;

public class GasPrice {

    String type;
    float gasPrice;
    float waitTime;
    boolean isSelected;

    public GasPrice(String type, float gasPrice, float waitTime, boolean isSelected) {
        this.type = type;
        this.gasPrice = gasPrice;
        this.waitTime = waitTime;
        this.isSelected = isSelected;
    }

    private GasPrice(Builder builder) {
        type = builder.type;
        gasPrice = builder.gasPrice;
        waitTime = builder.waitTime;
        isSelected = builder.isSelected;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(GasPrice copy) {
        Builder builder = new Builder();
        builder.type = copy.getType();
        builder.gasPrice = copy.getGasPrice();
        builder.waitTime = copy.getWaitTime();
        builder.isSelected = copy.isSelected();
        return builder;
    }

    public float getGasPrice() {
        return gasPrice;
    }

    public float getWaitTime() {
        return waitTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GasPrice gasPrice1 = (GasPrice) o;

        if (Float.compare(gasPrice1.gasPrice, gasPrice) != 0) return false;
        if (Float.compare(gasPrice1.waitTime, waitTime) != 0) return false;
        if (isSelected != gasPrice1.isSelected) return false;
        return type != null ? type.equals(gasPrice1.type) : gasPrice1.type == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (gasPrice != +0.0f ? Float.floatToIntBits(gasPrice) : 0);
        result = 31 * result + (waitTime != +0.0f ? Float.floatToIntBits(waitTime) : 0);
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GasPrice{" +
                "type='" + type + '\'' +
                ", gasPrice=" + gasPrice +
                ", waitTime=" + waitTime +
                ", isSelected=" + isSelected +
                '}';
    }

    public static final class Builder {
        private String type;
        private float gasPrice;
        private float waitTime;
        private boolean isSelected;

        private Builder() {
        }

        public Builder setType(String val) {
            type = val;
            return this;
        }

        public Builder setGasPrice(float val) {
            gasPrice = val;
            return this;
        }

        public Builder setWaitTime(float val) {
            waitTime = val;
            return this;
        }

        public Builder setIsSelected(boolean val) {
            isSelected = val;
            return this;
        }

        public GasPrice build() {
            return new GasPrice(this);
        }
    }
}
