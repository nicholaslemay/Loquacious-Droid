package com.pyxis.loquaciousdroid;

import android.widget.CheckBox;
import static junit.framework.Assert.*;

public class CheckBoxAction {

	AndroidUser androidUser;
	private CheckBox selectedCheckBox;

	public CheckBoxAction(AndroidUser androidUser) {
		this.androidUser = androidUser;
	}

	public CheckBoxAction looksAtThisCheckboxWithThisId( final int id ) {
		selectedCheckBox = ((CheckBox)(androidUser.getActivity().findViewById(id)));
		return this;
	}

	public CheckBoxAction thenTogglesIt() {
		androidUser.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				selectedCheckBox.toggle();
			}
		});

		return this;
	}

	public CheckBoxAction andItShouldBeChecked() {
		androidUser.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				assertTrue(selectedCheckBox.isChecked());
			}
		});
		return this;
	}

	public CheckBoxAction andItShouldNotBeChecked() {
		androidUser.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				assertFalse(selectedCheckBox.isChecked());
			}});
		return this;
	}

	public AndroidUser andThen() {
		return androidUser;
	}

}
