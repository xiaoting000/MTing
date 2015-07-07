package com.bzd.clientmyfcd.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import com.bzd.clientmyfcd.R;
import com.bzd.clientmyfcd.adapter.RecordsListViewAdapter;
import com.bzd.clientmyfcd.bean.ChatMessage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
	
	DbUtils db;
	RecordsListViewAdapter mRecordsAdapter;
	
	Socket clientS;
	DataInputStream dInputStream;
	DataOutputStream dOutputStream;
	
	Handler h;
	
	private void initRecordDataFromDb() throws DbException{
		db = DbUtils.create(this);
//		List<ChatMessage> records = db.findAll(ChatMessage.class);
//		mRecordsAdapter.add(records);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		mRecordsAdapter = new RecordsListViewAdapter(this);
		lv_records.setAdapter(mRecordsAdapter);
		
		h = new Handler(){
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 22:
					mRecordsAdapter.add((ChatMessage)msg.obj);
					break;

				default:
					break;
				}
			}
		};
		
		try {
			initRecordDataFromDb();
		} catch (DbException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					clientS = new Socket("172.16.1.109", 8899);
					dInputStream =  new DataInputStream(clientS.getInputStream());
					dOutputStream = new DataOutputStream(clientS.getOutputStream());
					while (true) {
						String s = dInputStream.readUTF();
						ChatMessage rC = new ChatMessage();
						rC.setContent(s);
						rC.setCreate_at(getDate());
						rC.setMsgType(true);
						rC.setType(1);
						Message  m = new Message();
						m.obj = rC;
						m.what = 22;
						h.sendMessage(m);
						try {
							db.save(rC);
						} catch (DbException e) {
							e.printStackTrace();
						}
						lv_records.setSelection(mRecordsAdapter.getCount());
						
						if (s.equals("[债见byebye]")) {
							break;
						}
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if (clientS != null) {
						try {
							dInputStream.close();
							dOutputStream.close();
							clientS.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
	}
	
	@OnClick(R.id.et_naqu)
	public void setRecordsBottom(View v){
		lv_records.setSelection(mRecordsAdapter.getCount());
	}
	
	@OnClick(R.id.bt_qu)
	public void sendWords(View v) throws DbException, IOException{
		if (et_naqu.getText().toString().equals("")) {
			return;
		}
		ChatMessage go = new ChatMessage();
		go.setCreate_at(getDate());
		go.setContent(et_naqu.getText().toString() + "");
		go.setType(1);
		go.setMsgType(false);
		mRecordsAdapter.add(go);
		
		db.save(go);
		
		dOutputStream.writeUTF(et_naqu.getText().toString() + "");
		
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
