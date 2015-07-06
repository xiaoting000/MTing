package com.bzd.clientmyfcd.adapter;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bzd.clientmyfcd.R;
import com.bzd.clientmyfcd.bean.ChatMessage;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecordsListViewAdapter extends ItemAdapter<ChatMessage>{

    public RecordsListViewAdapter(Activity context, List<ChatMessage> items) {
    	super(context, items);
    }
    
    public RecordsListViewAdapter(Activity context) {
    	super(context);
    }

    private Date getDate() {
    	Date date = new Date(System.currentTimeMillis());
    	return date;
    }
    
	@Override
	protected ViewHolder newHolder() {
		return new ViewHolder(R.layout.contect_listview) {
			public TextView tvSendTime;
	        public TextView tvContentLeft;
	        public TextView tvContent_right;
	        public ImageView iv_chatcontent_left;
	        public ImageView iv_chatcontent_right;
	        
	        private RelativeLayout rl_left;
	        private RelativeLayout rl_right;
	        
			@Override
			protected void set(int position, boolean isSelected) {
		    	ChatMessage message = get(position);
		    	boolean isComMsg = message.getMsgType();
			    String time_that = new SimpleDateFormat("yyyy-MM-dd HH:mm",
						Locale.getDefault()).format(message.getCreate_at());
			    
			    String time_today = new SimpleDateFormat("yyyy-MM-dd HH:mm",
						Locale.getDefault()).format(getDate());
			    
			    String s0[] = time_that.split(" ");
			    String s1[] = time_today.split(" ");
			    
			    if (s0[0].equals(s1[0])) {
			    	tvSendTime.setText(s0[1]);
				}else {
					tvSendTime.setText(time_that);
				}
			    
			    if (message.getType() == 1) { //文字
			    	if (isComMsg) {
			    		rl_left.setVisibility(View.VISIBLE);
			    		rl_right.setVisibility(View.GONE);
			    		tvContentLeft.setText(message.getContent());
			    		tvContentLeft.setVisibility(View.VISIBLE);
			    		tvContent_right.setVisibility(View.GONE);
			    		iv_chatcontent_left.setVisibility(View.GONE);
					}else {
						rl_right.setVisibility(View.VISIBLE);
						rl_left.setVisibility(View.GONE);
						tvContent_right.setText(message.getContent());
						tvContent_right.setVisibility(View.VISIBLE);
						tvContentLeft.setVisibility(View.GONE);
						iv_chatcontent_right.setVisibility(View.GONE);
					}
				}else { //图片
					if (isComMsg) {
						rl_left.setVisibility(View.VISIBLE);
						rl_right.setVisibility(View.GONE);
//						ImageLoader.getInstance().displayImage(message.getContent(), iv_chatcontent_left);
						iv_chatcontent_left.setVisibility(View.VISIBLE);
						tvContentLeft.setVisibility(View.GONE);
					}else {
						rl_right.setVisibility(View.VISIBLE);
						rl_left.setVisibility(View.GONE);
//						ImageLoader.getInstance().displayImage(message.getContent(), iv_chatcontent_right);
						iv_chatcontent_right.setVisibility(View.VISIBLE);
						tvContent_right.setVisibility(View.GONE);
					}
				}
			}
			
			@Override
			protected void findView(View root) {
				tvSendTime = (TextView) root.findViewById(R.id.tv_sendtime);
				
				rl_left = (RelativeLayout) root.findViewById(R.id.rl_left);
				tvContentLeft = (TextView) root.findViewById(R.id.tv_chatcontent_left);
				iv_chatcontent_left = (ImageView) root.findViewById(R.id.iv_chatcontent_left);
				
				rl_right = (RelativeLayout) root.findViewById(R.id.rl_right);
				tvContent_right = (TextView) root.findViewById(R.id.tv_chatcontent_right);
				iv_chatcontent_right = (ImageView) root.findViewById(R.id.iv_chatcontent_right);
			}
		};
	}
}