package com.helixs.net;

import com.helixs.model.BaseRes;

/**
 * Created by helixs on 2017/2/21.
 */

public abstract class AppNetCallBack<T> implements Net.NetCallBack<T> {
	@Override
	public void result(T o) {
		onCallBack();
		if (BaseRes.class.isInstance(o)) {
			BaseRes baseRes = (BaseRes) o;
			ok((T) baseRes);
		}else{
			ok(o);
		}
	}

	@Override
	public void netFailure() {
		onCallBack();

	}

	@Override
	public void crash() {
		onCallBack();

	}

	public void onCallBack() {

	}
	public abstract void ok(T t);
}
