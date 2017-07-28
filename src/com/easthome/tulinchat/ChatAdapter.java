package com.easthome.tulinchat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ChatModel> list;
	
	public ChatAdapter(Context context) {
		this(context, new ArrayList<ChatModel>());
	}
	
	public ChatAdapter(Context context, List<ChatModel> models) {
		this.mContext = context;
		this.list = new ArrayList<ChatModel>();
		if (models != null) {
			this.list.clear();
			this.list.addAll(models);
		}
	}
	
	public void update(List<ChatModel> models) {
		try {
			list.clear();
			notifyDataSetChanged();
			list.addAll(models);
			notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = LinearLayout.inflate(mContext, R.layout.item_chat, null);
			
			hodler.vgLeft = (ViewGroup) convertView.findViewById(R.id.vg_item_left);
			hodler.vgRight = (ViewGroup) convertView.findViewById(R.id.vg_item_right);
			hodler.tvLeft = (TextView) convertView.findViewById(R.id.tv_item_left);
			hodler.tvRight = (TextView) convertView.findViewById(R.id.tv_item_right);
			
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}
		
		ChatModel model = (ChatModel) getItem(position);
		hodler.vgLeft.setVisibility(View.GONE);
		hodler.vgRight.setVisibility(View.GONE);
		
		if (model.direction == ChatModel.DIR_LEFT) {
			hodler.vgLeft.setVisibility(View.VISIBLE);
			hodler.tvLeft.setText(model.message);
		} else {
			hodler.vgRight.setVisibility(View.VISIBLE);
			hodler.tvRight.setText(model.message);
		}
		
		return convertView;
	}
	
	/**
	 * 
	 * @author Doots
	 *
	 */
	class ViewHodler {
		
		public ViewGroup vgLeft;
		public ViewGroup vgRight;
		
		public TextView tvLeft;
		public TextView tvRight;
	}
	

}
