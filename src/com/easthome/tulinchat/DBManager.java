package com.easthome.tulinchat;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	
	private DBHelper mDbHelper;
	
	private static final String TAB_CHAT = "chat";
	public static final String CREATE_TAB_CHAT = 
			"create table " + TAB_CHAT + " (" +
			"_id integer primary key autoincrement, " +
			"direction integer, " +
			"message text" + 
			")";

	public DBManager(Context context) {
		mDbHelper = new DBHelper(context);
	}
	

	/**
	 * ��������
	 * @param models
	 */
	public void bulkInsert(List<ChatModel> models) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// �ֶ����ÿ�ʼ����
		db.beginTransaction(); 
		for (int i = 0; i < models.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("direction", models.get(i).direction);
			values.put("message", models.get(i).message);
			db.insert(TAB_CHAT, null, values);
		}
		db.setTransactionSuccessful(); // ����������ɹ��������û��Զ��ع����ύ
		db.endTransaction(); // �������
		db.close();
	}
	
	/**
	 * ��ѯ����
	 * @return
	 */
	public List<ChatModel> query() {
		List<ChatModel> models = new ArrayList<ChatModel>();
		
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.query(TAB_CHAT, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			ChatModel model = new ChatModel();
			model.direction = cursor.getInt(cursor.getColumnIndex("direction"));
			model.message = cursor.getString(cursor.getColumnIndex("message"));
			models.add(model);
		}
		cursor.close();
		db.close();
		return models;
	}
	
	/**
	 * ɾ��������
	 * @return
	 */
	public int deleteAll() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int row = db.delete(TAB_CHAT, null, null);
        db.close();
        return row;
	}

}
