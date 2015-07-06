package com.bzd.clientmyfcd.activity;

import java.util.Date;

import com.bzd.clientmyfcd.R;
import com.bzd.clientmyfcd.R.id;
import com.bzd.clientmyfcd.R.layout;
import com.bzd.clientmyfcd.adapter.RecordsListViewAdapter;
import com.bzd.clientmyfcd.bean.ChatMessage;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

@ContentView(R.layout.records_activity)
public class ClientMainActivity extends Activity{

	@ViewInject(R.id.lv_records)
	ListView lv_records;
	@ViewInject(R.id.iv_add)
	ImageView iv_add;
	@ViewInject(R.id.et_naqu)
	EditText et_naqu;
	
	RecordsListViewAdapter mRecordsAdapter;
	
	private void initRecordDataFromDb(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		mRecordsAdapter = new RecordsListViewAdapter(this);
		lv_records.setAdapter(mRecordsAdapter);
		
		ChatMessage tes = new ChatMessage();
		tes.setCreate_at(getDate());
		tes.setContent("???");
		tes.setType(1);
		tes.setMsgType(true);
		
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
		mRecordsAdapter.add(tes);
	}
	
	@OnClick(R.id.et_naqu)
	public void setRecordsBottom(View v){
		lv_records.setSelection(mRecordsAdapter.getCount());
	}
	
	@OnClick(R.id.bt_qu)
	public void sendWords(View v){
		if (et_naqu.getText().toString().equals("")) {
			return;
		}
		ChatMessage go = new ChatMessage();
		go.setCreate_at(getDate());
		go.setContent(et_naqu.getText().toString() + "");
		go.setType(1);
		go.setMsgType(false);
		mRecordsAdapter.add(go);
		
		lv_records.setSelection(mRecordsAdapter.getCount());
		et_naqu.setText("");
	}

	@OnClick(R.id.iv_add)
	public void sendPic(){
	}
	
	private Date getDate() {
    	Date date = new Date(System.currentTimeMillis());
    	return date;
    }
}
