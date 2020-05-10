/**
 * 
 */
package concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * @author Rabindra
 *
 */
public class Producer_Consumer_with_BlockingQueue {

	static class Task {
		String display() {
			return "Hi";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
		ExecutorService producerService = Executors.newFixedThreadPool(3);
		ExecutorService consumerService = Executors.newFixedThreadPool(3);
		
		//task producer 
		Runnable producer = ()-> {
				try {
					System.out.println("Producing-- " + new Producer_Consumer_with_BlockingQueue.Task().display());
					queue.put(new Producer_Consumer_with_BlockingQueue.Task().display());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		};
		
		//task consumer 
		Runnable consumer = () -> {
			try {
				System.out.println("Consuming-- " +queue.take());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
			
			producerService.execute(producer);
			consumerService.execute(consumer);
			producerService.execute(producer);
			producerService.execute(producer);
			producerService.execute(producer);
			consumerService.execute(consumer);
			consumerService.execute(consumer);
			consumerService.execute(consumer);
			consumerService.execute(consumer);
			producerService.execute(producer);
			consumerService.execute(consumer);
			
	}

}
