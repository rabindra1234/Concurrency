package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Forkjoinpool_Concept extends RecursiveTask<Integer> {

	int num = 0;
	int result=0;
	public Forkjoinpool_Concept(int num) {
		this.num = num;
	}
	
	@Override
	protected Integer compute() {
		if(num <= 1) {
			return num;
		} else {
			Forkjoinpool_Concept f1 = new Forkjoinpool_Concept(num-1);
			f1.fork();
			Forkjoinpool_Concept f2 = new Forkjoinpool_Concept(num-2);
			f2.fork();
			result = f1.join() + f2.join();
		}
		return result;
	}
	
	public static void main(String[] args) {
		int times = 10;
		int n = 0;
		while (--times > 0) {
			int noOfCores = Runtime.getRuntime().availableProcessors();
			ForkJoinPool objRef = new ForkJoinPool(noOfCores);
	    	ForkJoinTask<Integer> task = new Forkjoinpool_Concept(n);
		    System.out.println(String.format("fib(%d) = %d", n, objRef.invoke(task)));
		}
	}
}


