package com.pyxis.loquaciousdroid;

import android.view.View;
import android.widget.Button;

import static com.pyxis.loquaciousdroid.ViewFetcher.NO_RESULT_FOUND;
import static junit.framework.Assert.fail;

public class ButtonAction extends ViewAction {

    ViewFetcher viewFetcher;

    public ButtonAction(AndroidUser androidUser) {
        super(androidUser);
        viewFetcher = new ViewFetcher(androidUser);
    }

    public ButtonAction clicksTheButtonWithThisId(final int id) {
        waitForIdleSync();
        final View viewFound = androidUser.getActivity().findViewById(id);

        if (viewFound == NO_RESULT_FOUND || !(viewFound instanceof Button)) {
            fail("No button with this id : " + id + " was found");
        }

        Button button = (Button) viewFound;
        clickThis(button);

        return this;
    }

    public AndroidUser andThen() {
        return androidUser;
    }

    private void clickThis(final Button button) {
        runOnMainSync(
                new Runnable() {
                    public void run() {
                        button.performClick();
                    }
                }
        );
    }


}
