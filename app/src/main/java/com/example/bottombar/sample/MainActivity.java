package com.example.bottombar.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.simple_three_tabs).setOnClickListener(this);
        findViewById(R.id.five_tabs_changing_colors).setOnClickListener(this);
        findViewById(R.id.three_tabs_quick_return).setOnClickListener(this);
        findViewById(R.id.five_tabs_custom_colors).setOnClickListener(this);
        findViewById(R.id.badges).setOnClickListener(this);
		findViewById(R.id.buttons).setOnClickListener(this);
		findViewById(R.id.empty).setOnClickListener(this);
		findViewById(R.id.five_tabs_with_badges).setOnClickListener(this);
		findViewById(R.id.switch_tabs).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class clazz = null;

        switch (v.getId()) {
            case R.id.simple_three_tabs:
                clazz = ThreeTabsActivity.class;
                break;
            case R.id.five_tabs_changing_colors:
                clazz = FiveColorChangingTabsActivity.class;
                break;
            case R.id.three_tabs_quick_return:
                clazz = ThreeTabsQRActivity.class;
                break;
            case R.id.five_tabs_custom_colors:
                clazz = CustomColorAndFontActivity.class;
                break;
            case R.id.badges:
                clazz = BadgeActivity.class;
                break;
			case R.id.buttons:
				clazz = ButtonTabActivity.class;
				break;
			case R.id.empty:
				clazz = EmptyTabActivity.class;
				break;
			case R.id.five_tabs_with_badges:
				clazz = FiveTabsWithBadgesActivity.class;
				break;
			case R.id.switch_tabs:
				clazz = ChangeTabsActivity.class;
				break;
        }

        startActivity(new Intent(this, clazz));
    }
}
