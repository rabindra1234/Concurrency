package concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrier_Concept {

	public static void main(String[] args) {
		
		System.out.println("Called from main");
		
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {

			@Override
			public void run() {
				System.out.println("All Parties are Reached");
			}
		});
		
		new Thread(new Task(barrier)).start();
		new Thread(new Task(barrier)).start();
		new Thread(new Task(barrier)).start();
	}
}

class Task implements Runnable {

	private CyclicBarrier barrier;
	
	public Task(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + " is waiting for barrier");
			barrier.await();
			System.out.println(Thread.currentThread().getName() + " is crossed the barrier");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
}
