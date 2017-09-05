package com.roughike.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nic on 2/11/16.
 */

public class BottomBarButton extends BottomBarComponent {

	private int cornerRadius;
	private int iconColour;
	private int backgroundColor;

	BottomBarButton(Context context) {
		super(context);
	}


	public void setIconColor(Integer color) {
		this.iconColour = color;
	}

	public void setBackgroundColor(Integer color){
		this.backgroundColor = color;
	}

	public void setCornerRadius(Integer cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	@Override
	protected void prepareLayout() {
		int layoutResource;

		layoutResource = R.layout.bb_bottom_bar_button_item;

		inflate(getContext(), layoutResource, this);
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		iconView = (AppCompatImageView) findViewById(R.id.bb_bottom_bar_icon);
		iconView.setImageResource(iconResId);
		if(iconView != null && iconColour != 0){
			iconView.setColorFilter(iconColour);
		}

		GradientDrawable shape =  new GradientDrawable();
		shape.setCornerRadius(cornerRadius);
		shape.setColor(backgroundColor);
		if(Build.VERSION.SDK_INT >= 16){
			iconView.setBackground(shape);
		}else {
			iconView.setBackgroundDrawable(shape);
		}
		int eightdp = MiscUtils.dpToPixel(getContext(), 10);
		int fourdp = MiscUtils.dpToPixel(getContext(), 5);
		iconView.setPadding(eightdp, fourdp, eightdp, fourdp);
		iconView.setAlpha(1.0f);


		if (type != Type.TABLET) {
			titleView = (TextView) findViewById(R.id.bb_bottom_bar_title);
			titleView.setText(title);
		}
	}



}
