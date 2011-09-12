package com.pyxis.loquaciousdroid;

import android.widget.ListView;
import android.widget.TextView;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.*;

public class ListViewActions {
	
	private ListView selectedListView;
	private AndroidUser androidUser;
	private static final int NOT_FOUND_INDEX = -1;
	private TextViewClicker textViewClicker;
	private ViewFetcher viewFetcher;
	public ListViewActions(AndroidUser androidUser){
		this.androidUser = androidUser;
		this.textViewClicker = new TextViewClicker(androidUser);
		this.viewFetcher = new ViewFetcher(androidUser);
	}

	public ListViewActions looksAtThisListView(final int id){
		selectedListView = ((ListView)(androidUser.getActivity().findViewById(id)));
		return this;
	}
	
	public ListViewActions andThen(){
		return this;
	}
	
	public AndroidUser andItShouldContainThisText(String text){
		//TODO Test for null
		boolean foundText = false;
		
		for (int i = 0; i < selectedListView.getCount(); i++) {
			Object objectDisplayed = selectedListView.getItemAtPosition(i);
			
			if(objectDisplayed.toString()!=null && objectDisplayed.toString().contains(text)){
				foundText = true;
				break;
			}
		}
		
		assertTrue("This text : " + text + "was not found in the spinner", foundText);
		
		return androidUser;
	}

	
	public AndroidUser andItShouldNotContainThisText(String text) {
		//TODO Test for null
		boolean foundText = false;
		
		for (int i = 0; i < selectedListView.getCount(); i++) {
			Object objectDisplayed = selectedListView.getItemAtPosition(i);
			
			if(objectDisplayed.toString()!=null && objectDisplayed.toString().contains(text)){
				foundText = true;
				break;
			}
		}
		
		assertFalse("The text : " + text + " was found in the spinner", foundText);
		
		return androidUser;
	}
	
	
	
	public AndroidUser andSelectsTheElementThatContainsThisText(String text) {
		int indexAtWhichTheElementWasFound = indexAtWhichThisTextIsFoundInTheSelectedListView(text);
		
		if(indexAtWhichTheElementWasFound != NOT_FOUND_INDEX){
			final int theIndexToClick =indexAtWhichTheElementWasFound;
			androidUser.getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					selectedListView.setSelection(theIndexToClick);
					selectedListView.performItemClick(selectedListView, 0, theIndexToClick);
				}
			});
		}else{
			fail("No element with this text : " + text + " was found");
		}
		
		return androidUser;
		
	}

	private int indexAtWhichThisTextIsFoundInTheSelectedListView(String text) {
		int indexAtWhichTheElementWasFound = NOT_FOUND_INDEX;
		
		for (int i = 0; i < selectedListView.getCount(); i++) {
			Object objectDisplayed = selectedListView.getItemAtPosition(i);
			
			if(objectDisplayed.toString()!=null && objectDisplayed.toString().contains(text)){
				indexAtWhichTheElementWasFound = i;
				break;
			}
		}
		return indexAtWhichTheElementWasFound;
	}
	
	
	public AndroidUser andPressesForALongTimeTheElementThatContainsThisText(String text){
		final TextView view = viewFetcher.getViewMatchingThisText(text);
		
		if(view != NO_RESULT_FOUND){
			textViewClicker.clickForALongTimeOnThis(view);
		}else{
			fail("No element with this text : " + text + " was found");
		}
		
		return androidUser;
		
	}
	
}
