package com.example.sundeep.offline_ether.utils;

import java.math.BigDecimal;

public class EtherMath {

    public static String weiAsEtherStr(BigDecimal wei) {
        return wei.divide(new BigDecimal("1E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH";
    }

    public static String weiAsEtherStr(String wei) {
        BigDecimal balance = new BigDecimal(wei);
        return balance.divide(new BigDecimal("1E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH";
    }

}
