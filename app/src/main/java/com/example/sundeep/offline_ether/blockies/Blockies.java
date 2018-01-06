package com.example.sundeep.offline_ether.blockies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Arrays;

public class Blockies {

    private static final int size = 8;
    private static long[] randseed = new long[4];

    public static Bitmap createIcon(String address) {
        return createIcon(address, new BlockiesOpts(16, 0, 0));
    }

    public static Bitmap createIcon(String address, BlockiesOpts blockiesOpts) {
        seedrand(address);
        HSL color = createColor();
        HSL bgColor = createColor();
        HSL spotColor = createColor();

        int[] imgdata = createImageData();
        return createCanvas(imgdata, color, bgColor, spotColor, blockiesOpts);
    }

    private static Bitmap createCanvas(int[] imgData, HSL color, HSL bgcolor, HSL spotcolor, BlockiesOpts blockiesOpts) {
        int width = (int) Math.sqrt(imgData.length);

        int w = width * blockiesOpts.scale;
        int h = width * blockiesOpts.scale;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w, h, conf);
        Canvas canvas = new Canvas(bmp);

        int background = toRGB((int) bgcolor.h, (int) bgcolor.s, (int) bgcolor.l);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(background);
        canvas.drawRect(0, 0, w, h, paint);

        int main = toRGB((int) color.h, (int) color.s, (int) color.l);
        int scolor = toRGB((int) spotcolor.h, (int) spotcolor.s, (int) spotcolor.l);

        for (int i = 0; i < imgData.length; i++) {
            int row = (int) Math.floor(i / width);
            int col = i % width;
            paint = new Paint();

            paint.setColor((imgData[i] == 1.0d) ? main : scolor);

            if (imgData[i] > 0d) {
                canvas.drawRect(col * blockiesOpts.scale,
                        row * blockiesOpts.scale,
                        (col * blockiesOpts.scale) + blockiesOpts.scale,
                        (row * blockiesOpts.scale) + blockiesOpts.scale,
                        paint);
            }
        }
        return getCroppedBitmap(bmp, blockiesOpts);
    }

    private static double rand() {
        int t = (int) (randseed[0] ^ (randseed[0] << 11));
        randseed[0] = randseed[1];
        randseed[1] = randseed[2];
        randseed[2] = randseed[3];
        randseed[3] = (randseed[3] ^ (randseed[3] >> 19) ^ t ^ (t >> 8));
        double t1 = Math.abs(randseed[3]);

        return (t1 / Integer.MAX_VALUE);
    }

    private static HSL createColor() {
        double h = Math.floor(rand() * 360d);
        double s = ((rand() * 42));
        return new HSL(h, s, 45);
    }

    private static int[] createImageData() {
        int width = size;
        int height = size;

        int dataWidth = (int) Math.ceil(width / 2);
        int mirrorWidth = width - dataWidth;

        int[] data = new int[size * size];
        int dataCount = 0;
        for (int y = 0; y < height; y++) {
            int[] row = new int[dataWidth];
            for (int x = 0; x < dataWidth; x++) {
                row[x] = (int) Math.floor(rand() * 2.3f);
            }

            int[] r = Arrays.copyOfRange(row, 0, mirrorWidth);
            r = reverse(r);
            row = concat(row, r);

            for (int i = 0; i < row.length; i++) {
                data[dataCount] = row[i];
                dataCount++;
            }
        }

        return data;
    }

    public static int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c = new int[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private static int[] reverse(int[] data) {
        for (int i = 0; i < data.length / 2; i++) {
            int temp = data[i];
            data[i] = data[data.length - i - 1];
            data[data.length - i - 1] = temp;
        }
        return data;
    }

    private static void seedrand(String seed) {
        randseed = new long[4];
        for (int i = 0; i < seed.length(); i++) {
            long test = randseed[i % 4] << 5;
            if (test > Integer.MAX_VALUE << 1 || test < Integer.MIN_VALUE << 1)
                test = (int) test;

            long test2 = test - randseed[i % 4];
            randseed[i % 4] = (test2 + Character.codePointAt(seed, i));
        }

        for (int i = 0; i < randseed.length; i++)
            randseed[i] = (int) randseed[i];
    }

    private static int toRGB(float h, float s, float l) {
        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        float r = Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)));
        float g = Math.max(0, HueToRGB(p, q, h));
        float b = Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)));

        r = Math.min(r, 1.0f);
        g = Math.min(g, 1.0f);
        b = Math.min(b, 1.0f);

        int red = (int) (r * 255);
        int green = (int) (g * 255);
        int blue = (int) (b * 255);
        return Color.rgb(red, green, blue);
    }

    private static float HueToRGB(float p, float q, float h) {
        if (h < 0) h += 1;
        if (h > 1) h -= 1;
        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }
        if (2 * h < 1) {
            return q;
        }
        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }
        return p;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap, BlockiesOpts blockiesOpts) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectf = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectf, blockiesOpts.rx, blockiesOpts.ry, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static class BlockiesOpts{
        int scale;
        int rx;
        int ry;

        public BlockiesOpts(int scale, int rx, int ry) {
            this.scale = scale;
            this.rx = rx;
            this.ry = ry;
        }
    }

    static class HSL {
        double h, s, l;

        public HSL(double h, double s, double l) {
            this.h = h;
            this.s = s;
            this.l = l;
        }

        @Override
        public String toString() {
            return "HSL [h=" + h + ", s=" + s + ", l=" + l + "]";
        }

    }
}