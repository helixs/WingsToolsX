package com.helixs.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helixs.tools.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helixs on 2017/8/15.
 */

public abstract class BaseRecycleAdapter<T1,T2 extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecycleAdapter.BaseViewHolder>{
	private List<T1> mlist;
	private LayoutInflater mInflater;
	@LayoutRes
	private int mLayout;

	public BaseRecycleAdapter(Context context, int layout) {
		this.mLayout = layout;
		this.mInflater = LayoutInflater.from(context);
		this.mlist = new ArrayList<>();
	}

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(mLayout,parent,false);
		return new BaseViewHolder(view);
	}

	@Override
	public void onBindViewHolder(BaseViewHolder holder, int position) {
		bindViewHolder((T2) holder.getBinding(),position,mlist.get(position));

	}

	protected abstract void bindViewHolder(T2 b, int position, T1 t1);

	@Override
	public int getItemCount() {
		if (!CollectionUtil.isEmpty(mlist)){
			return mlist.size();
		}
		return 0;
	}

	public void setList(List<T1> list) {
		this.mlist = list;
	}

	public static class BaseViewHolder extends RecyclerView.ViewHolder{

		private ViewDataBinding binding;


		public BaseViewHolder(View itemView) {
			super(itemView);
			binding =  DataBindingUtil.bind(itemView);
		}

		public ViewDataBinding getBinding(){
			return binding;
		}
	}
}
