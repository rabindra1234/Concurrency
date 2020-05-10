package concurrency;

import java.util.LinkedList;

public class CustomThreadPool {

	LinkedList<Runnable> taskQueue;
	WorkerThread [] threads;
	
	
	public CustomThreadPool(int threadNumber) {
		taskQueue = new LinkedList<Runnable>();
		threads = new WorkerThread[threadNumber];
		for(int i=0; i< threads.length; i++) {
			threads[i] = new WorkerThread();
			threads[i].start();
		}
	}
	
	public void enqueue(Runnable r) {
		synchronized (taskQueue) {
			System.out.println("adding data into task queue");
			taskQueue.addLast(r);
			taskQueue.notify();
		}
	}
	
	public class WorkerThread extends Thread {
		public void run() {
			Runnable r;
			while(true) {
				synchronized (taskQueue) {
					while(taskQueue.isEmpty()) {
						try {
							System.out.println("Task Queue is going to waiting state");
							taskQueue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					r = taskQueue.removeFirst();
				}
				r.run();
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		Runnable task1 = () -> {
				System.out.println("Hi");
		};
		
		Runnable task2 = () -> {
			System.out.println("Hello");
		};
		
		CustomThreadPool pool = new CustomThreadPool(3);
		pool.enqueue(task1);
		pool.enqueue(task2);
	}

}
