package com.example.bottombar.sample;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnButtonClickListener;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by Nic on 6/09/17.
 */

public class EmptyTabActivity extends AppCompatActivity {
	private TextView messageView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_tab_activity);

		messageView = (TextView) findViewById(R.id.messageView);

		final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(@IdRes int tabId) {
				messageView.setText(TabMessage.get(tabId, false));
			}
		});

		bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
			@Override
			public void onTabReSelected(@IdRes int tabId) {
				Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
			}
		});
	}
}
