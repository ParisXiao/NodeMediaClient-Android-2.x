package cn.nodemedia.demo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;

import com.asha.vrlib.MDVRLibrary;

import cn.nodemedia.NodePlayer;

import static com.asha.vrlib.MDVRLibrary.DISPLAY_MODE_GLASS;
import static com.asha.vrlib.MDVRLibrary.DISPLAY_MODE_NORMAL;

public class VRPlayerDemoActivity extends AppCompatActivity {
    private MDVRLibrary mVRLibrary;
    private NodePlayer np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrplayer_demo);

        np = new NodePlayer(this);
        // init VR Library
        initVRLibrary();

        np.setBufferTime(1000);
        np.setMaxBufferTime(10000);

        String playUrl = SharedPreUtil.getString(this, "vrPlayUrl");
        np.setInputUrl(playUrl);
        np.start();
    }

    private void initVRLibrary(){
        // new instance
        mVRLibrary = MDVRLibrary.with(this)
                .displayMode(DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_MOTION_WITH_TOUCH)
                .asVideo(new MDVRLibrary.IOnSurfaceReadyCallback() {
                    @Override
                    public void onSurfaceReady(Surface surface) {
                        //设置vr渲染视图
                        np.setVRPlayerView(surface);
                    }
                })
                .build(findViewById(R.id.vr_surface_view));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mVRLibrary.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVRLibrary.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVRLibrary.onDestroy();
        np.stop();
        np.release();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mVRLibrary.onOrientationChanged(this);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ) {
            mVRLibrary.switchDisplayMode(this,DISPLAY_MODE_NORMAL);
        }else {
            mVRLibrary.switchDisplayMode(this,DISPLAY_MODE_GLASS);
        }
    }
}
