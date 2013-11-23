package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UserRemarksActivity extends BaseActivity {
	
	private LinearLayout linearElements;
	
	private CheckBox chbConnect;
	private CheckBox chbMedchanges;
	private CheckBox chbPflege;
	
	private EditText etOther;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		initElements();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.notes, menu);
		
		return true;
	}
	
	private void initElements(){		
		linearElements = (LinearLayout)findViewById(R.id.linearElements);
		chbConnect = (CheckBox)findViewById(R.id.chbConnect);
		chbMedchanges = (CheckBox)findViewById(R.id.chbMedchanges);
		chbPflege = (CheckBox)findViewById(R.id.chbPflege);
		etOther = (EditText)findViewById(R.id.etOther);
	}
}
