package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

	private BlockingQueue<JobInfo> jobs = new LinkedBlockingQueue<>();
	private Thread[] workers;
	private final static Runnable RED_PILL = () -> {};
	
	public ThreadPool() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public ThreadPool(int numberOfWorkers) {
		workers = new Thread[numberOfWorkers];

		for (int i = 0; i < numberOfWorkers; i++) {
			workers[i] = new Thread(() -> {
				while (true) {
					JobInfo info = nextJob();
					if (info.job == RED_PILL) break;
					try {
						info.job.run();
					} finally {
						info.markExecuted();
					}
				}
			});
			workers[i].start();
		}
	}

	public JobInfo addJob(Runnable job) {
		JobInfo jobInfo = new JobInfo(job);
		while (true) {
			try {
				jobs.put(jobInfo);
				break;
			} catch (InterruptedException e) {
			}
		}
		return jobInfo;
	}

	public void shutDown() {
		for (int i = 0; i < workers.length; i++) {
			addJob(RED_PILL);
		}
	}
	
	private JobInfo nextJob() {
		while (true) {
			try {
				return jobs.take();
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static class JobInfo {
		private boolean executed = false;
		private Runnable job;
		
		public JobInfo(Runnable job) {
			this.job = job;
		}
		
		private synchronized void markExecuted(){
			executed = true;
			this.notifyAll();
		}
		
		public synchronized void waitUntilNotExecuted() {
			while(!executed){
				try { this.wait(); } catch (InterruptedException e) {}
			}
		}
	}
}
