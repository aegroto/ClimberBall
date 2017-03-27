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
import com.google.android.gms.ads.InterstitialAd;
import java.io.File;
 
public class MainActivity extends Activity {
    /*
     * Note that you can ignore the errors displayed in this file,
     * the android project will build regardless.
     * Install the 'Android' plugin under Tools->Plugins->Available Plugins
     * to get error checks and code completion for the Android project files.
     */
 
    private final String interstitialUnitId="ca-app-pub-1805400128871765/3794327135";
    private InterstitialAd interstitialAd;
    
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
        
        //97B98342FB4CF17F648567CD76F539A8
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(interstitialUnitId);
        
        loadInterstitialAd();
        
        Main.initializeCacheManager(getCacheDir().getAbsolutePath()+"/cache"); 
        
        /*Main.setSecondChanceCallable(new Callable<Object>() {
           @Override
           public Object call() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {                
                        
                    }
                });
                return null;
            }
        });*/

        // uncomment the next line to add the default android profiler to the project
        //jmeFragment.getJmeApplication().setAppProfiler(new DefaultAndroidProfiler());
    }
    
    private void loadInterstitialAd() {
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("97B98342FB4CF17F648567CD76F539A8")
                .build());        
    }
    
    private void showInterstitialAd() {
        if(interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        else {
            loadInterstitialAd();
            System.err.println("Interstitial not ready");
        }
    }

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
