package com.pyxis.loquaciousdroid;


import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import static junit.framework.Assert.*;

public class TextViewClicker {

    private final AndroidUser androidUser;

    public TextViewClicker(AndroidUser androidUser) {
        this.androidUser = androidUser;
    }

    public void clickOnThis(TextView view) {
        clickOnScreen(getXPositionOnScreen(view), getYPositionOnScreen(view));
    }

    public void clickForALongTimeOnThis(TextView view) {
        clickLongOnScreen(getXPositionOnScreen(view), getYPositionOnScreen(view), 4000);
    }

    private float getXPositionOnScreen(TextView view) {
        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        final int viewWidth = view.getWidth();
        final float x = xy[0] + (viewWidth / 2.0f);
        return x;
    }

    private float getYPositionOnScreen(TextView view) {
        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        final int viewHeight = view.getHeight();
        float y = xy[1] + (viewHeight / 2.0f);
        return y;
    }

    private void clickOnScreen(float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        MotionEvent event2 = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_UP, x, y, 0);
        try {
            androidUser.getInstrumentation().sendPointerSync(event);
            androidUser.getInstrumentation().sendPointerSync(event2);
        } catch (SecurityException e) {
            assertTrue("Click can not be completed! Something is in the way e.g. the keyboard.", false);
        }
    }

    private void clickLongOnScreen(float x, float y, int time) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        try {
            androidUser.getInstrumentation().sendPointerSync(event);
        } catch (SecurityException e) {
            assertTrue(
                    "Click can not be completed! Something is in the way e.g. the keyboard.",
                    false);
        }
        androidUser.getInstrumentation().waitForIdleSync();
        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_MOVE, x + ViewConfiguration.getTouchSlop()
                / 2, y + ViewConfiguration.getTouchSlop() / 2, 0);
        androidUser.getInstrumentation().sendPointerSync(event);
        androidUser.getInstrumentation().waitForIdleSync();
        if (time > 0) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
            }
        } else {
            try {
                Thread.sleep((int) (ViewConfiguration.getLongPressTimeout() * 1.5f));
            } catch (InterruptedException e) {
            }
        }
        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,
                x, y, 0);
        androidUser.getInstrumentation().sendPointerSync(event);
        androidUser.getInstrumentation().waitForIdleSync();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
        }

    }


}
