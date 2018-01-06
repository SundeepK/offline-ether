package com.example.sundeep.offline_ether.entities;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Price {

    @Id
    Long id;

    private String code;
    private float price;

    public Price(){
    }

    public Price(String code, float price) {
        this.code = code;
        this.price = price;
    }

    private Price(Builder builder) {
        id = builder.id;
        code = builder.code;
        price = builder.price;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Price copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.code = copy.getCode();
        builder.price = copy.getPrice();
        return builder;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        if (Float.compare(price1.price, price) != 0) return false;
        if (id != null ? !id.equals(price1.id) : price1.id != null) return false;
        return code != null ? code.equals(price1.code) : price1.code == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String code;
        private float price;

        private Builder() {
        }

        public Builder setId(Long val) {
            id = val;
            return this;
        }

        public Builder setCode(String val) {
            code = val;
            return this;
        }

        public Builder setPrice(float val) {
            price = val;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }
}
