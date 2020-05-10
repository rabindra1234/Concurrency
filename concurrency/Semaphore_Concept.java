package concurrency;

import java.util.concurrent.Semaphore;

public class Semaphore_Concept {

	//binary semaphore - will allow only on thread to access the critical section of the code
	Semaphore binaySem = new Semaphore(1, true);
	
	//binary semaphore - will allow configured no of threads to access the critical section of the code
	Semaphore countingSem = new Semaphore(3, true);
	
	public static void main(String[] args) {
		Semaphore_Concept binarySem_objRef = new Semaphore_Concept();
		new Thread(binarySem_objRef.binarySem_task).start();
		new Thread(binarySem_objRef.binarySem_task).start();
		new Thread(binarySem_objRef.binarySem_task).start();
		
		
		Semaphore_Concept countingSem_objRef = new Semaphore_Concept();
		new Thread(countingSem_objRef.countingSem_task).start();
		new Thread(countingSem_objRef.countingSem_task).start();
		new Thread(countingSem_objRef.countingSem_task).start();
	}
	
	
	Runnable binarySem_task = () -> {
		binaySem.acquireUninterruptibly();
		System.out.println(binaySem.availablePermits());
		
		//critical section of code | shared resource
		System.out.println("Hi");
		
		binaySem.release();
	};
	
	
	Runnable countingSem_task = () -> {
		countingSem.acquireUninterruptibly();
		System.out.println(countingSem.availablePermits());
		
		//critical section of code | shared resource
		System.out.println("Hello");
		
		countingSem.release();
	};

}
