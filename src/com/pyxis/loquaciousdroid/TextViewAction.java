package com.pyxis.loquaciousdroid;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextViewAction {

	private TextView selectedTextView;
	private AndroidUser androidUser;
	private ViewFetcher viewFetcher;
	private TextViewClicker textViewClicker;
	
	public TextViewAction(AndroidUser androidUser) {
		this.androidUser = androidUser;
		viewFetcher = new ViewFetcher(androidUser);
		textViewClicker = new TextViewClicker(androidUser);
	}

	public TextViewAction looksAtThisTextView(int id) {
		selectedTextView = (TextView) viewFetcher.getVisibleViewById(id);
		return this;
	}

	private String getFailureMessage(String expectedText) {
		return "Text view with this id" + selectedTextView.getId()
				+ " did not contain this text : " + expectedText
				+ " it contained this text : "
				+ selectedTextView.getText().toString();
	}

	public AndroidUser andItShouldContainThisText(String expectedText) {

		if (expectedText == null
				&& selectedTextView.getText().toString() == null) {
			return androidUser;
		}

		try {
			assertTrue(getFailureMessage(expectedText), selectedTextView
					.getText().toString().contains(expectedText));
		} catch (NullPointerException e) {
			fail(getFailureMessage(expectedText));
		}

		return androidUser;
	}

	public AndroidUser clicksTheElementContainingThisText(final String text) {
		final TextView view = viewFetcher.getVisibleViewMatchingThisText(text);

		if (view == NO_RESULT_FOUND) {
			Assert.fail("No element containing this text : " + text + " was found");
		}

		textViewClicker.clickOnThis(view);

		return androidUser;
	}



}
