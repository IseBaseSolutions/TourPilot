package isebase.cognito.tourpilot.Utils;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.widget.Toast;

public class DataBaseUtils {

	public static final String DB_PATH = "/data/data/isebase.cognito.tourpilot/databases/TourPilot.db";
	public static final String DB_BACKUP_PATH = "/mnt/sdcard/backup/";
	
	public static void backup(){
		File dbFile = new File(DB_PATH);
		String dbBackupPath = DB_BACKUP_PATH + "tourpilot_" + DateUtils.DateFormat.format(new Date()) + ".db";
		File dbBackupFile = new File(dbBackupPath);
		File backupPath = new File(DB_BACKUP_PATH);
		try {
			backupPath.mkdirs();
			dbBackupFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileUtils.copyFile(new FileInputStream(dbFile), new FileOutputStream(dbBackupFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CharSequence text = StaticResources.getBaseContext().getString(R.string.db_backup_created);
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(StaticResources.getBaseContext(), text, duration);
		toast.show();
	}

}
