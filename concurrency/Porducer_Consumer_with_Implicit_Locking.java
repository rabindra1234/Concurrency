package concurrency;

import java.util.LinkedList;
import java.util.Queue;

/*
Blocking Queue Implementation for Producer Consumer Problem
Traditional - Implicit Locking mechanism Implementation
*/

public class Porducer_Consumer_with_Implicit_Locking {

	Queue<String> queue = new LinkedList<String>();	
	final int MAX_SIZE = 3;
	
	public void  put(String str) {
		synchronized(queue) {
			try {
				while(queue.size() == MAX_SIZE) {
					System.out.println("Queue size is full, producer is waiting");
					queue.wait();
				}
				queue.add(str);
				System.out.println("Producing--" +str);
				Thread.sleep(1000);
				queue.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void take() {
			synchronized(queue) {
				try {
					while(queue.size() == 0) {
						System.out.println("Queue is empty, consumer is waiting");
						queue.wait();
					}
					Thread.sleep(1000);
					System.out.println("Consuming--" +queue.remove());
					queue.notify();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
	}
	
	public static void main(String[] args) {
		Porducer_Consumer_with_Implicit_Locking objectRef=new Porducer_Consumer_with_Implicit_Locking();  
		new Thread(){  
			public void run() {
				int count=1;
				while(count <= 4) {
					objectRef.put("Hello");
					count++;
				}
				}  
		}.start(); 
		
		new Thread(){  
			public void run(){
				int count=1;
				while(count <= 4) {
					objectRef.take();
					count++;
				}
			}  
		}.start();  
	}
}
