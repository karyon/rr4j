import java.util.ArrayList;


public class JobList {
	
	private ArrayList<Job> list = new ArrayList<Job>();	
	
	public void addJob(Job job) {
		list.add(job);
		if (list.size() == 1)
			job.execute();
	}
	
	public void jobDoneExecuteNext() {
		list.remove(0);
		if (!list.isEmpty())
			list.get(0).execute();
	}
	
	public void jobDoneCancelNext() {
		list.clear();
	}
	
	public void cancelAll() {
		if (list.size() > 0)
			list.get(0).cancel();
		list.clear();
	}
}
