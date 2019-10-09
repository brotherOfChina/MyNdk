package com.example.myndk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //加密 解密
    //拆分 合并
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Crypter crypter;
    TextView tv;

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
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public void diff(View view) {
        FileUtils.split("/storage/emulated/0/DCIM/Camera/VID_20190910_225014.mp4", 1000000);
    }

    public void merge(View view) {
        FileUtils.merge("/storage/emulated/0/DCIM/Camera/VID_20190910_225014.mp4");
    }
}
