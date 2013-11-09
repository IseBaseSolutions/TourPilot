package isebase.cognito.tourpilot.Templates;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WorkerAdapter extends ArrayAdapter<Worker>{
	
	private List<Worker> workers;
	private int layoutResourceId;
	private Context context;

	public WorkerAdapter(Context context, int layoutResourceId, List<Worker> workers) {
		super(context, layoutResourceId, workers);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.workers = workers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WorkerHolder workerHolder = new WorkerHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		workerHolder.worker = workers.get(position);
		workerHolder.tvWorkerName = (TextView) row.findViewById(R.id.tvWorkerName);
		workerHolder.tvWorkerName.setText(workerHolder.worker.getName());
	
		row.setTag(workerHolder);
		return row;
	}

	class WorkerHolder {
		 Worker worker;
		 TextView tvWorkerName;
	}
}
