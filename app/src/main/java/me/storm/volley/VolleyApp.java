/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package me.storm.volley;

import android.app.Application;

public class VolleyApp extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        RequestManager.init(this);
    }

}
