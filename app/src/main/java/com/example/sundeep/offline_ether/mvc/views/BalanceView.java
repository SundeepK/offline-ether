package com.example.sundeep.offline_ether.mvc.views;

public interface BalanceView {

    public void onTotalBalance(String format);

    public void onBalanceError(Throwable throwable);

}
