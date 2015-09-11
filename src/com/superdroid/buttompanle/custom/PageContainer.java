package com.superdroid.buttompanle.custom;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 存放四种页面(加载中，加载出错，内容为空，加载成功)的容器
 * 
 * @author Administrator
 *
 */
public class PageContainer extends FrameLayout {

	public PageContainer(Context context) {
		super(context);
		init();
	}

	private void init() {
//		this.setBackgroundColor(color);
	}

}
