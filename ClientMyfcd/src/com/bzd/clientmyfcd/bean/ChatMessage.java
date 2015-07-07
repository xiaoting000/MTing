package com.bzd.clientmyfcd.bean;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Transient;

public class ChatMessage {
	@Transient
	private int id;
	private int sender;
	private int receiver;
    private Date create_at;
    private String content;
    private int type;
    private boolean isComMeg = true;

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
    
    public void setReceiver(int receiver){
		this.receiver = receiver;
	}
	
	public int getReceiver(){
		return receiver;
	}

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }
}
