package com.dali.admin.livestreaming.http;

import android.content.Context;
import android.util.Log;

import com.dali.admin.livestreaming.LiveApplication;
import com.dali.admin.livestreaming.http.request.IRequest;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.utils.LogDebugUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @description: 网络管理类
 */
public class AsyncHttp {
	private static final String TAG = AsyncHttp.class.getName();
	private static Gson mGson = null;
	private static AsyncHttp mInstance;

	private CookieJarImpl mCookieJar = new CookieJarImpl(new PersistentCookieStore(LiveApplication.getInstance()));

	//初始化操作，设置超时时间
	private OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
			.readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
			.cookieJar(mCookieJar)
			.build();

	//私有构造函数，初始化参数
	private AsyncHttp() {
		if (mGson == null) {
			mGson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//					.registerTypeAdapter(RowInfo.class, new RowInfoDeserializer())
					.create();
		}
		//单例模式
		OkHttpUtils.initClient(okHttpClient);
	}

	//单例模式
	public static AsyncHttp instance() {
		if (mInstance == null) {
			synchronized (OkHttpUtils.class) {
				if (mInstance == null) {
					mInstance = new AsyncHttp();
				}
			}
		}
		return mInstance;
	}

	public void post(IRequest request, IHttpListener listener) {
		Log.e(TAG, "post: url=" + request.getUrl());
		if (request != null) {
			request.getParams()
					.getPostFormBuilder()
					.url(request.getUrl())
					.id(request.getRequestId())
					.build()
					.execute(new ResponseCallback(listener, request.getParserType()));
		} else {
			throw new RuntimeException("Request param is null");
		}
	}

	//get 请求
	public void get(IRequest request, IHttpListener listener) {
		LogDebugUtil.e(TAG, "postForm: url=" + request.getUrl());
		if (request != null) {
//			RequestParams params = request.getParams();
//			GetBuilder getBuilder = params.getGetBuilder();
//			RequestCall build = getBuilder.url(request.getUrl()).id(request.getRequestId()).build();
//			build.execute(new ResponseCallback(listener,request.getParserType()));

			request.getParams()
					.getGetBuilder()
					.url(request.getUrl())
					.id(request.getRequestId())
					.build()
					.execute(new ResponseCallback(listener, request.getParserType()));

		} else {
			throw new RuntimeException("Request param is null");
		}
	}

	//postForm form请求
	public void postForm(IRequest request, IHttpListener listener) {
		LogDebugUtil.e(TAG, "postForm: url=" + request.getUrl());
		if (request != null) {

//			RequestParams params = request.getParams();
//			PostFormBuilder postFormBuilder = params.getPostFormBuilder();
//			RequestCall call = postFormBuilder.url(request.getUrl()).id(request.getRequestId()).build();
//			call.execute(new ResponseCallback(listener,request.getParserType()));


			request.getParams()
					.getPostFormBuilder()
					.url(request.getUrl())
					.id(request.getRequestId())
					.build()
					.execute(new ResponseCallback(listener, request.getParserType()));
		} else {
			throw new RuntimeException("Request param is null");
		}
	}

	//postForm json
	public void postJson(IRequest request, IHttpListener listener) {
		Log.e("imLogin","postFo url=" + request.getUrl());
		if (request != null) {

//			RequestParams params = request.getParams();
//			PostStringBuilder postJsonBuilder = params.getPostJsonBuilder();
//			RequestCall call = postJsonBuilder.url(request.getUrl()).id(request.getRequestId()).build();
//			call.execute(new ResponseCallback(listener,request.getParserType()));

			if (request.cleanCookie()){
				mCookieJar.getCookieStore().removeAll();
			}
			request.getParams()
					.getPostJsonBuilder()
					.url(request.getUrl())
					.id(request.getRequestId())
					.build().execute(new ResponseCallback(listener, request.getParserType()));
		} else {
			throw new RuntimeException("Request param is null");
		}
	}

	private void addHeader(Context context) {
	/*	String token = PreManager.instance(context).getToken();
		if (token != null) {
			mHttpClient.addHeader("token", token);
		}*/
	}

	public void cancelRequest(String tag) {
		if (tag != null) {
			OkHttpUtils.getInstance().cancelTag(tag);
		}
	}

	/**
	 * 请求状态的接口
	 */
	public interface IHttpListener {
		void onStart(int requestId);

		void onSuccess(int requestId, Response response);

		void onFailure(int requestId, int httpStatus, Throwable error);
	}

	abstract class StringCallback extends Callback<String> {
		@Override
		public String parseNetworkResponse(okhttp3.Response response, int id) throws IOException {
			return response.body().string();
		}
	}

	/**
	 * 网络请求返回接口
	 */
	class ResponseCallback extends Callback<Response> {
		private IHttpListener mHttpListener;
		private Type mParserType;

		public ResponseCallback(IHttpListener httpListener, Type parserType) {
			mHttpListener = httpListener;
			mParserType = parserType;
		}

		@Override
		public void onBefore(Request request, int id) {
			if (mHttpListener != null) {
				mHttpListener.onStart(id);
			}
		}

		@Override
		public Response parseNetworkResponse(okhttp3.Response response, int id) throws Exception {
			LogDebugUtil.e(TAG, "parseNetworkResponse: ");
			Response responseData = null;
			if (mHttpListener != null && response != null) {
				if (response.isSuccessful()) {
					String content = response.body().string();
					if (content != null) {
						try {
							LogDebugUtil.e(TAG, "onSuccess: " + content);
							Log.e("imLogin","JSON: " + content);
							responseData = mGson.fromJson(content,mParserType);
						} catch (JsonSyntaxException e) {
							onError(null,e,id);
						}
					}
				} else {
					onError(null, new Exception("net error"),id);
				}
			}
			return responseData;
		}

		@Override
		public void onError(Call call, Exception e, int id) {
			if (mHttpListener != null) {
				if (!call.isCanceled()) {
					mHttpListener.onFailure(id, 0, e);
				}
			}
		}

		@Override
		public void onResponse(Response response, int id) {
			if (mHttpListener != null) {
				mHttpListener.onSuccess(id, response);
			}
		}
	}


}
