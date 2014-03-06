package isebase.cognito.tourpilot.Data.WayPoint;

import isebase.cognito.tourpilot.Connection.SentObjectVerification;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import android.location.Location;

public class WayPoint extends BaseObject {
	
	public static final String TrackIDField = "track_id";
	public static final String LatitudeField = "latitude"; 
	public static final String LongitudeField = "longitude"; 
	public static final String NbSatellitesField = "nbSatellites"; 
//	public static final String UuidField = "uuid";
	public static final String AltitudeField = "altitude"; 
	public static final String AccuracyField = "accuracy";
//	public static final String LinkField = "link"; 
	public static final String TimeField = "time"; 
	public static final String WorkerIDField = "worker_id"; 
	public static final String PilotTourIDField = "pilot_tour_id"; 
	
	//private long trackID;
	private double latitude;
	private double longitude;
	private int nbSatellites;
	//private String uuid;
	private double altitude;
	private float accuracy;
	//private String link;
	private long time;
	private int workerID;
	private int pilotTourID;

//	@MapField(DatabaseField = TrackIDField)
//	public long getTrackID() {
//		return trackID;
//	}
//
//	@MapField(DatabaseField = TrackIDField)
//	public void setTrackID(long trackID) {
//		this.trackID = trackID;
//	}

	@MapField(DatabaseField = LatitudeField)
	public double getLatitude() {
		return latitude;
	}

	@MapField(DatabaseField = LatitudeField)
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@MapField(DatabaseField = LongitudeField)
	public double getLongitude() {
		return longitude;
	}

	@MapField(DatabaseField = LongitudeField)
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@MapField(DatabaseField = NbSatellitesField)
	public int getNbSatellites() {
		return nbSatellites;
	}

	@MapField(DatabaseField = NbSatellitesField)
	public void setNbSatellites(int nbSatellites) {
		this.nbSatellites = nbSatellites;
	}
	
//	@MapField(DatabaseField = UuidField)
//	public String getUuid() {
//		return uuid;
//	}
//
//	@MapField(DatabaseField = UuidField)
//	public void setUuid(String uuid) {
//		this.uuid = uuid;
//	}
	
	@MapField(DatabaseField = AltitudeField)
	public double getAltitude() {
		return altitude;
	}

	@MapField(DatabaseField = AltitudeField)
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@MapField(DatabaseField = AccuracyField)
	public float getAccuracy() {
		return accuracy;
	}

	@MapField(DatabaseField = AccuracyField)
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	
//	@MapField(DatabaseField = LinkField)
//	public String getLink() {
//		return link;
//	}
//
//	@MapField(DatabaseField = LinkField)
//	public void setLink(String link) {
//		this.link = link;
//	}
	
	@MapField(DatabaseField = TimeField)
	public long getTime() {
		return time;
	}

	@MapField(DatabaseField = TimeField)
	public void setTime(long time) {
		this.time = time;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID() {
		return pilotTourID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int pilotTourID) {
		this.pilotTourID = pilotTourID;
	}
	
	public WayPoint() {
		clear();
	}
	
	public WayPoint(int workerID, int pilotTourID, Location location, int nbSatellites) {
		try {
			setWorkerID(workerID);
			setPilotTourID(pilotTourID);
	    	setLatitude(location.getLatitude());
	    	setLongitude(location.getLongitude());
	    	setNbSatellites(nbSatellites);
	        if (location.hasAltitude()) {
	        	setAltitude(location.getAltitude());
	        }
	        
	        if (location.hasAccuracy()) {
	        	setAccuracy(location.getAccuracy());
	        }
	        setTime(location.getTime());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public WayPoint(long trackId, Location location, int nbSatellites, String name, String link, String uuid) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Tracking waypoint '" + name + "', track=" + trackId + ", uuid=" + uuid + ", link='" + link + "', location=" + location, Toast.LENGTH_SHORT);
//		toast.show();
//
//        // location should not be null, but sometime is.
//        // TODO investigate this issue.
//        if (location != null) {
//        	setTrackID(trackId);
//        	setLatitude(location.getLatitude());
//        	setLongitude(location.getLongitude());
//        	setName(name);
//        	setNbSatellites(nbSatellites);
//
//            if (uuid != null) {
//            	setUuid(uuid);
//            }
//            
//            if (location.hasAltitude()) {
//            	setAltitude(location.getAltitude());
//            }
//            
//            if (location.hasAccuracy()) {
//            	setAccuracy(location.getAccuracy());
//            }
//            if (link != null) {
//               	// Rename file to match location timestamp
//            	setLink(renameFile(trackId, link, DateUtils.FileNameFormat.format(location.getTime())));
//            }
//            
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StaticResources.getBaseContext());
//            if (prefs.getBoolean(OSMTracker.Preferences.KEY_GPS_IGNORE_CLOCK, OSMTracker.Preferences.VAL_GPS_IGNORE_CLOCK)) {
//                    // Use OS clock
//            	setTime(System.currentTimeMillis());
//            } else {
//            	setTime(location.getTime());
//            }
////            Uri trackUri = ContentUris.withAppendedId(TrackContentProvider.CONTENT_URI_TRACK, trackId);
////            contentResolver.insert(Uri.withAppendedPath(trackUri, Schema.TBL_WAYPOINT + "s"), values);
//        }
//    }
	
//    public void updateWayPoint(long trackId, String uuid, String name, String link) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Updating waypoint with uuid '" + uuid + "'. New values: name='" + name + "', link='" + link + "'", Toast.LENGTH_SHORT);
//		toast.show();
//        if (uuid != null) {
//            if (name != null) {
//            	setName(name);
//            }
//            if (link != null) {
//            	setLink(link);
//            }            
////            Uri trackUri = ContentUris.withAppendedId(TrackContentProvider.CONTENT_URI_TRACK, trackId);
////            contentResolver.update(Uri.withAppendedPath(trackUri, Schema.TBL_WAYPOINT + "s"), values,
////                            "uuid = ?", new String[] { uuid });
//        }
//    }
//    
//    public void deleteWayPoint(String uuid) {
//		Toast toast = Toast.makeText(StaticResources.getBaseContext(), "Deleting waypoint with uuid '" + uuid, Toast.LENGTH_SHORT);
//		toast.show();
//        if (uuid != null) {
//                //contentResolver.delete(Uri.withAppendedPath(TrackContentProvider.CONTENT_URI_WAYPOINT_UUID, uuid), null, null);
//        }
//    }
//    
//    private String renameFile(Long trackId, String from, String to) {
//        // If all goes terribly wrong and we can't rename the file,
//        // we will return the original file name we were given
//        String _return = from;
//        
//        File trackDir = getTrackDirectory(trackId);
//        
//        String ext = from.substring(from.lastIndexOf(".") + 1, from.length());
//        File origin = new File(trackDir + File.separator + from);
//        
//        // No point in trying to rename the file unless it exist
//        if (origin.exists()) {
//                File target = new File(trackDir + File.separator + to + "." + ext);
//                // Check & manages if there is already a file with this name
//                for (int i = 0; i < 20 && target.exists(); i++) {
//                        target = new File(trackDir + File.separator + to + i + "." + ext);
//                }
//        
//                origin.renameTo(target);
//                _return = target.getName(); 
//        }
//        
//        return _return;
//    }
//    
//    /**
//     * Generate a string of the directory path to external storage for the track id provided 
//     * @param trackId Track id
//     * @return A the path where this track should store its files
//     */
//    public static File getTrackDirectory(long trackId) {
//            File _return = null;
//            
//            String trackStorageDirectory = Environment.getExternalStorageDirectory()  
//            + "/osmtracker/data/files/track" + trackId;
//            
//            _return = new File(trackStorageDirectory);                
//            return _return;
//    }
	
    public String getDone()
    {
		String strValue = new String("C;");
		strValue += getWorkerID() + ";";
		strValue += getPilotTourID() + ";";
		strValue += getTime() + ";";
		strValue += getLatitude() + ";";
		strValue += getLongitude() + ";";
		SentObjectVerification.Instance().sentWayPoints.add(this);
		return strValue;
    }

}
