package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

public class AutoUpdate extends AsyncTask<Void, Boolean, Void> {
	

	@Override
	protected Void doInBackground(Void... params) {
		platformRequest();
		return null;
	}
	
    public String platformRequest()
    {
    	if (Option.Instance().getVersionLink() == null)
    		return null;
//        PowerManager pm = (PowerManager) StaticResources.getBaseContext().getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//             getClass().getName());
//        wl.acquire();
        
        try {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(Option.Instance().getVersionLink());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report 
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                     return "Server returned HTTP " + connection.getResponseCode() 
                         + " " + connection.getResponseMessage();

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/download/" + "TourPilot.apk");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled())
                        return null;
                    total += count;
                    // publishing the progress....
//                    if (fileLength > 0) // only if total length is known
//                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } 
                catch (IOException ignored) { }

                if (connection != null)
                    connection.disconnect();
            }
        } finally {
//            wl.release();
        }
              	
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	File file = new File(Environment.getExternalStorageDirectory() + "/download/" + "TourPilot.apk");
    	intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	StaticResources.getBaseContext().startActivity(intent);
        return null;
    }

}
