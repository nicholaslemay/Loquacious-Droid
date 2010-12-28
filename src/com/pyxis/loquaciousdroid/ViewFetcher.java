package com.pyxis.loquaciousdroid;

import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;

public class ViewFetcher {
	
	
	private final AndroidUser androidUser;
	private final JayWayViewFetcher jayWayViewFetcher;
	public  static final View NO_RESULT_FOUND = null;
	
	
	public ViewFetcher(AndroidUser androidUser){
		this.androidUser = androidUser;
		this.jayWayViewFetcher = new JayWayViewFetcher(androidUser);
	}
	
	
	public View getVisibleViewById(int id){
		ArrayList <TextView> textViewList = jayWayViewFetcher.getCurrentViews(TextView.class);
		//textViewList = removeInvisibleViews(textViewList);

		for (TextView textView : textViewList){
			if(textView.getId() == id){
				return textView;
			}
		}
		
		return NO_RESULT_FOUND;
	}
	
	public TextView getVisibleViewMatchingThisText(String text){
		ArrayList <TextView> textViewList = jayWayViewFetcher.getCurrentViews(TextView.class);
		for (TextView textView : textViewList){
			if(textView.getText().toString().equals(text)){
				return textView;
			}
		}
		
		return null;
	}
	
	
	private static <T extends View> ArrayList<T> removeInvisibleViews( ArrayList<T> viewList) {
		ArrayList<T> tmpViewList = new ArrayList<T>(viewList.size());
		for (T view : viewList) {
			if (view != null && view.isShown()) {
				tmpViewList.add(view);
			}
		}
		return tmpViewList;
	}
	
	
	
	
//	public static ArrayList<TextView> getAllCurrentlyVisibleTextViews(){
//		ArrayList<TextView> allCurrentlyVisibleTextViews = new ArrayList<TextView>();
//		
//		ArrayList<View> allVisibleViews =  getAllCurrentlyVisibleViews();
//		for (View view : allVisibleViews) {
//			if( TextView.class.isAssignableFrom(view.getClass())){
//				allCurrentlyVisibleTextViews.add((TextView)view);
//			}
//		}
//		return allCurrentlyVisibleTextViews;
//		
//	}
	
	
//	public static ArrayList<View> getAllCurrentlyVisibleViews() {
//		
//		final ArrayList<View> allVisibleViews = new ArrayList<View>();
//		
//		final View[] viewsFoundByTheWindowsManager = getAllViewsFromWindowsManager();
//			
//		
//		if (viewsFoundByTheWindowsManager != null && viewsFoundByTheWindowsManager.length > 0) {
//			int length = viewsFoundByTheWindowsManager.length;
//			for (int i = length - 1; i >= 0; i--) {
//				try {
//					if (viewsFoundByTheWindowsManager[i].isShown())
//						addChildrenOfTheViewGroupToTheViews(allVisibleViews, (ViewGroup) viewsFoundByTheWindowsManager[i]);
//				} catch (Exception ignored) {
//				}
//			}
//		}
//		return allVisibleViews;
//	}
//	
//	
//	private static void addChildrenOfTheViewGroupToTheViews(ArrayList<View> views, ViewGroup viewGroup) {
//		for (int i = 0; i < viewGroup.getChildCount(); i++) {
//			final View child = viewGroup.getChildAt(i);
//
//			views.add(child);
//
//			if (child instanceof ViewGroup) {
//				addChildrenOfTheViewGroupToTheViews(views, (ViewGroup) child);
//			}
//		}
//	}
//	
//	
//	
//	
//	private static Class<?> windowManager;
//	
//	static {
//		try {
//			windowManager = Class.forName("android.view.WindowManagerImpl");
//
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	private static View[] getAllViewsFromWindowsManager() {
//
//		Field viewsField;
//		Field instanceField;
//		try {
//			
//			viewsField = windowManager.getDeclaredField("mViews");
//			instanceField = windowManager.getDeclaredField("mWindowManager");
//			viewsField.setAccessible(true);
//			instanceField.setAccessible(true);
//			Object instance = instanceField.get(null);
//		
//			return (View[]) viewsField.get(instance);
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return null;
//	}
}
