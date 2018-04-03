package cn.nodemedia.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.MediaController;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerView;

/**
 * 点播播放实例,函数说明请参考LivePlayerDemoActivity
 */

public class VodPlayerDemoActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{
    NodePlayer np;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod_player_demo);

        np = new NodePlayer(this);

        NodePlayerView npv = (NodePlayerView)findViewById(R.id.vod_player_view);
        npv.setUIViewContentMode(NodePlayerView.UIViewContentMode.ScaleAspectFit);
        np.setPlayerView(npv);


        //媒体播放控制器,播放\暂停\快进\时间条等
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.mediaController));


        String url =  SharedPreUtil.getString(this, "vodPlayUrl");
        np.setInputUrl(url);

        /**
         * 设置启动缓冲区时长,单位毫秒.
         */
        int bufferTime = Integer.valueOf(SharedPreUtil.getString(this, "vodBufferTime"));
        np.setBufferTime(bufferTime);

        /**
         * 设置最大缓冲区时长,单位毫秒.
         * 点播视频如果不控制最大缓冲,会自动下载所有数据到本地缓存,若看一半不看了,流量就浪费了.
         * 设置一个最大缓冲时长,有利于带宽利用
         */
        int maxBufferTime = Integer.valueOf(SharedPreUtil.getString(this, "vodMaxBufferTime"));
        np.setMaxBufferTime(maxBufferTime);

        np.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 点击显示区域显示控制条
         */
        mediaController.show();
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        np.stop();
        np.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        np.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        np.start();
    }
    @Override
    public void start() {
        np.start();
    }

    @Override
    public void pause() {
        np.pause();
    }

    @Override
    public int getDuration() {
        return (int) np.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return (int) np.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        np.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return np.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return np.getBufferPercentage();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
