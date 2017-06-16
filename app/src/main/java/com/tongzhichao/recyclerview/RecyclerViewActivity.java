package com.tongzhichao.recyclerview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tongzhichao.example.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tongzhichao on 17-6-15.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DefaultAdapter defaultAdapter;

    private ArrayList<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        defaultAdapter = new DefaultAdapter();
        recyclerView.setAdapter(defaultAdapter);
        DefaultItemTouchHelper defaultItemTouchHelper = new DefaultItemTouchHelper(itemListener);
        defaultItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i + "");
        }
    }

    private DefaultItemTouchHelper.ItemListener itemListener = new DefaultItemTouchHelper.ItemListener() {
        @Override
        public void onMove(int move, int target) {
            Collections.swap(data, move, target);
            defaultAdapter.notifyItemMoved(move, target);
            Log.d("aaa", move + " : " + target);
        }

        @Override
        public void onSwiped(int position, boolean toright) {
            data.remove(position);
            defaultAdapter.notifyItemRemoved(position);
            Log.d("aaa", "swiped  " + position + " : " + toright);
        }
    };

    public class DefaultAdapter extends RecyclerView.Adapter<DefaultAdapter.DefaultHolder> {
        @Override
        public DefaultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DefaultHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(
                    R.layout.recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DefaultHolder holder, int position) {
            holder.setData(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class DefaultHolder extends RecyclerView.ViewHolder {
            private TextView name;
            private View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("recycler", "position:" + getAdapterPosition() + ",text:" + name.getText());
                }
            };

            public DefaultHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.item_name);
                itemView.setOnClickListener(onClickListener);

            }

            public void setData(String name) {
                this.name.setText(name);
            }

        }
    }
}
