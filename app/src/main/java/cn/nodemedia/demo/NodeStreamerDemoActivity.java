package cn.nodemedia.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import cn.nodemedia.NodeStreamer;
import cn.nodemedia.NodeStreamerDelegate;

/**
 * 直播串流器使用实例
 * 可用于RTSP流转为RTMP直播流推送
 * 也可将本地MP4文件按实际帧率推送到RTMP服务器
 */
public class NodeStreamerDemoActivity extends AppCompatActivity implements NodeStreamerDelegate {
    EditText inEdt, outEdt;
    Button startBtn;
    NodeStreamer ns;
    boolean isStarting = false;
    EditText logET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_streamer_demo);

        inEdt = (EditText) findViewById(R.id.editText_input);
        outEdt = (EditText) findViewById(R.id.editText_output);
        startBtn = (Button) findViewById(R.id.button_streaming);
        logET = (EditText) findViewById(R.id.editText_ns_log);

        ns = new NodeStreamer(this);
        ns.setDelegate(this);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isStarting) {
                    ns.stopStreaming();
                } else {
                    ns.startStreaming(inEdt.getText().toString(), outEdt.getText().toString());
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ns.stopStreaming();
        ns.setDelegate(null);
    }

    @Override
    public void onEventCallback(NodeStreamer streamer, final int event, final String msg) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
                String sRecTime = sDateFormat.format(new java.util.Date());
                sb.append(sRecTime);
                sb.append(" - ");
                sb.append(event+" : ");
                sb.append(msg);
                sb.append("\r\n");
                logET.append(sb);
            }
        });


        Log.i("NodeMedia.NodeStreamer", "onEvent:" + event + " msg:" + msg);
        switch (event) {
            case 3000:
                //串流器开始打开输入地址
                break;
            case 3001:
                //串流器输入地址打开成功
                //串流器开始打开输出地址
                break;
            case 3002:
                //串流器输出地址打开成功,开始串流
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        startBtn.setText("Stop");
                    }
                });
                isStarting = true;
                break;
            case 3003:
                //串流器输入端打开失败
                break;
            case 3004:
                //串流器输出端打开失败
                break;
            case 3005:
                //串流中途网络异常中断
                break;
            case 3006:
                //串流结束
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        startBtn.setText("Streaming");
                    }
                });
                isStarting = false;
                break;
            default:
                break;
        }
    }
}
