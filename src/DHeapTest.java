import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;


public class DHeapTest {

	@Test
	public void testDHeapSort() {
		Random rand = new Random();
		
		for (int i=0; i<10; i++) {
			int arrSize = rand.nextInt(100000) + 1000;
			DHeap.bigD = i+2;
			
			// create rand array in size arrSize
			int[] nums = new int[arrSize];
			for (int j = 0; j < arrSize; j++) {
				nums[j] = rand.nextInt();
			}
			
			// sort a copy of the array
			int[] origNums = nums.clone();
			Arrays.sort(origNums);
			
			// use the heap to sort another copy of the array
			nums = DHeap.DHeapSort(nums);
			
			// validate that the arrays are of equal length
			assertEquals(nums.length, origNums.length);
			
			// validate that the arrays are the same
			for (int j=0; j<nums.length; j++) {
				assertEquals(nums[j], origNums[j]);
			}
		}
	}
	
	@Test
	public void testDecreaseKey() {
		Random rand = new Random();
		
		for (int i=0; i<10; i++) {
			int arrSize = rand.nextInt(10000) + 1000; 
			DHeap h = new DHeap(i+2, arrSize+100);
			int delta = rand.nextInt(1000);
			
			// create rand array in size arrSize
			int[] nums = new int[arrSize];
			for (int j = 0; j < arrSize; j++) {
				nums[j] = rand.nextInt();
			}
			
			// sort a copy of the array
			int[] origNums = nums.clone();
			Arrays.sort(origNums);
		
			// create items with keys enlarged by delta
			DHeap_Item[] items = new DHeap_Item[arrSize];
			for (int j = 0; j < arrSize; j++) {
				items[j] = new DHeap_Item("" + nums[j], nums[j] + delta);
			}
			
			// insert all into a heap
			for (int j=0; j<arrSize; j++) {
				h.Insert(items[j]);
			}
			
			// decrease all keys inside the heap by delta
			for (int j=0; j<arrSize; j++) {
				h.Decrease_Key(items[j], delta);
			}
			
			// create a sorted array out of it
			for (int j=0; j<arrSize; j++) {
				nums[j] = h.Get_Min().getKey();
				h.Delete_Min();
 			}
			
			// validate that the arrays are of equal length
			assertEquals(nums.length, origNums.length);
			
			// validate that the arrays are the same
			for (int j=0; j<nums.length; j++) {
				assertEquals(nums[j], origNums[j]);
			}
		}
	}
	
	@Test
	public void testDecreaseKey2() {
		Random rand = new Random();
		final int NUM_TESTS = 1000;

		for (int i=0; i<10; i++) {
			int heapSize = rand.nextInt(100000) + 1000; 
			DHeap h = new DHeap(i+2, heapSize);
			
			// create items with keys running from NUM_TESTS to heapSize
			// and insert them to the heap
			DHeap_Item[] items = new DHeap_Item[heapSize];
			for (int j = 0; j < heapSize; j++) {
				items[j] = new DHeap_Item("" + j, j+NUM_TESTS);
				h.Insert(items[j]);
			}
			
			// every time, choose a random elemnt and make it the minimum
			for (int j=NUM_TESTS-1; j>0; j--) {
				int chosenIndex = rand.nextInt(items.length);
				h.Decrease_Key(items[chosenIndex], items[chosenIndex].getKey() - j); // decrease it to j
				
				// verify that now it is the minimum
				assertEquals(j, h.Get_Min().getKey());
				assertEquals("" + chosenIndex, h.Get_Min().getName());
			}
		}
	}
}
