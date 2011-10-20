package com.pyxis.loquaciousdroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class AndroidUser {

	private ActivityInstrumentationTestCase2<?> context;

	private ActivityMonitor activityMonitor;
	private Instrumentation instrumentation;
	private Activity originalActivity;
	private ArrayList<Activity> activities;
	private int timeout;

	public AndroidUser(Instrumentation instrumentation, Activity originalActivity) {
		this.instrumentation = instrumentation;
		this.originalActivity = originalActivity;

		activityMonitor = instrumentation.addMonitor((IntentFilter) null, null, false);
		activities = new ArrayList<Activity>();
		activities.add(originalActivity);
		timeout = 10000;
	}

	public AndroidUser andThen() {
		return this;
	}

	public Activity getActivity() {
		if ( null == activityMonitor.getLastActivity() ) {
			return originalActivity;
		}

		if ( !activities.contains(activityMonitor.getLastActivity()) ) {
			activities.add(activityMonitor.getLastActivity());
		}

		return activityMonitor.getLastActivity();
	}

	public Instrumentation getInstrumentation() {
		return this.instrumentation;
	}

	public ButtonAction clicksTheButtonWithThisId( final int id ) {
		return new ButtonAction(this).clicksTheButtonWithThisId(id);
	}

	public AndroidUser clicksTheElementsContainingThisText( final String text ) {
		return new TextViewAction(this).clicksTheElementContainingThisText(text);
	}

	public TextAction selectsTheTextFieldWithThisID( final int id ) {
		return new TextAction(this).selectsTheTextFieldWithThisID(id);
	}

	public ListViewActions looksAtThisListView( final int id ) {
		return new ListViewActions(this).looksAtThisListView(id);
	}

	public ViewAction tapViewWithThisId( final int id ) {
		return new ViewAction(this).tapViewWithThisId(id);
	}

	public void quits() {
        instrumentation.removeMonitor(activityMonitor);
		for (Activity activity : activities) {
			activity.finish();
		}
        activities.clear();
	}

	public AndroidUser waitsForThisActivityToShowUp( Class<?> activityToWaitFor ) throws InterruptedException {
		int elapsedTime = 0;
		while (!activityToWaitFor.getSimpleName().equals(getActivity().getClass().getSimpleName())) {

			if ( elapsedTime < timeout ) {
				Thread.sleep(200);
				elapsedTime += 200;
			} else
				fail(MessageFormat.format("The time allowed [{0} ms] for the activity {1} to appear has expired, current acitvity is [{2}]", timeout, activityToWaitFor.getName(), getActivity().getClass().getName()));
		}

		return this;
	}

	public TextViewAction looksAtThisTextView( int id ) {
		instrumentation.waitForIdleSync();
		return new TextViewAction(this).looksAtThisTextView(id);
	}

	public CheckBoxAction looksAtThisCheckbox( int id ) {
		instrumentation.waitForIdleSync();
		return new CheckBoxAction(this).looksAtThisCheckboxWithThisId(id);
	}

	public AndroidUser waitsForThisTextToDisappear( final String text ) throws InterruptedException {
		ViewFetcher viewFetcher = new ViewFetcher(this);

		boolean timeOutHasNotExpired = true;
		boolean elementIsStillVisible = true;
		int timeWeveWaitedForTheElementToDisappear = 0;

		while (timeOutHasNotExpired && elementIsStillVisible) {
			elementIsStillVisible = false;
			TextView foundTextView = viewFetcher.getViewMatchingThisText(text);

			if ( foundTextView != NO_RESULT_FOUND ) {
				elementIsStillVisible = true;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					timeOutHasNotExpired = false;
				}
				timeWeveWaitedForTheElementToDisappear += 500;
				if ( timeWeveWaitedForTheElementToDisappear > 3000 ) {
					timeOutHasNotExpired = false;
				}

			}
		}
		assertTrue("The timeout time allowed for the element with this text : " + text + " to disappear has expired", timeOutHasNotExpired);

		return this;
	}

	public AndroidUser waits() throws InterruptedException {
		Thread.sleep(3000);
		return this;
	}

	public AndroidUser waitsForThisTextToAppear( final String text ) throws InterruptedException {
		boolean timeOutHasExpired = false;
		boolean elementIsVisible = false;
		int timeWeveWaitedForTheElementToDisappear = 0;
		ViewFetcher viewFetcher = new ViewFetcher(this);

		instrumentation.waitForIdleSync();
		while (!timeOutHasExpired && !elementIsVisible) {

			TextView foundTextView = viewFetcher.getViewContainingThisText(text);

			if ( foundTextView != NO_RESULT_FOUND ) {
				elementIsVisible = true;
			}

			if ( timeWeveWaitedForTheElementToDisappear > timeout ) {
				timeOutHasExpired = true;
			} else if ( !elementIsVisible ) {
				sleepSilent(500);
				timeWeveWaitedForTheElementToDisappear += 500;
			}

		}

		assertTrue("The timeout time allowed for the element with this text : " + text + " to show up has expired", !timeOutHasExpired);
		return this;
	}

	public void waitsForThisActivityToFinish( final Class<? extends Activity> expectedClass ) {
		instrumentation.waitForIdleSync();
		for (int i = 0; i < timeout; i += 300) {
			if ( getActivity().getClass() != expectedClass || getActivity().isFinishing() )
				return;
			sleepSilent(300);
		}
		fail(MessageFormat.format("Timeout [{0} ms] for Activity [{1}] to finish  expired", timeout, expectedClass.getName()));
	}

	private void sleepSilent( long millis ) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public void shouldNotSeeView( int viewId ) {
		new ViewAction(this).shouldNotBeVisible(viewId);
	}

}
