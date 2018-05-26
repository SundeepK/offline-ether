package com.github.sundeepk.offline.ether.entities;

public class EthGas {

    private float fastestWait;
    private float fast;
    private float block_time;
    private float average;
    private float safeLowWait;
    private float fastest;
    private float avgWait;
    private float blockNum;
    private float speed;
    private float fastWait;
    private float safeLow;

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

    public float getFastestWait() {
        return fastestWait;
    }

    public float getFast() {
        return fast;
    }

    public float getBlock_time() {
        return block_time;
    }

    public float getAverage() {
        return average;
    }

    public float getSafeLowWait() {
        return safeLowWait;
    }

    public float getFastest() {
        return fastest;
    }

    public float getAvgWait() {
        return avgWait;
    }

    public float getBlockNum() {
        return blockNum;
    }

    public float getSpeed() {
        return speed;
    }

    public float getFastWait() {
        return fastWait;
    }

    public float getSafeLow() {
        return safeLow;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EthGas ethGas = (EthGas) o;

        if (Float.compare(ethGas.fastestWait, fastestWait) != 0) return false;
        if (Float.compare(ethGas.fast, fast) != 0) return false;
        if (Float.compare(ethGas.block_time, block_time) != 0) return false;
        if (Float.compare(ethGas.average, average) != 0) return false;
        if (Float.compare(ethGas.safeLowWait, safeLowWait) != 0) return false;
        if (Float.compare(ethGas.fastest, fastest) != 0) return false;
        if (Float.compare(ethGas.avgWait, avgWait) != 0) return false;
        if (Float.compare(ethGas.blockNum, blockNum) != 0) return false;
        if (Float.compare(ethGas.speed, speed) != 0) return false;
        if (Float.compare(ethGas.fastWait, fastWait) != 0) return false;
        return Float.compare(ethGas.safeLow, safeLow) == 0;
    }

    @Override
    public int hashCode() {
        int result = (fastestWait != +0.0f ? Float.floatToIntBits(fastestWait) : 0);
        result = 31 * result + (fast != +0.0f ? Float.floatToIntBits(fast) : 0);
        result = 31 * result + (block_time != +0.0f ? Float.floatToIntBits(block_time) : 0);
        result = 31 * result + (average != +0.0f ? Float.floatToIntBits(average) : 0);
        result = 31 * result + (safeLowWait != +0.0f ? Float.floatToIntBits(safeLowWait) : 0);
        result = 31 * result + (fastest != +0.0f ? Float.floatToIntBits(fastest) : 0);
        result = 31 * result + (avgWait != +0.0f ? Float.floatToIntBits(avgWait) : 0);
        result = 31 * result + (blockNum != +0.0f ? Float.floatToIntBits(blockNum) : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        result = 31 * result + (fastWait != +0.0f ? Float.floatToIntBits(fastWait) : 0);
        result = 31 * result + (safeLow != +0.0f ? Float.floatToIntBits(safeLow) : 0);
        return result;
    }

    public static final class Builder {
        private float fastestWait;
        private float fast;
        private float block_time;
        private float average;
        private float safeLowWait;
        private float fastest;
        private float avgWait;
        private float blockNum;
        private float speed;
        private float fastWait;
        private float safeLow;

        private Builder() {
        }

        public Builder setFastestWait(float val) {
            fastestWait = val;
            return this;
        }

        public Builder setFast(float val) {
            fast = val;
            return this;
        }

        public Builder setBlock_time(float val) {
            block_time = val;
            return this;
        }

        public Builder setAverage(float val) {
            average = val;
            return this;
        }

        public Builder setSafeLowWait(float val) {
            safeLowWait = val;
            return this;
        }

        public Builder setFastest(float val) {
            fastest = val;
            return this;
        }

        public Builder setAvgWait(float val) {
            avgWait = val;
            return this;
        }

        public Builder setBlockNum(float val) {
            blockNum = val;
            return this;
        }

        public Builder setSpeed(float val) {
            speed = val;
            return this;
        }

        public Builder setFastWait(float val) {
            fastWait = val;
            return this;
        }

        public Builder setSafeLow(float val) {
            safeLow = val;
            return this;
        }

        public EthGas build() {
            return new EthGas(this);
        }
    }
}
