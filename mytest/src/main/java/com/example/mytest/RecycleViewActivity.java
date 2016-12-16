package com.example.mytest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * recycleview特殊效果练习
 */
public class RecycleViewActivity extends AppCompatActivity
{
	private static final String TAG = "RecycleViewActivity";
	RecyclerView mRecycleView;
	ReAdapter adapter;
	List<Data> list = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycle_view);
		initRecycle();
		initData();
	}

	private void initData()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 100; i++)
				{
					Data data = new Data();
					data.text = "data " + i;
					list.add(data);
				}
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						adapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	private void initRecycle()
	{
		mRecycleView = (RecyclerView) findViewById(R.id.recyclerview);
		mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		adapter = new ReAdapter(this, list);
		ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
		alphaAdapter.setDuration(1000);
		SlideInLeftAnimator animator = new SlideInLeftAnimator();
		mRecycleView.setItemAnimator(animator);
		mRecycleView.setAdapter(alphaAdapter);
	}

	static class ReViewHolder extends RecyclerView.ViewHolder
	{
		CheckBox checkBox;
		TextView textView;

		public ReViewHolder(View itemView)
		{
			super(itemView);
			checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
			textView = (TextView) itemView.findViewById(R.id.text);
		}
	}

	static class ReAdapter extends RecyclerView.Adapter<ReViewHolder>
	{
		Context ctx;
		List<Data> data;
		RecyclerView re;
		int lastPosition;
		public ReAdapter(Context ctx, List<Data> data)
		{
			this.ctx = ctx;
			this.data = data;
			//SortedList
		}

		private void addItem(int position)
		{
			Data newData = new Data();
			newData.text = "additem " + position;
			data.add(position,newData);
			notifyItemInserted(position);
			//re.scrollToPosition(position);
		}

		private void removeItem(int position)
		{
			data.remove(position);
			notifyItemRemoved(position);
		}

		@Override
		public void onAttachedToRecyclerView(RecyclerView recyclerView)
		{
			super.onAttachedToRecyclerView(recyclerView);
			re = recyclerView;
			Log.d(TAG, "onAttachedToRecyclerView: ");
			re.addOnScrollListener(new RecyclerView.OnScrollListener()
			{
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy)
				{
					super.onScrolled(recyclerView, dx, dy);
				}
			});
		}

		@Override
		public void onViewAttachedToWindow(ReViewHolder holder)
		{
			super.onViewAttachedToWindow(holder);
			Log.d(TAG, "onViewAttachedToWindow: ");
		}

		@Override
		public ReViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			ReViewHolder holder = new ReViewHolder(LayoutInflater.from(ctx).inflate(R.layout.recycle_item, parent,false));
			Log.d(TAG, "onCreateViewHolder: ");
			return holder;
		}

		@Override
		public void onBindViewHolder(final ReViewHolder holder, final int position)
		{

//			holder.itemView.setTranslationY(100);
//			holder.itemView.setAlpha(0);
//			holder.itemView.animate().alpha(1).translationY(0).setDuration(1000).start();
			final Data curData = data.get(position);
			holder.checkBox.setClickable(false);
			holder.checkBox.setChecked(curData.isCheck);
			holder.textView.setText(curData.text);
			holder.textView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//移除数据
					removeItem(holder.getAdapterPosition());
				}
			});
			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//增加数据
					//addItem(holder.getAdapterPosition());
					//notifyDataSetChanged();
					curData.isCheck=!curData.isCheck;
					holder.checkBox.setChecked(curData.isCheck);
					//notifyItemChanged(holder.getAdapterPosition());
				}
			});
			Log.d(TAG, "onBindViewHolder: ");
			lastPosition=position;
		}

		@Override
		public int getItemCount()
		{
			return data.size();
		}
	}

	static class Data
	{
		boolean isCheck;
		String text;
	}
}
