package com.example.sundeep.offline_ether.utils;

import com.example.sundeep.offline_ether.entities.EtherAddress;
import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.util.List;

public class EtherMath {

    public static String weiAsEtherStr(BigDecimal wei) {
        return wei.divide(new BigDecimal("1E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH";
    }

    public static BigDecimal weiAsEther(BigDecimal wei) {
        return wei.divide(new BigDecimal("1E18"), 4, BigDecimal.ROUND_HALF_UP);
    }

    public static String weiAsEtherStr(String wei) {
        if (Strings.isNullOrEmpty(wei)) {
            return "0 ETH";
        }
        BigDecimal balance = new BigDecimal(wei);
        return balance.divide(new BigDecimal("1E18"), 4, BigDecimal.ROUND_HALF_UP).toString() + " ETH";
    }

    public static BigDecimal sumAddresses(List<EtherAddress> addresses) {
        BigDecimal balance = new BigDecimal("0");
        for (EtherAddress address : addresses) {
            balance = balance.add(new BigDecimal(address.getBalance()));
        }
        return EtherMath.weiAsEther(balance);
    }

}
