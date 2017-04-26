package com.aegroto.climberball;
 
import com.jme3.app.DefaultAndroidProfiler;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Window;
import android.view.WindowManager;
import com.jme3.app.AndroidHarnessFragment;
import java.util.logging.Level;
import java.util.logging.LogManager;
import com.aegroto.climberball.R;
import java.util.concurrent.Callable;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.File;
 
public class MainActivity extends Activity /* implements RewardedVideoAdListener */ {
    /*
     * Note that you can ignore the errors displayed in this file,
     * the android project will build regardless.
     * Install the 'Android' plugin under Tools->Plugins->Available Plugins
     * to get error checks and code completion for the Android project files.
     */
 
    private final String APP_ID = "ca-app-pub-1805400128871765~2679350737",
                         rewardedVideoUnitId="ca-app-pub-1805400128871765/5910819939";
    
    private RewardedVideoAd rewardedVideoAd;

    public MainActivity(){
        // Set the default logging level (default=Level.INFO, Level.ALL=All Debug Info)
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        // Set window fullscreen and remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        // find the fragment
        FragmentManager fm = getFragmentManager();
        AndroidHarnessFragment jmeFragment =
                (AndroidHarnessFragment) fm.findFragmentById(R.id.jmeFragment); 
        
        Main.setAndroidLaunch(true);
        Main.initializeCacheAppState(getCacheDir().getAbsolutePath()+"/cache"); 
        
        // 97B98342FB4CF17F648567CD76F539A8
        
        /* MobileAds.initialize(this, APP_ID);        
        
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        
        Main.setAndroidLaunch(true);
        Main.initializeCacheManager(getCacheDir().getAbsolutePath()+"/cache"); 
        loadRewardedVideoAd();
        
        Main.setSecondChanceCallable(new Callable<Object>() {
           @Override
           public Object call() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {                
                        showRewardedVideo();
                    }
                });
                return null;
            }
        }); */

        // uncomment the next line to add the default android profiler to the project
        //jmeFragment.getJmeApplication().setAppProfiler(new DefaultAndroidProfiler());
    }
    
    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) 
            rewardedVideoAd.loadAd(rewardedVideoUnitId, new AdRequest.Builder()
                    .addTestDevice("97B98342FB4CF17F648567CD76F539A8")
                    .build());
        System.err.println("[ADS] Loading rewarded video");
    }
    
    private void showRewardedVideo() {        
        System.err.println("[ADS] Showing rewarded video");
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
            System.err.println("[ADS] Showed rewarded video");
        } else {
            loadRewardedVideoAd();
            System.err.println("[ADS] Unable to show rewarded video, loading a new one...");
        }
    }
    
    /* @Override
    public void onRewardedVideoAdLeftApplication() {
        System.out.println("[ADS] onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        System.out.println("[ADS] onRewardedVideoAdClosed");
        // Preload the next video ad.
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        System.out.println("[ADS] onRewardedVideoAdFailedToLoad " + errorCode);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        System.out.println("[ADS] onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        System.out.println("[ADS] onRewardedVideoAdOpened");
    }

    @Override
    public void onRewarded(RewardItem reward) {
        System.out.println(
                String.format("[ADS] onRewarded! currency: %s amount: %d", reward.getType(),
                        reward.getAmount()));
    }

    @Override
    public void onRewardedVideoStarted() {
        System.out.println("[ADS] onRewardedVideoStarted");
    }
    
    @Override
    public void onResume() {
        rewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    } */

    public static class JmeFragment extends AndroidHarnessFragment {
        public JmeFragment() {
            // Set main project class (fully qualified path)
            appClass = "com.aegroto.climberball.Main";
 
            // Set the desired EGL configuration
            eglBitsPerPixel = 24;
            eglAlphaBits = 0;
            eglDepthBits = 16;
            eglSamples = 0;
            eglStencilBits = 0;
 
            // Set the maximum framerate
            // (default = -1 for unlimited)
            frameRate = -1;
 
            // Set the maximum resolution dimension
            // (the smaller side, height or width, is set automatically
            // to maintain the original device screen aspect ratio)
            // (default = -1 to match device screen resolution)
            maxResolutionDimension = -1;
 
            // Set input configuration settings
            joystickEventsEnabled = false;
            keyEventsEnabled = true;
            mouseEventsEnabled = true;
 
            // Set application exit settings
            finishOnAppStop = true;
            handleExitHook = true;
            exitDialogTitle = "Do you want to exit?";
            exitDialogMessage = "Use your home key to bring this app into the background or exit to terminate it.";
 
            // Set splash screen resource id, if used
            // (default = 0, no splash screen)
            // For example, if the image file name is "splash"...
            //     splashPicID = R.drawable.splash;
            splashPicID = 0;
        }
    }
}
