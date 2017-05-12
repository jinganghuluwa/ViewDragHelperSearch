package com.tongzhichao.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-4-11.
 */

public class ScrollActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setAdapter(new ScrollAdapter());
    }

    class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ScrollHolder> {
        @Override
        public ScrollHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ScrollHolder(new TextView(ScrollActivity.this));
        }

        @Override
        public void onBindViewHolder(ScrollHolder holder, int position) {
            holder.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ScrollHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            ScrollHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(50);
            }

            void setText(String name) {
                textView.setText(name);
            }
        }
    }
}
