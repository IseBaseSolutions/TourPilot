package isebase.cognito.tourpilot.Connection;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPostExecute(Void result) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(Void... params) {
		Connection connection = new Connection();
		try {
			connection.StartConnection(connection.getConnectionURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
