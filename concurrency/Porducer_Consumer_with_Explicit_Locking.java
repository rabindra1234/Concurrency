/**
 * 
 */
package concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService ;

/**
 * @author Rabindra
 *
 */
public class Porducer_Consumer_with_Explicit_Locking {

	Queue<Integer> queue = new LinkedList<Integer>();
	final int MAX_SIZE=4;
	final Lock lock = new ReentrantLock();
	final Condition isFullCondition = lock.newCondition();
	final Condition isEmptyCondition = lock.newCondition();
	
	public void put(Integer data) {
		lock.lock();
		try {
			while(queue.size() ==  MAX_SIZE) {
				System.out.println("Queue is Full | Producer is waiting");
				isFullCondition.await();
			}
			queue.add(data);
			System.out.println("Producing--" +data);
			Thread.sleep(1200);
			isEmptyCondition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public Integer take() {
		lock.lock();
		Integer data = null;
		try {
			while(queue.isEmpty()) {
				System.out.println("Queue is Empty | Consumer is waiting");
				isEmptyCondition.await();
			}
			Thread.sleep(1000);
			data = queue.remove();
			System.out.println("Consuming--" + data);
			isFullCondition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException {
		// traditional way of creating n starting Thread
		Porducer_Consumer_with_Explicit_Locking objectRef_1=new Porducer_Consumer_with_Explicit_Locking();
		
				 new Thread() {
					public void run() {
						int data = 1;
						while(data <= 5) {
							objectRef_1.put(data);
							data++;
						}
					}
				}.start();
				
				 new Thread() {
					public void run() {
						int data = 1;
						while(data <= 5) {
							objectRef_1.take();
							data++;
						}
					}
				}.start();
				
		
		// sofisticated n impressive way of creating thread 
				Porducer_Consumer_with_Explicit_Locking objectRef_2=new Porducer_Consumer_with_Explicit_Locking();
				Thread producer = new Thread() {
					public void run() {
						int data = 1;
						while(data <= 5) {
							objectRef_2.put(data);
							data++;
						}
					}
				};
				
				Thread consumer = new Thread() {
					public void run() {
						int data = 1;
						while(data <= 5) {
							objectRef_2.take();
							data++;
						}
					}
				};
				  ExecutorService producerService = Executors.newFixedThreadPool(2);
				  producerService.execute(producer);
				  
				  ExecutorService consumerService = Executors.newFixedThreadPool(2);
				  consumerService.execute(consumer);
		
	}
	
}
