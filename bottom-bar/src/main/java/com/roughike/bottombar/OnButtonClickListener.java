package com.roughike.bottombar;

import android.support.annotation.IdRes;

/**
 * Created by Nic on 2/11/16.
 */

public interface OnButtonClickListener {

	/**
	 * The method being called when currently visible {@link BottomBarTab} is
	 * reselected. Use this method for scrolling to the top of your content,
	 * as recommended by the Material Design spec
	 *
	 * @param tabId the {@link BottomBarTab} that was reselected.
	 */
	void onButtonClicked(@IdRes int tabId);

}
