package com.github.tifezh.kchartlib.toutiao;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description 文字动画
 * @date 2020/7/29 16:21
 */

public class TextAnimationFrame extends BaseAnimationFrame {
    public static final int TYPE = 2;
    private long lastClickTimeMillis;
    /**
     * 连续点击次数
     */
    private int likeCount;

    public TextAnimationFrame(long duration) {
        super(duration);
    }

    @Override
    public void prepare(int width, int height, int x, int y, BitmapProvider.Provider bitmapProvider) {
        reset();
        setStartPoint(width, height, x, y);
        calculateCombo();
        elements = generatedElements(width, height, x, y, bitmapProvider);
    }

    /**
     * 判断数字是重置还是加 1
     */
    private void calculateCombo() {
        //连续点击事件小于 1s 加 1, 否着重置
        if (System.currentTimeMillis() - lastClickTimeMillis < duration) {
            likeCount++;
        } else {
            likeCount = 1;
        }
        lastClickTimeMillis = System.currentTimeMillis();
    }

    @Override
    public int getType() {
        return TYPE;
    }


    @Override
    public boolean onlyOne() {
        return true;
    }


    /**
     * 生成N个 Element
     */
    @Override
    protected List<Element> generatedElements(int width, int height, int x, int y, BitmapProvider.Provider bitmapProvider) {
        List<Element> elements = new ArrayList<>();
        int count = likeCount;
        int offset_x = 0;
        while (count > 0) {
            int number = count % 10;
            Bitmap bitmap = bitmapProvider.getNumberBitmap(number);
            offset_x += bitmap.getWidth();
            Element element = new TextElement(bitmap, x - offset_x - width);
            elements.add(element);
            count = count / 10;
        }

        int level = likeCount / 10;
        if (level > 2) {
            level = 2;
        }
        elements.add(new TextElement(bitmapProvider.getLevelBitmap(level), x - width));
        return elements;
    }

    public static class TextElement implements Element {
        private int x;
        private int y;
        private Bitmap bitmap;

        public TextElement(Bitmap bitmap, int x) {
            this.bitmap = bitmap;
            this.x = x;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public Bitmap getBitmap() {
            return bitmap;
        }

        @Override
        public void evaluate(int width, int height, int start_x, int start_y, double time) {
            y = start_y - height - bitmap.getHeight();
        }
    }
}
