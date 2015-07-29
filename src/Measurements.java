import java.util.Random;

public class Measurements {
	static int ms[] = { 1000, 10000, 100000 };
	static int xs[] = { 1, 100, 1000 };

	public static void main(String[] args) {
		partA();
		partB();
	}

	public static void partA() {
		System.out.println("\nPart A");
		System.out.println("----------------------");
		
		for (int m : ms) {
			System.out.println("m = " + m);
			for (int d = 2; d < 5; d++) {
				DHeap.bigD = d;
				
				DHeap.counterHeapifyDown = 0;
				for (int times=0; times<10; times++) {
			
					// create rand array in size m
					int[] nums = new int[m];
					Random rand = new Random();
					for (int j = 0; j < m; j++) {
						nums[j] = rand.nextInt(1000);
					}
			
					// heapsort it 
					DHeap.DHeapSort(nums);
				}
				
				System.out.println("	d=" + d + ": " + (float)DHeap.counterHeapifyDown/10.0);
			}
		}
	}
		
	public static void partB() {

		final int N = 100000;
		Random rand = new Random();
		
		System.out.println("\nPart B");
		System.out.println("----------------------");

		for (int x: xs) {
			System.out.println("x = " + x);
			
			
			for (int d = 2; d < 5; d++) {
				long totalCounter = 0;
				
				for (int times=0; times<10; times++) {
	
					// create rand items
					DHeap_Item[] items = new DHeap_Item[N];
					for (int i = 0; i < N; i++) {
						items[i] = new DHeap_Item("" + i, rand.nextInt(1000));
					}
					
					// insert to the heap
					DHeap hip = new DHeap(d, N);
					for (int i = 0; i < N; i++) {
						hip.Insert(items[i]);
					}
					
					// decreas the key
					DHeap.counterDecreaseKey = 0;
					for (int i = 0; i < N; i++) {
						hip.Decrease_Key(items[i], x);
					}
					
					totalCounter += DHeap.counterDecreaseKey;
				}
				
				// print the number
				System.out.println("	d=" + d + ": " + (float)totalCounter/10.);
			}
		}
	}
}
