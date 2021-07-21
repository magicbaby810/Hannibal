package com.sk.hannibal;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;



import java.net.MulticastSocket;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;



/**
 * @author sk on 2018/11/23.
 * Email: magicbaby810@gmail.com
 */
public class AppApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
