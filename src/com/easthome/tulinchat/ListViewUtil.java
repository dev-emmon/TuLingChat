package com.easthome.tulinchat;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.AbsListView;

public class ListViewUtil {
	/**
	 * 滚动列表到顶端
	 *
	 * @param listView
	 */
	public static void smoothScrollListViewToTop(final AbsListView listView) {
		if (listView == null) {
			return;
		}
		smoothScrollListView(listView, 0);
		listView.postDelayed(new Runnable() {
			@Override
			public void run() {
				listView.setSelection(0);
			}
		}, 200);
	}

	/**
	 * 滚动列表到position
	 *
	 * @param listView
	 * @param position
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void smoothScrollListView(AbsListView listView, int position) {
		if (Build.VERSION.SDK_INT > 7) {
			listView.smoothScrollToPosition(position);
		} else {
			listView.setSelection(position);
		}
	}
}
