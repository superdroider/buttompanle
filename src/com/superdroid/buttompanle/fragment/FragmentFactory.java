package com.superdroid.buttompanle.fragment;

import android.util.SparseArray;

public class FragmentFactory {

	private static SparseArray<BaseFragment> fragments = new SparseArray<BaseFragment>();

	// private static Map<Integer, BaseFragment> fragments = new
	// HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {

		BaseFragment fragment = fragments.get(position);
		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AroundFragment();
				break;
			case 2:
				fragment = new MineFragment();
				break;
			case 3:
				fragment = new MoreFragment();
				break;
			}
			fragments.put(position, fragment);
		}
		return fragment;
	}

}
