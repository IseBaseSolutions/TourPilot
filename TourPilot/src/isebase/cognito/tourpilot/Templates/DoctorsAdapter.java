package isebase.cognito.tourpilot.Templates;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

public class DoctorsAdapter extends ArrayAdapter<Doctor> {

	private List<Doctor> listDoctors = new ArrayList<Doctor>();
	private int layoutResourceId;
	private Context context;

	private View row;
	
	public DoctorsAdapter(Context context, int layoutResourceId, List<Doctor> listDoctors){
		super(context, layoutResourceId, listDoctors);
		
		this.listDoctors = listDoctors;
		this.context = context;
		this.listDoctors = listDoctors;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		row = convertView;

		DoctorHolder doctorHolder = new DoctorHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		doctorHolder.doctor = this.listDoctors.get(position);
		
		doctorHolder.imageCall = (ImageButton)row.findViewById(R.id.imgbtnCallToDoctor);
		doctorHolder.imageCall.setTag(doctorHolder.doctor);
		return row;
	}
	public class DoctorHolder{
		Doctor doctor;
		ImageButton imageCall;
	}
}
