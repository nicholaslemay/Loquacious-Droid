package com.pyxis.loquaciousdroid;

import android.widget.TextView;
import org.junit.Assert;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		selectedTextView = (TextView) viewFetcher.getViewByID(id);
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
		final TextView view = viewFetcher.getViewMatchingThisText(text);

		if (view == NO_RESULT_FOUND) {
			Assert.fail("No element containing this text : " + text + " was found");
		}

		textViewClicker.clickOnThis(view);

		return androidUser;
	}



}
