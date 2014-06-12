package isebase.cognito.tourpilot.Gps.Service;

import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.WayPoint.WayPoint;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class GPSLogger extends Service implements LocationListener {

	// private DataHelper dataHelper;

	/**
	 * Are we currently tracking ?
	 */
	private boolean isTracking = false;

	/**
	 * Is GPS enabled ?
	 */
	private boolean isGpsEnabled = false;

	/**
	 * System notification id.
	 */
	private static final int NOTIFICATION_ID = 1;

	/**
	 * Last known location
	 */
	private Location lastLocation;

	/**
	 * Last number of satellites used in fix.
	 */
	private int lastNbSatellites;

	/**
	 * LocationManager
	 */
	private LocationManager lmgr;

	/**
	 * Current Track ID
	 */
	private long currentTrackId = -1;

	/**
	 * the timestamp of the last GPS fix we used
	 */
	private long lastGPSTimestamp = 0;
	
	private long previousTime = 0;

	/**
	 * the interval (in ms) to log GPS fixes defined in the preferences
	 */
	private long gpsLoggingInterval;
	
	private double prevLat;
	
	private double prevLon;
	
	private WayPoint currentWayPoint;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Received intent " + intent.getAction(), Toast.LENGTH_SHORT);
//			toast.show();
//
//			if (OSMTracker.INTENT_TRACK_WP.equals(intent.getAction())) {
//				// Track a way point
//				Bundle extras = intent.getExtras();
//				if (extras != null) {
//					// because of the gps logging interval our last fix could be
//					// very old
//					// so we'll request the last known location from the gps
//					// provider
//					lastLocation = lmgr
//							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//					if (lastLocation != null) {
//						Long trackId = extras.getLong(WayPoint.TrackIDField);
//						String uuid = extras
//								.getString(OSMTracker.INTENT_KEY_UUID);
//						String name = extras
//								.getString(OSMTracker.INTENT_KEY_NAME);
//						String link = extras
//								.getString(OSMTracker.INTENT_KEY_LINK);
////						WayPoint wayPoint = new WayPoint(trackId, lastLocation,
////								lastNbSatellites, name, link, uuid);
//					}
//				}
//			} else if (OSMTracker.INTENT_UPDATE_WP.equals(intent.getAction())) {
//				// Update an existing waypoint
//				Bundle extras = intent.getExtras();
//				if (extras != null) {
//					Long trackId = extras.getLong(WayPoint.TrackIDField);
//					String uuid = extras.getString(OSMTracker.INTENT_KEY_UUID);
//					String name = extras.getString(OSMTracker.INTENT_KEY_NAME);
//					String link = extras.getString(OSMTracker.INTENT_KEY_LINK);
//					//dataHelper.updateWayPoint(trackId, uuid, name, link);
//				}
//			} else if (OSMTracker.INTENT_DELETE_WP.equals(intent.getAction())) {
//				// Delete an existing waypoint
//				Bundle extras = intent.getExtras();
//				if (extras != null) {
//					String uuid = extras.getString(OSMTracker.INTENT_KEY_UUID);
//					//dataHelper.deleteWayPoint(uuid);
//				}
//			} else if (OSMTracker.INTENT_START_TRACKING.equals(intent
//					.getAction())) {
//				Bundle extras = intent.getExtras();
//				if (extras != null) {
//					Long trackId = extras.getLong(WayPoint.TrackIDField);
//					startTracking(trackId);
//				}
//			} else if (OSMTracker.INTENT_STOP_TRACKING.equals(intent
//					.getAction())) {
//				stopTrackingAndSave();
//			}
		}
	};

	/**
	 * Binder for service interaction
	 */
	private final IBinder binder = new GPSLoggerBinder();

	@Override
	public IBinder onBind(Intent intent) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Service onBind()", Toast.LENGTH_SHORT);
//		toast.show();
		return binder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Service onUnbind()", Toast.LENGTH_SHORT);
//		toast.show();
		// If we aren't currently tracking we can
		// stop ourselves
		if (!isTracking) {
//			toast = Toast.makeText(StaticResources.getBaseContext(), "Service self-stopping", Toast.LENGTH_SHORT);
//			toast.show();
			stopSelf();
		}

		// We don't want onRebind() to be called, so return false.
		return false;
	}

	public class GPSLoggerBinder extends Binder {

		/**
		 * Called by the activity when binding. Returns itself.
		 * 
		 * @return the GPS Logger service
		 */
		public GPSLogger getService() {
			return GPSLogger.this;
		}
	}

	@Override
	public void onCreate() {
		try {
			StaticResources.setBaseContext(this);
			gpsLoggingInterval = Long.parseLong(PreferenceManager
					.getDefaultSharedPreferences(this.getApplicationContext())
					.getString(OSMTracker.Preferences.KEY_GPS_LOGGING_INTERVAL,
							OSMTracker.Preferences.VAL_GPS_LOGGING_INTERVAL)) * 1000;
	
			// Register our broadcast receiver
			IntentFilter filter = new IntentFilter();
			filter.addAction(OSMTracker.INTENT_TRACK_WP);
			filter.addAction(OSMTracker.INTENT_UPDATE_WP);
			filter.addAction(OSMTracker.INTENT_DELETE_WP);
			filter.addAction(OSMTracker.INTENT_START_TRACKING);
			filter.addAction(OSMTracker.INTENT_STOP_TRACKING);
			registerReceiver(receiver, filter);
	
			// Register ourselves for location updates
			lmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			super.onCreate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Service onStartCommand(-,"+flags+","+startId+")", Toast.LENGTH_SHORT);
//		toast.show();
        //startForeground(NOTIFICATION_ID, getNotification());
        return Service.START_STICKY;
    }
    
    @Override
    public void onDestroy() {
    	try {

	        if (isTracking) {
	            // If we're currently tracking, save user data.
	            stopTrackingAndSave();
	        }
	
	        // Unregister listener
	        lmgr.removeUpdates(this);
	        
	        // Unregister broadcast receiver
	        unregisterReceiver(receiver);
	        
	        // Cancel any existing notification
	        stopNotifyBackgroundService();
	
	        super.onDestroy();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

	/**
	 * Stops GPS Logging
	 */
	private void stopTrackingAndSave() {
        isTracking = false;
        currentTrackId = -1;
        this.stopSelf();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		try {
	        isGpsEnabled = true;
	        
	        if (Option.Instance().gpsStartTime - System.nanoTime() > (2 * 60 * 60 * 1000000000))
	        	stopSelf();

	        if(((lastGPSTimestamp + gpsLoggingInterval) < System.currentTimeMillis()) /*&& (System.currentTimeMillis() - lastGPSTimestamp) > 6000*/){

	            lastGPSTimestamp = System.currentTimeMillis(); // save the time of this fix
	    
	            lastLocation = location;
	            lastNbSatellites = countSatellites();
	            if (previousTime != 0 && (System.currentTimeMillis() - previousTime < 10000))
	            	return;
    			if (((location.getLatitude() != prevLat || location.getLongitude() != prevLon) && lastLocation.getAccuracy() < 25))
    			{
    				StaticResources.setBaseContext(this);
        			currentWayPoint = new WayPoint(Option.Instance().getWorkerID(), Option.Instance().getPilotTourID(), lastLocation, lastNbSatellites);
    				HelperFactory.getHelper().getWayPointDAO().save(currentWayPoint);
    				prevLat = currentWayPoint.getLatitude();
    				prevLon = currentWayPoint.getLongitude();
    				previousTime = System.currentTimeMillis();
    			}
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
    /**
     * Counts number of satellites used in last fix.
     * @return The number of satellites
     */
    private int countSatellites() {
        int count = 0;
        GpsStatus status = lmgr.getGpsStatus(null);
        for(GpsSatellite sat:status.getSatellites()) {
            if (sat.usedInFix()) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Stops notifying the user that we're tracking in the background
     */
    private void stopNotifyBackgroundService() {
    	try {
		    NotificationManager nmgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		    nmgr.cancel(NOTIFICATION_ID);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

	@Override
	public void onProviderDisabled(String provider) {
		isGpsEnabled = false;

	}

	@Override
	public void onProviderEnabled(String provider) {
		isGpsEnabled = true;

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	

    /**
     * Getter for gpsEnabled
     * @return true if GPS is enabled, otherwise false.
     */
    public boolean isGpsEnabled() {
            return isGpsEnabled;
    }
    
    /**
     * Setter for isTracking
     * @return true if we're currently tracking, otherwise false.
     */
    public boolean isTracking() {
            return isTracking;
    }

}
