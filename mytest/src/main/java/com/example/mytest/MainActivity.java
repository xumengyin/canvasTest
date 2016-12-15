package com.example.mytest;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> list = new ArrayList<>();
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        int i =3;
        //startActivity();

        adapter = new MyAdapter(this, list);
        //  recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int[] a = new int[2];
                int[] b = new int[2];
                recyclerView.getLocationOnScreen(a);
                recyclerView.getLocationInWindow(b);
                Logger.d("a1:" + a[0] + "a2:" + a[1] + "b1:" + b[0] + "b2:" + b[1]);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for ( i = 0; i < 100; i++) {
                    list.add("data " + i);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        Context ctx;
        List<String> data;
        RecyclerView re;

        public MyAdapter(Context ctx, List<String> data) {
            this.ctx = ctx;
            this.data = data;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            re = recyclerView;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(ctx).inflate(R.layout.item_layout, parent, false));
            Logger.i("onCreatrViewHolder....");
            return holder;
        }

        private void addItem(int position) {
            data.add(position, "insert" + position);
            notifyItemInserted(position);
            re.scrollToPosition(position);
        }

        private void removeItem(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.textView.setText(data.get(position));
            ViewGroup.LayoutParams params = holder.textView.getLayoutParams();
            params.height = (int) (100 + 1.5 * position);
            holder.textView.setLayoutParams(params);
            // StaggeredGridLayoutManager mamager;
            //mamager.find
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position % 2 == 0) {
                        addItem(position);

                    } else {
                        removeItem(position);
                    }
                }
            });
            Logger.d("onBindViewHolder");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        Drawable d;

        public MyItemDecoration(Context ctx) {

            TypedArray a = ctx.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
            d = a.getDrawable(0);
            a.recycle();
        }

        private void draw2(Canvas canvas, RecyclerView parent) {
            //int left=
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            for (int i = 0; i < parent.getLayoutManager().getChildCount(); i++) {
                final View child = parent.getChildAt(i);

                float left = child.getLeft() + (child.getRight() - child.getLeft()) / 4;
                float top = child.getTop() + (child.getBottom() - child.getTop()) / 2;
                float right = left + (child.getRight() - child.getLeft()) / 2;
                float bottom = top + 10;

                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        private void draw1(Canvas c, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                //  int left=child.getPaddingLeft();
                //   int right=child.getWidth()-child.getPaddingRight();
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottem = top + d.getIntrinsicHeight();
                // d.setBounds(left, top, right, bottem);
                d.setBounds(180, 0, 540, 5);
                d.draw(c);
            }

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition() == parent.getAdapter().getItemCount() - 1) {
                outRect.set(0, 0, 0, 0);

            } else {

                // outRect.set(0, 0, 0, d.getIntrinsicHeight());
                outRect.set(0, 0, 0, 0);
            }
            // outRect.set(0, 0, 0, 50);

        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            //   super.onDraw(c, parent, state);
            //  draw1(c, parent);
            draw2(c, parent);
        }
    }
}
