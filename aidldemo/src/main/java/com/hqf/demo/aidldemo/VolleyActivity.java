package com.hqf.demo.aidldemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huangqianfang on 2017/7/20.
 * email huangqianfanghn@gmail.com
 */

public class VolleyActivity extends Activity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		RequestQueue mQueue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest("http://1hu9lai.cn",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG", response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
		});
//	String url = "http://1hu9lai.cn";
//
//		StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,  listener, errorListener) {
//			@Override
//			protected Map<String, String> getParams() throws AuthFailureError {
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("params1", "value1");
//				map.put("params2", "value2");
//				return map;
//			}
//		};

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id" , 2);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		JsonArrayRequest jsonObjectRequest = new JsonArrayRequest("http://www.1hu9lai.cn:8780/api/liusen_manager/getAllMsg",
				jsonObject,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d("TAG" , response.toString());

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		try {
			jsonObjectRequest.getHeaders();
		} catch (AuthFailureError authFailureError) {
			authFailureError.printStackTrace();
		}
//		可以看到，这里我们填写的URL地址是http://m.we
		mQueue.add(jsonObjectRequest);
	}
}
