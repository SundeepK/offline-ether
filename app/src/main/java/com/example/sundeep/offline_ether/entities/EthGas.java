package com.example.sundeep.offline_ether.entities;

public class EthGas {

    private int fastestWait;
    private int fast;
    private int block_time;
    private int average;
    private int safeLowWait;
    private int fastest;
    private int avgWait;
    private int blockNum;
    private int speed;
    private int fastWait;
    private int safeLow;

    private EthGas(Builder builder) {
        fastestWait = builder.fastestWait;
        fast = builder.fast;
        block_time = builder.block_time;
        average = builder.average;
        safeLowWait = builder.safeLowWait;
        fastest = builder.fastest;
        avgWait = builder.avgWait;
        blockNum = builder.blockNum;
        speed = builder.speed;
        fastWait = builder.fastWait;
        safeLow = builder.safeLow;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EthGas copy) {
        Builder builder = new Builder();
        builder.fastestWait = copy.getFastestWait();
        builder.fast = copy.getFast();
        builder.block_time = copy.getBlock_time();
        builder.average = copy.getAverage();
        builder.safeLowWait = copy.getSafeLowWait();
        builder.fastest = copy.getFastest();
        builder.avgWait = copy.getAvgWait();
        builder.blockNum = copy.getBlockNum();
        builder.speed = copy.getSpeed();
        builder.fastWait = copy.getFastWait();
        builder.safeLow = copy.getSafeLow();
        return builder;
    }

    public int getFastestWait() {
        return fastestWait;
    }

    public int getFast() {
        return fast;
    }

    public int getBlock_time() {
        return block_time;
    }

    public int getAverage() {
        return average;
    }

    public int getSafeLowWait() {
        return safeLowWait;
    }

    public int getFastest() {
        return fastest;
    }

    public int getAvgWait() {
        return avgWait;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public int getSpeed() {
        return speed;
    }

    public int getFastWait() {
        return fastWait;
    }

    public int getSafeLow() {
        return safeLow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EthGas ethGas = (EthGas) o;

        if (fastestWait != ethGas.fastestWait) return false;
        if (fast != ethGas.fast) return false;
        if (block_time != ethGas.block_time) return false;
        if (average != ethGas.average) return false;
        if (safeLowWait != ethGas.safeLowWait) return false;
        if (fastest != ethGas.fastest) return false;
        if (avgWait != ethGas.avgWait) return false;
        if (blockNum != ethGas.blockNum) return false;
        if (speed != ethGas.speed) return false;
        if (fastWait != ethGas.fastWait) return false;
        return safeLow == ethGas.safeLow;
    }

    @Override
    public int hashCode() {
        int result = fastestWait;
        result = 31 * result + fast;
        result = 31 * result + block_time;
        result = 31 * result + average;
        result = 31 * result + safeLowWait;
        result = 31 * result + fastest;
        result = 31 * result + avgWait;
        result = 31 * result + blockNum;
        result = 31 * result + speed;
        result = 31 * result + fastWait;
        result = 31 * result + safeLow;
        return result;
    }

    @Override
    public String toString() {
        return "EthGas{" +
                "fastestWait=" + fastestWait +
                ", fast=" + fast +
                ", block_time=" + block_time +
                ", average=" + average +
                ", safeLowWait=" + safeLowWait +
                ", fastest=" + fastest +
                ", avgWait=" + avgWait +
                ", blockNum=" + blockNum +
                ", speed=" + speed +
                ", fastWait=" + fastWait +
                ", safeLow=" + safeLow +
                '}';
    }

    public static final class Builder {
        private int fastestWait;
        private int fast;
        private int block_time;
        private int average;
        private int safeLowWait;
        private int fastest;
        private int avgWait;
        private int blockNum;
        private int speed;
        private int fastWait;
        private int safeLow;

        private Builder() {
        }

        public Builder setFastestWait(int val) {
            fastestWait = val;
            return this;
        }

        public Builder setFast(int val) {
            fast = val;
            return this;
        }

        public Builder setBlock_time(int val) {
            block_time = val;
            return this;
        }

        public Builder setAverage(int val) {
            average = val;
            return this;
        }

        public Builder setSafeLowWait(int val) {
            safeLowWait = val;
            return this;
        }

        public Builder setFastest(int val) {
            fastest = val;
            return this;
        }

        public Builder setAvgWait(int val) {
            avgWait = val;
            return this;
        }

        public Builder setBlockNum(int val) {
            blockNum = val;
            return this;
        }

        public Builder setSpeed(int val) {
            speed = val;
            return this;
        }

        public Builder setFastWait(int val) {
            fastWait = val;
            return this;
        }

        public Builder setSafeLow(int val) {
            safeLow = val;
            return this;
        }

        public EthGas build() {
            return new EthGas(this);
        }
    }
}
