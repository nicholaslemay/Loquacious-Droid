package com.pyxis.loquaciousdroid;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.fail;

public class ViewAction {

    private Instrumentation instrumentation;
    private final AndroidUser androidUser;
    ViewFetcher viewFetcher;

    public ViewAction(AndroidUser androidUser) {
        this.androidUser = androidUser;
        this.instrumentation = androidUser.getInstrumentation();
        viewFetcher = new ViewFetcher(androidUser);
    }

    public ViewAction tapViewWithThisId(int id) {
        final View view = androidUser.getActivity().findViewById(id);

        if (view == NO_RESULT_FOUND) {
            fail("No view with id : " + id + " was found");
        }

        tapThis(view);

        return this;
    }

    private void tapThis(final View view) {
        int[] xy = new int[2];
        view.getLocationOnScreen(xy);

        final int viewWidth = view.getWidth();
        final int viewHeight = view.getHeight();

        final float x = xy[0] + (viewWidth / 2.0f);
        float y = xy[1] + (viewHeight / 2.0f);

        Instrumentation inst = instrumentation;

        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();

        eventTime = SystemClock.uptimeMillis();
        final int touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE,
                x + (touchSlop / 2.0f), y + (touchSlop / 2.0f), 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();

        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();
    }


}
