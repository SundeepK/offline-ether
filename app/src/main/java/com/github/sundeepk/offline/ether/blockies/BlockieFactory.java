package com.github.sundeepk.offline.ether.blockies;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BlockieFactory {


    public byte[] getBlockie(String address) {
        Bitmap blockie = Blockies.createIcon(address, new Blockies.BlockiesOpts(6, 2, 2));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        blockie.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
