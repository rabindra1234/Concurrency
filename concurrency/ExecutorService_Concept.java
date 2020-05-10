/**
 * 
 */
package concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

/**
 * @author Rabindra
 *
 */
public class ExecutorService_Concept {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Thread thread1 = new Thread(new Task());
		thread1.start();
		Thread thread2 = new Thread(new Task());
		thread2.start();
		
		int noOfProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(noOfProcessors);
		service.execute(new Task());
		service.submit(new Task());  // ideally designed for callable 
		
		/*
		 * initiates shut down, all the tasks which are submitted / present in blocking
		 * queue will will be picked up by Threads. completed its task then shutdown
		 * will happen
		 */
		service.shutdown();  // must invoke from finally block always
		
		service.isShutdown(); // returns boolean true value if shutdown has initiated 
		
		service.isTerminated(); // returns boolean true value if executor has terminated after the shutdown invoke
		
		service.shutdownNow(); // forcefully shut down will happen with returns the list of runnable taks present in blocking queue.
		
		/*
		 * executor shut down will happen with configured time, with in the time all
		 * threads will try to pick the tasks from blocking queue and try to finish the
		 * execution, but its not mandatory wait, within the configured time whatever
		 * things will happen then executor will shut down.
		 */
		try {
			service.awaitTermination(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	}
	
	static class Task implements Runnable {

		@Override
		public void run() {
			System.out.println("Thread Name:" +Thread.currentThread().getName());			
		}
		
	}

}
