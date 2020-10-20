package com.nhcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Type: MyListView 让ListView可以做ScrollView的子控件，但尺寸不会减小
 *        这样做的目的是为了不让xml文件报警告(到最后还是妥协了)
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}