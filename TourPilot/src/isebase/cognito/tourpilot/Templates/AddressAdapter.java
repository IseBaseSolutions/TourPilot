package isebase.cognito.tourpilot.Templates;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Address.IAddressable;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddressAdapter<T extends IAddressable> extends ArrayAdapter<T> {

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
		
		addressHolder.tvFullname = (TextView) row.findViewById(R.id.tvFullName);
		addressHolder.tvFullname.setText(addressHolder.address.getFullName());
		
		addressHolder.tvAddressName = (TextView) row.findViewById(R.id.tvAddressName);		
		addressHolder.tvAddressName.setText(addressHolder.address.getAddress().getAddressData());

		addressHolder.tvPhone = (TextView) row.findViewById(R.id.tvPhone);
		addressHolder.imageCallPhone = (ImageButton)row.findViewById(R.id.btCallPhone);
		if(addressHolder.address.getAddress().getRealPhone().length() > 0){
			addressHolder.tvPhone.setText(String.format("%s",addressHolder.address.getAddress().getPhone()));
			addressHolder.imageCallPhone.setTag(addressHolder.address);
		}else{
			addressHolder.tvPhone.setText(StaticResources.getBaseContext()
					.getString(R.string.err_no_phone));
			addressHolder.imageCallPhone.setVisibility(View.INVISIBLE);
		}

		addressHolder.tvPrivatePhone = (TextView) row.findViewById(R.id.tvPrivatePhone);
		addressHolder.imageCallPrivatePhone = (ImageButton)row.findViewById(R.id.btCallPrivatePhone);
		if(addressHolder.address.getAddress().getRealPrivatePhone().length() > 0){
			addressHolder.tvPrivatePhone.setText(String.format("%s",addressHolder.address.getAddress().getPrivatePhone()));
			addressHolder.imageCallPrivatePhone.setTag(addressHolder.address);
		}else{
			addressHolder.tvPrivatePhone.setText(StaticResources.getBaseContext()
					.getString(R.string.err_no_phone));
			addressHolder.imageCallPrivatePhone.setVisibility(View.INVISIBLE);
		}
		
		addressHolder.tvMobilePhone = (TextView) row.findViewById(R.id.tvMobilePhone);
		addressHolder.imageCallMobilePhone = (ImageButton)row.findViewById(R.id.btCallMobilePhone);
		if(addressHolder.address.getAddress().getRealMobilePhone().length() > 0){
			addressHolder.tvMobilePhone.setText(String.format("%s",addressHolder.address.getAddress().getMobilePhone()));
			addressHolder.imageCallMobilePhone.setTag(addressHolder.address);
		}else{
			addressHolder.tvMobilePhone.setText(StaticResources.getBaseContext()
					.getString(R.string.err_no_phone));
			addressHolder.imageCallMobilePhone.setVisibility(View.INVISIBLE);
		}

		return row;
	}
	public class AddressHolder{

		IAddressable address;

		TextView tvFullname;
		
		ImageButton imageCallPhone;
		ImageButton imageCallPrivatePhone;
		ImageButton imageCallMobilePhone;

		TextView tvPhone;
		TextView tvPrivatePhone;
		TextView tvMobilePhone;
		
		TextView tvAddressName;
	}
}

