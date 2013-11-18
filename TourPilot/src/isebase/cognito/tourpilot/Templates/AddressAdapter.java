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
import android.widget.TextView;

public class AddressAdapter<T> extends ArrayAdapter<T> {

	private List<T> listAddress;
	private int layoutResourceId;
	private Context context;

	private View row;
	
	public AddressAdapter(Context context, int layoutResourceId, List<T> listAddress){
		super(context, layoutResourceId, listAddress);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.listAddress = listAddress;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		row = convertView;

		AddressHolder addressHolder = new AddressHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		addressHolder.address = listAddress.get(position);
		
		addressHolder.imageCall = (ImageButton)row.findViewById(R.id.imgbtnCallToDoctor);
		addressHolder.imageCall.setTag(addressHolder.address);
		addressHolder.tvDoctorName = (TextView) row.findViewById(R.id.tvAddressName);		
		addressHolder.tvDoctorName.setText(listAddress.get(position).toString());
		return row;
	}
	public class AddressHolder{
		T address;
		ImageButton imageCall;
		TextView tvDoctorName;
	}
}

