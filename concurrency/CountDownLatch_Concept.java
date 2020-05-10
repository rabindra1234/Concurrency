package concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatch_Concept {

	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(3);
		
		new Thread(new LatchTask(latch)).start();
		new Thread(new LatchTask(latch)).start();
		new Thread(new LatchTask(latch)).start();
		
		try {
			latch.await();
			System.out.println("Main Thread Awaiting for all Threads to reach the count");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}


class LatchTask implements Runnable {

	CountDownLatch latch ;
	
	public LatchTask(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		System.out.println("Hello");
		latch.countDown();
	}
}