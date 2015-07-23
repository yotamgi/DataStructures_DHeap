import java.util.Random;

public class Measurements {
	static int m = 1000;
	static int x = 1;

	public static void main(String[] args) {
		myHipDontLie();
		otherHipDontLie();
	}

	public static void otherHipDontLie() {
		int n = 100000;
		DHeap_Item[] items = new DHeap_Item[n];
		System.out.println("x = " + x);
		for (int i = 0; i < n; i++)
			items[i] = new DHeap_Item("", randi());
		for (int d = 2; d < 5; d++) {
			DHeap hip = new DHeap(d, n);
			for (int i = 0; i < n; i++)
				hip.Insert(items[i]);
			for (int i = 0; i < n; i++)
				hip.Decrease_Key(items[i], x);
			System.out.println(DHeap.counterDecreaseKey);
			DHeap.counterDecreaseKey = 0;
		}
	}

	public static void myHipDontLie() {
		int[] nums = new int[m];
		for (int i = 0; i < m; i++)
			nums[i] = randi();
		System.out.println("m = " + m);
		for (int d = 2; d < 5; d++) {
			DHeap.bigD = d;
			DHeap.DHeapSort(nums);
			System.out.println(DHeap.counterHeapifyDown);
			DHeap.counterHeapifyDown = 0;
		}
	}

	public static int randi() {
		Random rand = new Random();
		return rand.nextInt(1000);
	}
}
