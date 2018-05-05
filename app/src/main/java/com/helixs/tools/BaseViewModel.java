package com.helixs.tools;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by helixs on 2017/2/28.
 */

public class BaseViewModel extends BaseObservable {

	@BindingAdapter({"imageUrl", "errorRes"})
	public static void loadImage(ImageView imageView, String url, Drawable errorRes) {
		if(url == null || url.trim().length() == 0){
//			ImageLoadHelper.cancelLoad(imageView);
			imageView.setImageDrawable(errorRes);
		}else {
//			ImageLoadHelper.load(imageView, url,errorRes);
		}
	}

	@BindingAdapter({"imageUrl"})
	public static void loadImage(ImageView imageView, String url) {
		loadImage(imageView,url,null);
	}

}
