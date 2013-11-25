package isebase.cognito.tourpilot.DataInterfaces.Job;

import java.util.Comparator;

public class JobComparer implements Comparator<IJob> {


	@Override
	public int compare(IJob lhs, IJob rhs) {
		return lhs.time().compareTo(rhs.time());
	}

}
