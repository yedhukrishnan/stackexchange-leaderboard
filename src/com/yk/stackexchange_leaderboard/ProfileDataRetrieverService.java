package com.yk.stackexchange_leaderboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.JSONParser;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ProfileDataRetrieverService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("leaderboard", "Started ProfileDataRetriever Service");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show();


		testUrlDataRead();


		return super.onStartCommand(intent, flags, startId);
	}

	private void testUrlDataRead() {
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

		scheduledThreadPoolExecutor.schedule(new Runnable() {

			@Override
			public void run() {
				try {
				Log.d("leaderboard", "Started Reading API");
				URL url;

				url = new URL("http://api.stackexchange.com/2.2/users/2139625;1329731;788574;455117;3833658;3804420;2983576?order=desc&sort=reputation&site=stackoverflow");

				HttpURLConnection request1;

				request1 = (HttpURLConnection) url.openConnection();

				request1.setRequestMethod("GET");

				request1.connect();

				InputStream is = request1.getInputStream();
				BufferedReader bf_reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = bf_reader.readLine()) != null) {
					sb.append(line).append("\n");
				}

				String responseBody = sb.toString();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(responseBody);
				Log.d("leaderboard", obj.toString());
				Log.d("leaderboard", "Print complete");
				} catch(Exception e) {
					e.printStackTrace();
				}

			}
		}, 5, TimeUnit.SECONDS);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}


}
