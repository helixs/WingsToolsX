package com.helixs.ui.func;

import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;

import com.helixs.tools.BaseViewModel;

/**
 * Created by helixs on 2017/10/16.
 */
public class FucResUtilRes {

	public String content;
	@DrawableRes
	public int image;
	public static class FuncItemModel extends BaseViewModel {

		public ObservableField<String> content =  new ObservableField<>();
		public ObservableField<Integer> resourcesImage = new ObservableField<>();

		public void set(FucResUtilRes fucRes){
			content.set(fucRes.content);
			resourcesImage.set(fucRes.image);
		}
	}

}
