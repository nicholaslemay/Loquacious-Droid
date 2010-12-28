package com.pyxis.loquaciousdroid;

import android.widget.TextView;

public class TextAction {
	
	AndroidUser androidUser;
	private TextView selectedTextView;
	
	
	public TextAction(AndroidUser androidUser){
		this.androidUser = androidUser;
	}
	
	
	public TextAction selectsTheTextFieldWithThisID(final int id){
		androidUser.getActivity().runOnUiThread(
			      new Runnable() {
			        public void run() {
			        	selectedTextView = ((TextView)(androidUser.getActivity().findViewById(id)));
			        	selectedTextView.performClick();
			        } 
			      }
			    ); 

		return this;
	}
	
	public AndroidUser fillsItWithThisText(final String text){
		
		androidUser.getActivity().runOnUiThread(
			      new Runnable() {
			        public void run() {
			        	selectedTextView.setText(text);
			        } 
			      }
			    ); 
		
		return androidUser;
	}
	
	public TextAction andThen(){
		return this;
	} 
	
}
