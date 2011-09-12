package com.pyxis.loquaciousdroid;

import android.view.View;
import android.widget.Button;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.fail;

public class ButtonAction {

	private final AndroidUser androidUser;
	ViewFetcher viewFetcher;
	
	public ButtonAction(AndroidUser androidUser){
		this.androidUser = androidUser;
		viewFetcher = new ViewFetcher(androidUser);
	}
	
	public ButtonAction clicksTheButtonWithThisId(final int id){
		
		final View viewFound = androidUser.getActivity().findViewById(id);
		
		if (viewFound == NO_RESULT_FOUND || !(viewFound instanceof Button)) {
			fail("No button with this id : " + id + " was found");
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
