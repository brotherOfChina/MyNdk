package com.example.myndk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.ExternalStorageStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;

public class MainActivity extends AppCompatActivity {
    //加密 解密
    //拆分 合并
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    RecyclerView rv1;
    RecyclerView rv2;
    Crypter crypter;
    TextView tv;
    private List<String> bodys = new ArrayList<>();
    private List<String> headers = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crypter = new Crypter();
// Example of a call to a native method
        tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "加密", Toast.LENGTH_LONG).show();
                Log.d("zpj", Environment.getExternalStorageDirectory().getPath());
                tv.setText(crypter.encrypt("/storage/emulated/0/DCIM/Camera/IMG_20190910_211510.jpg", "/storage/emulated/0/DCIM/Camera/IMG_20191008_100345.jpg"));
            }
        });
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "解密", Toast.LENGTH_LONG).show();
                crypter.decrypt("/storage/emulated/0/DCIM/Camera/IMG_20191008_100345.jpg", "/storage/emulated/0/DCIM/Camera/IMG_20191008_100346.jpg");
            }
        });
        initView();
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public void diff(View view) {
        try {

            FileUtils.split("/storage/emulated/0/DCIM/Camera/IMG_20190910_203618.jpg", 50000);

        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public void merge(View view) {
        FileUtils.merge("/storage/emulated/0/DCIM/Camera/IMG_20190910_203618.jpg");
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        for (int i = 0; i < 200; i++) {
            if (i % 20 == 0) {
                headers.add("" + i / 20);
            }
            bodys.add("" + i);
        }

        rv1 = findViewById(R.id.rv1);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv1.setLayoutManager(layoutManager);


        rv2 = findViewById(R.id.rv2);
        layoutManager2 = new LinearLayoutManager(this);
        rv2.setLayoutManager(layoutManager2);
        BgaAdapter bgaAdapter1 = new BgaAdapter(rv1, R.layout.adapter_item);
        rv1.setAdapter(bgaAdapter1);
        BgaAdapter bgaAdapter2 = new BgaAdapter(rv1, R.layout.adapter_item);
        rv2.setAdapter(bgaAdapter2);
        bgaAdapter1.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.btn_show) {
                    rv2.smoothScrollToPosition(position * 20);
                }

            }
        });
        bgaAdapter1.setData(headers);
        bgaAdapter2.setData(bodys);
        rv2.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int FirstCompletelyVisibleItemPosition = layoutManager2.findFirstCompletelyVisibleItemPosition();
                int LastCompletelyVisibleItemPosition = layoutManager2.findLastCompletelyVisibleItemPosition();
                int LastVisibleItemPosition = layoutManager2.findLastVisibleItemPosition();
                int FirstVisibleItemPosition = layoutManager2.findFirstVisibleItemPosition();
                if (FirstCompletelyVisibleItemPosition % 20 == 0) {
                    rv1.smoothScrollToPosition(FirstCompletelyVisibleItemPosition / 20);
                }
                Log.d("zpj",  "FirstCompletelyVisibleItemPosition:"+FirstCompletelyVisibleItemPosition );
                Log.d("zpj",  "LastCompletelyVisibleItemPosition:"+LastCompletelyVisibleItemPosition );
                Log.d("zpj",  "LastVisibleItemPosition:"+LastVisibleItemPosition );
                Log.d("zpj",  "FirstVisibleItemPosition:"+FirstVisibleItemPosition );

            }
        });
        rv1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
        rv2.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}
