package com.helixs.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.helixs.compat.Compat;
import com.helixs.tools.BuildConfig;
import com.helixs.tools.Pop;
import com.helixs.tools.Print;
import com.helixs.tools.ThreadUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by helixs on 2017/2/17.
 */

public final class Net {

	private static OkHttpClient okHttpClient;
	private static Gson gson;
	public static final String TAG = "NetWork";

	public static void init() {
		okHttpClient = new OkHttpClient();
		gson = new Gson();
	}


	public static <T> PostRequest<T> post(String url, Map<String, String> params, @NonNull
			Class<T> cls) {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		String jsons = gson.toJson(params);
		RequestBody body = RequestBody.create(JSON, jsons);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		return new PostRequest(cls, request);
	}

	public static <T> PostRequest<T> get(String url, Map<String, String> params, @NonNull
			Class<T> cls) {
		Print.d(TAG, "请求接口:" + url + ":" + params);
		HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
		if (params != null) {
			for (Map.Entry<String, String> e : params.entrySet()) {
				String key = e.getKey();
				if (key == null) {
					continue;
				}
				String value = e.getValue();
				if (value == null) {
					value = "";
				}
				builder.addQueryParameter(key, value);
			}
		}
		Request request = new Request.Builder()
				.url(builder.build()).get()
				.build();
		return new PostRequest<>(cls, request);
	}


	@NonNull
	public static Map<String, String> thinkPageParams() {
		Map<String, String> postParams = Compat.createMap();
		postParams.put("language", "zh-Hans");
		postParams.put("key", BuildConfig.TINK_PAGE_KEY);

		return postParams;
	}


	public interface NetCallBack<T> {
		void result(T t);

		void netFailure();

		void crash();
	}


	public static class PostRequest<T> {
		private final Class<T> cls;
		private final Request request;

		PostRequest(Class<T> cls, Request request) {
			this.cls = cls;
			this.request = request;
		}

		public void send(final NetCallBack<T> netCallBack) {
			Print.d("url:::::", request.url());
			okHttpClient.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					ThreadUtil.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							netCallBack.netFailure();
						}
					});
				}

				@Override
				public void onResponse(Call call, final Response response) throws IOException {
					if (!response.isSuccessful()) {
						ThreadUtil.runOnMainThread(new Runnable() {
							@Override
							public void run() {
								Pop.toast("网络错误码:" + response.code());
							}
						});
					}
					try {
						final T result;
						if (BuildConfig.DEBUG) {
							String str = response.body().string();
							Print.d(Net.TAG, "返回结果", cls.getSimpleName(), str);
							result = gson.fromJson(str, cls);
						} else {
							result = gson.fromJson(response.body().charStream(), cls);
						}
						ThreadUtil.runOnMainThread(new Runnable() {
							@Override
							public void run() {
								netCallBack.result(result);
							}
						});
					} catch (final IOException e) {
						Print.stackTrace(e);

						ThreadUtil.runOnMainThread(new Runnable() {
							@Override
							public void run() {
								Pop.toast("连接失败");
							}
						});
					} catch (JsonSyntaxException | JsonIOException e) {
						e.printStackTrace();
						ThreadUtil.runOnMainThread(new Runnable() {
							@Override
							public void run() {
								Pop.toast("json解析错误");
							}
						});
					} finally {
						response.body().close();
					}
				}
			}

		);
	}
}


}
