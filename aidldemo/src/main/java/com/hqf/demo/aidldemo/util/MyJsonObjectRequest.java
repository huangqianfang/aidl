package com.hqf.demo.aidldemo.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangqianfang on 2017/7/20.
 * email huangqianfanghn@gmail.com
 */

public class MyJsonObjectRequest extends JsonObjectRequest {
	public MyJsonObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
	}


	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map map = new HashMap();
		map.put("Charset", "UTF-8");
		map.put("Content-Type", "application/x-javascript");
		map.put("Accept-Encoding", "gzip,deflate");
		map.put("app_type" , "3");
		map.put("os_type" , "android");
		map.put("osversion" , "5.1.1");
		map.put("appversion" , "1.0");
		map.put("devicefactory" , "huawei");

		return map;
	}

}
