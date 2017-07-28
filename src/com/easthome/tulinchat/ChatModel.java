package com.easthome.tulinchat;

public class ChatModel {
	
	// ·½ÏòÃ¶¾Ù
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
