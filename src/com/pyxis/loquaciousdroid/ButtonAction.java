package com.pyxis.loquaciousdroid;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;

import org.junit.Assert;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonAction {

	private final AndroidUser androidUser;
	ViewFetcher viewFetcher;
	
	public ButtonAction(AndroidUser androidUser){
		this.androidUser = androidUser;
		viewFetcher = new ViewFetcher(androidUser);
	}
	
	public ButtonAction clicksTheButtonWithThisId(final int id){
		
		final View viewFound = viewFetcher.getVisibleViewById(id);
		
		if (viewFound == NO_RESULT_FOUND || !(viewFound instanceof Button)) {
			Assert.fail("No button with this id : " + id + " was found");
		}
		
		Button button = (Button)viewFound;
		clickThis(button);
		
		return this;
	}
	
	public ButtonAction clicksTheButtonWithThisText(String text) {
		final TextView viewFound = viewFetcher.getVisibleViewMatchingThisText(text);
		
		if (viewFound == NO_RESULT_FOUND || !(viewFound instanceof Button)) {
			Assert.fail("No button with this text : " + text + " was found");
		}
		
		Button button = (Button)viewFound;
		clickThis(button);
		
		return this;
	}
	
	
	public AndroidUser andThen(){
		return androidUser;
	}
	
	private void clickThis(final Button button){
		androidUser.getActivity().runOnUiThread(
			      new Runnable() {
			        public void run() {
			        	 button.performClick();
			        } 
			      }
			    ); 
	}


	
	
	
}
