package com.pyxis.loquaciousdroid;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFetcher {
	
	
	private final AndroidUser androidUser;
	private final JayWayViewFetcher jayWayViewFetcher;
	public  static final View NO_RESULT_FOUND = null;
	
	
	public ViewFetcher(AndroidUser androidUser){
		this.androidUser = androidUser;
		this.jayWayViewFetcher = new JayWayViewFetcher(androidUser);
	}
	
	public View getViewByID(int id){
        ArrayList <View> viewList = jayWayViewFetcher.getAllViews(false);

		for (View textView : viewList){
			if(textView.getId() == id){
				return textView;
			}
		}

		return NO_RESULT_FOUND;
    }

	public TextView getViewMatchingThisText(String text){
		ArrayList <View> viewList = jayWayViewFetcher.getAllViews(false);
		for (View view : viewList){
			if(view instanceof TextView && ((TextView)view).getText().toString().equals(text)){
				return ((TextView)view);
			}
		}
		
		return null;
	}
	


}
