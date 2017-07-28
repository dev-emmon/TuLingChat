package com.easthome.tulinchat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ConnectHttpService {

	public final static String TAG = "JSON";
	
	/**
	 * httpURLConnection 通过 Get 方法获取数据
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String connectService(String path) throws Exception {
		Log.i(TAG, path);
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();//开启一个http连接
		httpURLConnection.setReadTimeout(5000);//设置超时为5秒
		httpURLConnection.setRequestMethod("GET");

		InputStream is = httpURLConnection.getInputStream();
		InputStreamReader in = new InputStreamReader(is, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(in);
		String result = "";
		String readLine = null;
		while ((readLine = bufferedReader.readLine()) != null) {
			result += readLine;
		}
		in.close();
		httpURLConnection.disconnect();
		return result;
	}
	
	/**
	 * 解析接收到的数据
	 * @param strJson
	 * @param model
	 */
	public static void parseChatModel(String strJson, ChatModel model) {
		
		try {
			Log.i(TAG, strJson);
			JSONObject jsonObject = new JSONObject(strJson);
			model.message = jsonObject.optString("text");
			model.direction = ChatModel.DIR_LEFT;
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.e(TAG, "Error-jsonUser");
		}
	}
	
//	
//	public static List<String> getJsonArrayString(JSONArray jsonArray) {
//		
//		List<String> lists = new ArrayList<String>();
//		
//		if(jsonArray != null) {
//			for (int i = 0; i < jsonArray.length(); i++) {
//				lists.add(jsonArray.optString(i));
//			}
//		} else {
//			Log.i(TAG, "JsonArrayString-空");
//		}
//		
//		return lists;
//	}
//	
//	public static List<Map<String, Object>> getJsonArray(JSONArray jsonArray) {
//		
//		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
//		if(jsonArray != null) {
//			for (int i = 0; i < jsonArray.length(); i++) {
//				lists.add(getJsonObject(jsonArray.optJSONObject(i)));
//			}
//		} else {
//			Log.i(TAG, "JSONArray-空");
//		}
//		
//		return lists;
//	}
//	
//	public static Map<String, Object> getJsonObject(JSONObject jsonObject) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//		@SuppressWarnings("unchecked")
//		Iterator<String> set = jsonObject.keys();
//		try {
//			while (set.hasNext()) {
//                String key = set.next().toString();
//                map.put(key, jsonObject.optString(key));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.i(TAG, "JSONObject-空");
//		}
//		return map;
//	}
//	
//	/**
//	 * JSONObject
//	 * @param json
//	 * @return
//	 */
//	
//	private static Map<String, Object> JSONObjectParser(JSONObject jsonObject) {
//		
//		JSONArray names = jsonObject.names();
//		Map<String, Object> map = new HashMap<String, Object>();
//		for (int i = 0; i < names.length(); i++) {
//			String name;
//			Object object;
//			try {
//				name = (String)names.get(i);
//				object = jsonObject.get(name);
//				
//				if(object instanceof JSONObject) {
//					map.put(name, JSONObjectParser((JSONObject)object));
//				} else if (object instanceof JSONArray) {
//					map.put(name, JSONArrayParse((JSONArray)object));
//				} else {
//					map.put(name, object);
//				}
//			} catch (JSONException e) {
//				// TODO: handle exception
//			}
//		}
//		return map;
//	}
//	
//	private static List<Object> JSONArrayParse(JSONArray jsonArray) {
//		
//		List<Object> lists = new ArrayList<Object>();
//		for (int i = 0; i < jsonArray.length(); i++) {
//			try {
//				Object object = jsonArray.get(i);
//				if(object instanceof JSONObject) {
//					lists.add(JSONObjectParser((JSONObject)object));
//				} else if(object instanceof JSONArray) {
//					lists.add(JSONArrayParse((JSONArray)object));
//				} else {
//					lists.add(object);
//				}
//			} catch (JSONException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return lists;
//	}
}
