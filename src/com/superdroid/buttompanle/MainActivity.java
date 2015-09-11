package com.superdroid.buttompanle;

import com.superdroid.buttompanle.custom.BottomItemView;
import com.superdroid.buttompanle.fragment.FragmentFactory;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	private BottomItemView home;
	private BottomItemView around;
	private BottomItemView mine;
	private BottomItemView more;
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		registeListener();
		home.setSelected(true);
		fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.frag_content, FragmentFactory.createFragment(0));
		transaction.commit();
	}

	private void initView() {
		home = (BottomItemView) findViewById(R.id.home);
		around = (BottomItemView) findViewById(R.id.around);
		mine = (BottomItemView) findViewById(R.id.mine);
		more = (BottomItemView) findViewById(R.id.more);
	}

	private void registeListener() {
		home.setOnClickListener(this);
		around.setOnClickListener(this);
		mine.setOnClickListener(this);
		more.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		resetSelectStatu();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		mine.showCirclePointBadge();
		switch (v.getId()) {
		case R.id.home:
			
			home.setSelected(!home.isSelected());
			transaction.replace(R.id.frag_content, FragmentFactory.createFragment(0));
			break;
		case R.id.around:
			around.setSelected(!mine.isSelected());
			transaction.hide(FragmentFactory.createFragment(0));
			transaction.replace(R.id.frag_content, FragmentFactory.createFragment(1));
			break;
		case R.id.mine:
			mine.setSelected(!mine.isSelected());
			mine.hiddenBadge();
			transaction.replace(R.id.frag_content, FragmentFactory.createFragment(2));
			break;
		case R.id.more:
			more.setSelected(!more.isSelected());
			transaction.replace(R.id.frag_content, FragmentFactory.createFragment(3));
			break;
		}
		transaction.commit();
	}

	private void resetSelectStatu() {
		home.setSelected(false);
		around.setSelected(false);
		mine.setSelected(false);
		more.setSelected(false);
	}
}
