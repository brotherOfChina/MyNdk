package com.example.myndk;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

public class BgaAdapter extends BGARecyclerViewAdapter<String> {


    public BgaAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public void setOnItemChildCheckedChangeListener(BGAOnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        super.setOnItemChildCheckedChangeListener(onItemChildCheckedChangeListener);

    }

    @Override
    public void setOnItemChildClickListener(BGAOnItemChildClickListener onItemChildClickListener) {
        super.setOnItemChildClickListener(onItemChildClickListener);

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.btn_show);
    }

    public BgaAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.setText(R.id.btn_show, model);
    }
}
