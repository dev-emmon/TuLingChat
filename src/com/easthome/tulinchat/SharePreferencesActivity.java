package com.easthome.tulinchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharePreferencesActivity extends ActionBarActivity {
	
	private static final String SP_NAME = "sp";
	private static final String KEY_SAVE_STR = "strs";
	
	private TextView mText;
	private EditText mEtInput;
	private Button mBtnSave;
	private Button mBtnRead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shared_preferences);
		
		mText = (TextView) findViewById(R.id.tv_shared_preferences);
		mEtInput = (EditText) findViewById(R.id.et_shared_preferences);
		mBtnSave = (Button) findViewById(R.id.btn_shared_preferences_save);
		mBtnRead = (Button) findViewById(R.id.btn_shared_preferences_read);
		
		mBtnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveInfo(mEtInput.getText().toString());
				mEtInput.setText("");
			}
		});
		
		mBtnRead.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mText.setText(getSaveInfo());
			}
		});
		
	}
	
	/**
	 * 保存信息
	 * @param str
	 */
	private void saveInfo(String str) {
		SharedPreferences.Editor editor = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_SAVE_STR, str);
		editor.commit();
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 获取保存的信息
	 * @return
	 */
	private String getSaveInfo() {
		SharedPreferences sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(KEY_SAVE_STR, "");
	}

}
