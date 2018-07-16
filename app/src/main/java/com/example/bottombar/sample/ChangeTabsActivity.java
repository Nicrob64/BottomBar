package com.example.bottombar.sample;

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

public class ChangeTabsActivity extends AppCompatActivity {

	private boolean fiveTabs = true;
	private BottomBar bottomBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_tabs);

		bottomBar = (BottomBar) findViewById(R.id.bottomBar);

		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(int tabId) {
				if(tabId == R.id.tab_nearby){
					toggleTabs();
				}
			}
		});

		bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
			@Override
			public void onTabReSelected(@IdRes int tabId) {
				Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
				if(tabId == R.id.tab_nearby){
					toggleTabs();
				}
			}
		});
	}

	private void toggleTabs(){
		fiveTabs = !fiveTabs;
		bottomBar.setItems(fiveTabs ? R.xml.bottombar_tabs_five : R.xml.bottombar_tabs_three);
	}

}
