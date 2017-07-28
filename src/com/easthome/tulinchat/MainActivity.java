package com.easthome.tulinchat;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements Callback {
	
	private static final String TAG = "MainActivity";
	
	private static final int MSG_ERROR = 0;
	private static final int MSG_SUCCESS = 1;
	
	// 数据库
	DBManager mDbManager;
	
	// 通信
	private ConnectHttpService mHttpService;
	private Handler mHandler;
	
	private ListView listview;
	private ChatAdapter mAdapter;
	private List<ChatModel> models;
	
	private EditText etIput;
	private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDbManager = new DBManager(this);
		mHandler = new Handler(this);
		mHttpService = new ConnectHttpService();
		
		mAdapter = new ChatAdapter(this);
		models = new ArrayList<ChatModel>();
		
		listview = (ListView) findViewById(R.id.lv_main);
		etIput = (EditText) findViewById(R.id.et_main);
		btnSend = (Button) findViewById(R.id.btn_main);
		
		listview.setAdapter(mAdapter);
		
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = etIput.getText().toString().trim();
				if (!text.equals("")) {
					sendMessae(text);
				} else {
					Toast.makeText(MainActivity.this, "请输入消息", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * 发送消息
	 * @param value
	 */
	private void sendMessae(final String value) {
		
		etIput.setText("");
		// 添加自己发送的消息
		ChatModel meModel = new ChatModel();
		meModel.message = value;
		meModel.direction = ChatModel.DIR_RIGHT;
		models.add(meModel);
		updateList();
		
		// 获取回复的消息
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					ChatModel model = new ChatModel();
					String json = mHttpService.connectService(Const.URL + "?key=" + Const.KEY + "&info=" + value);
					ConnectHttpService.parseChatModel(json, model);
					if (model != null) {
						models.add(model);
					}
					msg.what = MSG_SUCCESS;
				} catch (Exception e) {
					msg.what = MSG_ERROR;
					e.printStackTrace();
				} finally {
					mHandler.sendMessage(msg);
				}
	
			};
		}.start();
	}
	
	/**
	 * 更新列表
	 */
	private void updateList() {
		mAdapter.update(models);
		ListViewUtil.smoothScrollListView(listview, listview.getCount() - 1);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_SUCCESS:
			updateList();
			break;
		case MSG_ERROR:
			Toast.makeText(this, "网络错误！", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			return true;
		case R.id.action_shared_preferences:
			Intent intent = new Intent(MainActivity.this, SharePreferencesActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_read:
			List<ChatModel> list = mDbManager.query();
			models.clear();
			models.addAll(list);
			Log.i(TAG, list.toString());
			updateList();
			return true;
		case R.id.action_save:
			mDbManager.bulkInsert(models);
			return true;
		case R.id.action_delete:
			mDbManager.deleteAll();
			return true;
		case R.id.action_claer:
			models.clear();
			updateList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
