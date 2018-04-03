package cn.nodemedia.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerDelegate;
import cn.nodemedia.NodePlayerView;

import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 直播多播实例,函数说明请参考LivePlayerDemoActivity
 */
public class MultiPlayerDemoActivity extends AppCompatActivity implements NodePlayerDelegate {
    private NodePlayer npB, npS, npP;
    private NodePlayerView svB, svS, svP;
    private ToggleButton tbB, tbS, tbP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_player_demo);

        svB = (NodePlayerView) findViewById(R.id.surfaceView_b);
        svS = (NodePlayerView) findViewById(R.id.surfaceView_s);
        svP = (NodePlayerView) findViewById(R.id.surfaceView_p);

        tbB = (ToggleButton) findViewById(R.id.toggleButton1);
        tbS = (ToggleButton) findViewById(R.id.toggleButton2);
        tbP = (ToggleButton) findViewById(R.id.toggleButton3);


        npB = new NodePlayer(this);
        npS = new NodePlayer(this);
        npP = new NodePlayer(this);

        npB.setPlayerView(svB);
        npS.setPlayerView(svS);
        npP.setPlayerView(svP);

        npB.setNodePlayerDelegate(this);
        npS.setNodePlayerDelegate(this);
        npP.setNodePlayerDelegate(this);



        npB.setBufferTime(500);
        npS.setBufferTime(500);
        npP.setBufferTime(500);

        npB.setMaxBufferTime(1000);
        npS.setMaxBufferTime(1000);
        npP.setMaxBufferTime(1000);


        npB.setInputUrl("rtmp://xyplay.nodemedia.cn/live/stream_1001");
        npS.setInputUrl("rtmp://xyplay.nodemedia.cn/live/stream_1002");
        npP.setInputUrl("rtmp://xyplay.nodemedia.cn/live/stream_1003");

        tbB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                //设置该路流是否打开音频
                npB.setAudioEnable(arg1);
            }
        });

        tbS.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                //设置该路流是否打开音频
                npS.setAudioEnable(arg1);
            }
        });

        tbP.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                //设置该路流是否打开音频
                npP.setAudioEnable(arg1);
            }
        });

        npB.start();
        npP.start();
        npS.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        npB.stop();
        npS.stop();
        npP.stop();
        npB.release();
        npS.release();
        npP.release();
    }

    @Override
    public void onEventCallback(NodePlayer player, int event, String msg) {
        if (player.equals(npB)) {
            Log.d("NodePlayer", "npB event:" + event + " msg:" + msg);
        } else if (player.equals(npS)) {
            Log.d("NodePlayer", "npS event:" + event + " msg:" + msg);
        } else if (player.equals(npP)) {
            Log.d("NodePlayer", "npP event:" + event + " msg:" + msg);
        }
    }

}
