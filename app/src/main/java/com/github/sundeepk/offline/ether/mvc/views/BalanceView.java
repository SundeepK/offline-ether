package com.github.sundeepk.offline.ether.mvc.views;

public interface BalanceView {

    public void onTotalBalance(String format);

    public void onBalanceError(Throwable throwable);

}
