package isebase.cognito.tourpilot.Dialogs;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class BaseDialog extends DialogFragment {

	public EditText etPin;

	public interface ListenerDialog {
		public void onDialogPositiveClick(DialogFragment dialog);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	ListenerDialog mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ListenerDialog) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

}
