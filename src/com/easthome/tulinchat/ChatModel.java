package com.easthome.tulinchat;

public class ChatModel {
	
	// ����ö��
	public static final int DIR_LEFT = 0;
	public static final int DIR_RIGHT = 1;
	
	public int direction;
	
	public String message;

	@Override
	public String toString() {
		return "ChatModel [direction=" + direction + ", message=" + message
				+ "]";
	}

	
}
