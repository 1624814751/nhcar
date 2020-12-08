package com.nhcar;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActivityHome extends TabActivity {

	// 声明控件、变量
	private RadioGroup mHome_radio_button_group;
	private TabHost mTabHost;

	private static final String TAB_MAIN = "MAIN";
	private static final String TAB_SEARCH = "SEARCH";
	private static final String TAB_CATEGORY = "CATEGORY";
	private static final String TAB_CART = "CART";
	private static final String TAB_PERSONAL = "PERSONAL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		initView();
		initData();
		initListener();

	}

	private void initView() {
		mHome_radio_button_group = (RadioGroup) this
				.findViewById(R.id.home_radio_button_group);
		mTabHost = getTabHost();
	}

	private void initData() {
		// 选项卡初始化
		// 1. 声明各选项卡对应跳转的意图对象
		Intent i_mainIntent = new Intent(getApplicationContext(),
				ActivityIndex.class);
		Intent i_searchIntent = new Intent(getApplicationContext(),
				ActivitySearch.class);
		Intent i_categotyIntent = new Intent(getApplicationContext(),
				ActivityCategory.class);
		Intent i_cartIntent = new Intent(getApplicationContext(),
				ActivityCar.class);
		Intent i_PersonalIntent = new Intent(getApplicationContext(),
				ActivityPersonal.class);

		// 2.添加选项卡
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
				.setContent(i_mainIntent));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH).setIndicator(TAB_MAIN)
				.setContent(i_searchIntent));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
				.setIndicator(TAB_CATEGORY).setContent(i_categotyIntent));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART)
				.setContent(i_cartIntent));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL)
				.setIndicator(TAB_PERSONAL).setContent(i_PersonalIntent));

		// 3.设置选项卡初始页
		mTabHost.setCurrentTabByTag(TAB_MAIN);
	}

	private void initListener() {
		mHome_radio_button_group
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						// arg1——表示选中的单选框的id
						switch (arg1) {
							case R.id.home_tab_main:
								mTabHost.setCurrentTabByTag(TAB_MAIN);
								break;
							case R.id.home_tab_search:
								mTabHost.setCurrentTabByTag(TAB_SEARCH);
								break;
							case R.id.home_tab_category:
								mTabHost.setCurrentTabByTag(TAB_CATEGORY);
								break;
							case R.id.home_tab_cart:
								mTabHost.setCurrentTabByTag(TAB_CART);
								break;
							case R.id.home_tab_personal:
								mTabHost.setCurrentTabByTag(TAB_PERSONAL);
								break;
							default:
								Toast.makeText(getApplicationContext(), "开发中……",
										Toast.LENGTH_SHORT).show();
								break;
						}
					}
				});
	}
}