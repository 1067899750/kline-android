package com.github.tifezh.kchartlib.toutiao;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author puyantao
 * @description 图片动画
 * @date 2020/7/29 16:21
 */
public class EruptionAnimationFrame extends BaseAnimationFrame {
    public static final int TYPE = 1;
    private int elementSize;

    public EruptionAnimationFrame(int elementSize, long duration) {
        super(duration);
        this.elementSize = elementSize;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public void prepare(int width, int height, int x, int y, BitmapProvider.Provider bitmapProvider) {
        reset();
        setStartPoint(width, height, x, y);
        elements = generatedElements(width, height, x, y, bitmapProvider);
    }

    /**
     * 生成N个Element
     */
    @Override
    protected List<Element> generatedElements(int width, int height, int x, int y, BitmapProvider.Provider bitmapProvider) {
        List<Element> elements = new ArrayList<>(elementSize);
        for (int i = 0; i < elementSize; i++) {
            //起始角度
            double startAngle = Math.random() * 45 + (i * 30);
            //运动速率
            double speed = 1500 + Math.random() * 200;
            Element element = new EruptionElement(startAngle, speed, bitmapProvider.getRandomBitmap());
            elements.add(element);
        }
        return elements;
    }


    public static class EruptionElement implements Element {
        private int x;
        private int y;
        private double angle;
        private double speed;
        private int alpha;
        /**
         * 重力加速度px/s
         */
        private static final float GRAVITY = 800;
        private Bitmap bitmap;

        public EruptionElement(double angle, double speed, Bitmap bitmap) {
            this.angle = angle;
            this.speed = speed;
            this.bitmap = bitmap;
        }

        @Override
        public int getAlpha() {
            return alpha;
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
            time = time / 1000;
            double x_speed = speed * Math.cos(angle * Math.PI / 180);
            double y_speed = -speed * Math.sin(angle * Math.PI / 180);
            x = (int) (start_x + (x_speed * time) - (bitmap.getWidth() / 2));
            y = (int) (start_y + (y_speed * time) + (GRAVITY * time * time) / 2 - (bitmap.getHeight() / 2));
            alpha = (int) (255 - 255 * time);
        }
    }
}
