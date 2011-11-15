package com.pyxis.loquaciousdroid;

import android.R;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.fail;

public class ViewAction {

    private Instrumentation instrumentation;
    protected final AndroidUser androidUser;
    ViewFetcher viewFetcher;

    public ViewAction(AndroidUser androidUser) {
        this.androidUser = androidUser;
        this.instrumentation = androidUser.getInstrumentation();
        viewFetcher = new ViewFetcher(androidUser);
    }

    public ViewAction tapViewWithThisId(int id) {
        waitForIdleSync();
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
    
    
    public ViewAction shouldNotBeVisible(int viewId){
    	View view = viewFetcher.getViewByID(viewId);
		if(view != NO_RESULT_FOUND && view.isShown()){
    		fail("The requested view is visible");
    	}
    	return this;
    }

    protected void runOnMainSync(Runnable runnable){
        waitForIdleSync();
        androidUser.getInstrumentation().runOnMainSync(runnable);

    }

    protected void waitForIdleSync(){
        androidUser.getInstrumentation().waitForIdleSync();
    }

}
