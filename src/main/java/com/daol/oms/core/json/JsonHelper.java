package com.daol.oms.core.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
* Json 관련 유틸
* @author alarm
* @since 2018-01-12
*/
public class JsonHelper {
    
	/**
	* Java -> Json
	* @param object
	* @return
	* @throws JSONException
	*/
	@SuppressWarnings("unchecked")
	public static Object toJSON(Object object) throws JSONException {
		if (object instanceof Map) {
			JSONObject json = new JSONObject();
			Map<String, Object> map = (Map<String, Object>) object;
			for (Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			JSONArray json = new JSONArray();
			for (Object value : ((Iterable<Object>)object)) {
				json.add(value);
			}
			return json;
		} else {
			return object;
		}
	}
	
	/**
	* 빈 Json객체인지 검사
	* @param object
	* @return
	*/
	public static boolean isEmptyObject(JSONObject object) {
		return object.names() == null;
	}
	
	/**
	* 특정 KEY에 대한 JSON -> Map
	* @param object
	* @param key
	* @return
	* @throws JSONException
	*/
	public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
		return toMap(object.getJSONObject(key));
	}
	
	/**
	* JSON -> Map
	* @param object
	* @return
	* @throws JSONException
	*/
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> keys = object.keys();
		String key = null;
		Object obj = null;
		while (keys.hasNext()) {
			key = keys.next();
			obj = object.get(key);
			if(obj instanceof JSONObject && ((JSONObject)obj).isNullObject()){
				map.put(key, null);
			}else{
				map.put(key, fromJson(obj));
			}
		}
		return map;
	}
	
	/**
	 * Json -> List
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}
	
	/**
	 * Json -> Java
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	private static Object fromJson(Object json) throws JSONException {
		if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}
}