package com.roughike.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nic on 2/11/16.
 */

public abstract class BottomBarComponent extends LinearLayout {

	@VisibleForTesting
	static final String STATE_BADGE_COUNT = "STATE_BADGE_COUNT_FOR_TAB_";

	protected int iconResId;

	protected String title;
	protected TextView titleView;

	protected int badgeBackgroundColor;
	@VisibleForTesting
	BottomBarBadge badge;

	protected int indexInContainer;

	protected AppCompatImageView iconView;

	protected Type type = Type.FIXED;

	enum Type {
		FIXED, SHIFTING, TABLET
	}


	Type getType() {
		return type;
	}

	void setType(Type type) {
		this.type = type;
	}

	public BottomBarComponent(Context context) {
		super(context);
	}

	public BottomBarComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BottomBarComponent(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	int getBadgeBackgroundColor() {
		return badgeBackgroundColor;
	}

	void setBadgeBackgroundColor(int badgeBackgroundColor) {
		this.badgeBackgroundColor = badgeBackgroundColor;

		if (badge != null) {
			badge.setColoredCircleBackground(badgeBackgroundColor);
		}
	}


	public void setBadgeCount(int count) {
		if (count <= 0) {
			if (badge != null) {
				badge.removeFromTab(this);
				badge = null;
			}

			return;
		}

		if (badge == null) {
			badge = new BottomBarBadge(getContext());
			badge.attachToTab(this, badgeBackgroundColor);
		}

		badge.setCount(count);
	}

	public void removeBadge() {
		setBadgeCount(0);
	}



	int getIndexInTabContainer() {
		return indexInContainer;
	}

	void setIndexInContainer(int indexInContainer) {
		this.indexInContainer = indexInContainer;
	}


	@Override
	public Parcelable onSaveInstanceState() {
		if (badge != null) {
			Bundle bundle = saveState();
			bundle.putParcelable("superstate", super.onSaveInstanceState());
			return bundle;
		}

		return super.onSaveInstanceState();
	}

	@VisibleForTesting
	Bundle saveState() {
		Bundle outState = new Bundle();
		outState.putInt(STATE_BADGE_COUNT + getIndexInTabContainer(), badge.getCount());

		return outState;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			restoreState(bundle);
			state = bundle.getParcelable("superstate");
		}

		super.onRestoreInstanceState(state);
	}

	@VisibleForTesting
	void restoreState(Bundle savedInstanceState) {
		int previousBadgeCount = savedInstanceState.getInt(STATE_BADGE_COUNT + getIndexInTabContainer());
		setBadgeCount(previousBadgeCount);
	}


	AppCompatImageView getIconView() {
		return iconView;
	}

	int getIconResId() {
		return iconResId;
	}

	void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}


	String getTitle() {
		return title;
	}

	TextView getTitleView() {
		return titleView;
	}

	void setTitle(String title) {
		this.title = title;
	}

	protected abstract void prepareLayout();

}
