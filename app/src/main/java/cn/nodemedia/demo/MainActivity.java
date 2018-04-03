package cn.nodemedia.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button livePlayerBtn, livePublisherBtn, multiPlayerBtn, vodPlayerBtn, nodeStreamerBtn,vrPlayerBtn;
    EditText playUrl, pubUrl, bufferTime, maxBufferTime, vodPlayUrl, vodBufferTime, vodMaxBufferTime,vrPlayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Android 6.0及以上并且targetSdkVersion 23及以上,摄像头和麦克风权限需要用代码请求
        //这里使用第三方的权限管理库来请求
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                });
        livePlayerBtn = (Button) findViewById(R.id.button1);
        livePublisherBtn = (Button) findViewById(R.id.button2);
        multiPlayerBtn = (Button) findViewById(R.id.button3);
        nodeStreamerBtn = (Button) findViewById(R.id.button4);
        vodPlayerBtn = (Button) findViewById(R.id.button5);
        vrPlayerBtn = (Button)findViewById(R.id.button6);

        playUrl = (EditText) findViewById(R.id.editTextPlay);
        pubUrl = (EditText) findViewById(R.id.editTextPublish);
        bufferTime = (EditText) findViewById(R.id.editText_bufferTime);
        maxBufferTime = (EditText) findViewById(R.id.editText_maxBufferTime);
        vodPlayUrl = (EditText) findViewById(R.id.editText_vodPlayer);
        vodBufferTime = (EditText) findViewById(R.id.editText_vodBufferTime);
        vodMaxBufferTime = (EditText) findViewById(R.id.editText_vodMaxBufferTime);
        vrPlayUrl = (EditText) findViewById(R.id.editText_vrPlayer);

        playUrl.setText(SharedPreUtil.getString(this, "playUrl", "rtmp://xyplay.nodemedia.cn/live/stream_1001"));
        pubUrl.setText(SharedPreUtil.getString(this, "pubUrl", "rtmp://xypush.nodemedia.cn/live/stream_" + Math.round((Math.random() * 1000 + 1005))));
        bufferTime.setText(SharedPreUtil.getString(this, "bufferTime", "300"));
        maxBufferTime.setText(SharedPreUtil.getString(this, "maxBufferTime", "1000"));
        vodPlayUrl.setText(SharedPreUtil.getString(this, "vodPlayUrl", "http://vod.nodemedia.cn/qybs.flv"));
        vodBufferTime.setText(SharedPreUtil.getString(this, "vodBufferTime", "1000"));
        vodMaxBufferTime.setText(SharedPreUtil.getString(this, "vodMaxBufferTime", "20000"));
        vrPlayUrl.setText(SharedPreUtil.getString(this, "vrPlayUrl", "http://vod.nodemedia.cn/vrx.flv"));

        livePlayerBtn.setOnClickListener(this);
        livePublisherBtn.setOnClickListener(this);
        multiPlayerBtn.setOnClickListener(this);
        nodeStreamerBtn.setOnClickListener(this);
        vodPlayerBtn.setOnClickListener(this);
        vrPlayerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        SharedPreUtil.put(MainActivity.this, "playUrl", playUrl.getText().toString());
        SharedPreUtil.put(MainActivity.this, "bufferTime", bufferTime.getText().toString());
        SharedPreUtil.put(MainActivity.this, "maxBufferTime", maxBufferTime.getText().toString());
        SharedPreUtil.put(MainActivity.this, "pubUrl", pubUrl.getText().toString());
        SharedPreUtil.put(MainActivity.this, "vodPlayUrl", vodPlayUrl.getText().toString());
        SharedPreUtil.put(MainActivity.this, "vodBufferTime", vodBufferTime.getText().toString());
        SharedPreUtil.put(MainActivity.this, "vodMaxBufferTime", vodMaxBufferTime.getText().toString());
        SharedPreUtil.put(MainActivity.this, "vrPlayUrl", vrPlayUrl.getText().toString());

        switch (v.getId()) {
            case R.id.button1:
                // 记住上次播放配置的信息，只在demo中使用，非SDK方法
                MainActivity.this.startActivity(new Intent(MainActivity.this, LivePlayerDemoActivity.class));
                break;
            case R.id.button2:
                // 记住上次输入的发布地址，只在demo中使用，非SDK方法
                MainActivity.this.startActivity(new Intent(MainActivity.this, LivePublisherDemoActivity.class));
                break;
            case R.id.button3:
                MainActivity.this.startActivity(new Intent(MainActivity.this, MultiPlayerDemoActivity.class));
                break;
            case R.id.button4:
                MainActivity.this.startActivity(new Intent(MainActivity.this, NodeStreamerDemoActivity.class));
                break;
            case R.id.button5:
                MainActivity.this.startActivity(new Intent(MainActivity.this, VodPlayerDemoActivity.class));
                break;
            case R.id.button6:
                MainActivity.this.startActivity(new Intent(MainActivity.this, VRPlayerDemoActivity.class));
                break;
        }
    }
}
