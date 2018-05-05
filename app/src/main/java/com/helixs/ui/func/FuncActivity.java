package com.helixs.ui.func;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.helixs.adapter.BaseRecycleAdapter;
import com.helixs.base.BaseActivity;
import com.helixs.compat.Res;
import com.helixs.tools.R;
import com.helixs.ui.func.binding.ItemFuncBinding;
import com.helixs.ui.main.WeatherActivity;
import com.helixs.ui.sensor.SensorListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helixs on 2017/8/15.
 */

public class FuncActivity extends BaseActivity {

	private int[] funcContents = {R.string.sensor, R.string.weather};
	private int[] funcIcons = {R.mipmap.ic_launcher,R.mipmap.ic_launcher};



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funcs);
		List<FucResUtilRes> fucList= new ArrayList<>();
		for (int i = 0; i < funcContents.length; i++) {
			FucResUtilRes fucRes = new FucResUtilRes();
			fucRes.content = Res.getString(funcContents[i]);
			fucRes.image = funcIcons[i];
			fucList.add(fucRes);
		}
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcy);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		ItemFuncAdapter itemFuncAdapter  =  new ItemFuncAdapter(this,R.layout.item_func);
		recyclerView.setAdapter(itemFuncAdapter);
		itemFuncAdapter.setList(fucList);
		itemFuncAdapter.notifyDataSetChanged();

	}
	public static class ItemFuncAdapter extends BaseRecycleAdapter<FucResUtilRes,ItemFuncBinding> {

		public ItemFuncAdapter(Context context, int layout) {
			super(context, layout);
		}

		@Override
		protected void bindViewHolder(ItemFuncBinding b, int position, final FucResUtilRes fucRes) {
			b.setInfo(new FucResUtilRes.FuncItemModel());
			b.getInfo().set(fucRes);
			b.getRoot().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (fucRes.content){
						case "天气":
							v.getContext().startActivity(new Intent(v.getContext(), WeatherActivity.class));
							break;
						case "传感器":
							v.getContext().startActivity(new Intent(v.getContext(), SensorListActivity.class));
							break;
					}
				}
			});
		}
	}







}
