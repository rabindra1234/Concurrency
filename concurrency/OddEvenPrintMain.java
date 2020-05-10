package concurrency;

public class OddEvenPrintMain {
	 
	boolean odd;
	int count = 1;
	int MAX = 10;
 
	public void printOdd() {
		synchronized (this) {
			while (count < MAX) {
				while (!odd) {
					try {
						System.out.println("Odd waiting : " + count);
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Odd Thread :" + count);
				count++;
				odd = false;
				notify();
			}
		}
	}
 
	public void printEven() {
		synchronized (this) {
			while (count < MAX) {
				while (odd) {
					try {
						System.out.println("Even waiting: " + count);
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Even thread :" + count);
				count++;
				odd = true;
				notify();
			}
		}
	}
 
	public static void main(String[] args) {
		OddEvenPrintMain oep = new OddEvenPrintMain();
		oep.odd = true;
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				oep.printEven();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				oep.printOdd();
			}
		});
 
		t1.start();
		t2.start();
 
	}
}
