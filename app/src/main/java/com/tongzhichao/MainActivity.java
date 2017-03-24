package com.tongzhichao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tongzhichao.circleprogress.CircleProgressActivity;
import com.tongzhichao.path.CircleActivity;
import com.tongzhichao.drag.DragActivity;
import com.tongzhichao.example.R;
import com.tongzhichao.path.CurveActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzc on 2017/3/20.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<ViewBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(new ListAdapter());
        initData();
    }

    private void initData(){
        mDataList.add(new ViewBean("Drag", DragActivity.class));
        mDataList.add(new ViewBean("Circle", CircleActivity.class));
        mDataList.add(new ViewBean("Progress", CircleProgressActivity.class));
        mDataList.add(new ViewBean("Curve", CurveActivity.class));
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListHolder(LayoutInflater.from(MainActivity.this).
                    inflate(R.layout.item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            holder.setData(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView item;
            private ViewBean bean;

            public ListHolder(View itemView) {
                super(itemView);
                item = (TextView) itemView.findViewById(R.id.item);
                itemView.setOnClickListener(this);
            }

            public void setData(ViewBean bean) {
                this.bean = bean;
                item.setText(bean.getName());
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, bean.getClassName());
                startActivity(intent);
            }
        }
    }

    private class ViewBean {
        private String name;
        private Class className;

        public ViewBean(String name, Class className) {
            this.name = name;
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getClassName() {
            return className;
        }

        public void setClassName(Class className) {
            this.className = className;
        }
    }
}
