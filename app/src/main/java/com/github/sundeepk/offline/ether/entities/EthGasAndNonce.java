package com.github.sundeepk.offline.ether.entities;

public class EthGasAndNonce {

    private EthGas ethGas;
    private Nonce nonce;

    public EthGasAndNonce(EthGas ethGas, Nonce nonce) {
        this.ethGas = ethGas;
        this.nonce = nonce;
    }

    public EthGas getEthGas() {
        return ethGas;
    }

    public Nonce getNonce() {
        return nonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EthGasAndNonce that = (EthGasAndNonce) o;

        if (ethGas != null ? !ethGas.equals(that.ethGas) : that.ethGas != null) return false;
        return nonce != null ? nonce.equals(that.nonce) : that.nonce == null;
    }

    @Override
    public int hashCode() {
        int result = ethGas != null ? ethGas.hashCode() : 0;
        result = 31 * result + (nonce != null ? nonce.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EthGasAndNonce{" +
                "ethGas=" + ethGas +
                ", nonce=" + nonce +
                '}';
    }
}
