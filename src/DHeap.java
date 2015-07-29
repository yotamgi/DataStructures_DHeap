
/**
 * D-Heap
 */

public class DHeap {

	private int size, max_size, d;
	private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
	DHeap(int m_d, int m_size) {
		max_size = m_size;
		d = m_d;
		array = new DHeap_Item[max_size];
		size = 0;
	}

	// Getter for size
	// O(1)
	public int getSize() {
		return size;
	}

	/**
	 * public void arrayToHeap()
	 * 
	 * The function builds a new heap from the given array. Previous data of the
	 * heap should be erased. preconidtion: array1.length() <= max_size
	 * postcondition: isHeap() size = array.length()
	 * 
	 * amortized O(n)
	 */
	public void arrayToHeap(DHeap_Item[] array1) {
		int n = array1.length;
		if (n <= max_size) {
			size = n;
			for (int i = 0; i < n; i++) {
				array[i] = array1[i];
				array[i].setPos(i);
			}
			heapifyDown(parent(n - 1));
			for (int i = n - 2; i > 0; i--) {
				if (parent(i) != parent(i + 1))
					heapifyDown(parent(i));
			}
		}
	}

	/**
	 * public boolean isHeap()
	 * 
	 * The function returns true if and only if the D-ary tree rooted at
	 * array[0] satisfies the heap property or size == 0.
	 * 
	 * O(n)
	 */
	public boolean isHeap() {
		if (size == 0)
			return true;
		for (int i = 0; i < size; i++) {
			DHeap_Item tmp = array[i];
			if (tmp == null)
				return false;
			if (tmp.getPos() != i)
				return false;
			DHeap_Item parent = array[parent(i)];
			if (parent.getKey() > tmp.getKey())
				return false;
		}
		return true;
	}

	/**
	 * public int parent(i), child(i,k) (2 methods)
	 * 
	 * precondition: i >= 0
	 * 
	 * The methods compute the index of the parent and the k-th child of vertex
	 * i in a complete D-ary tree stored in an array. 1 <= k <= d. Note that
	 * indices of arrays in Java start from 0.
	 * 
	 * both functions O(1)
	 */
	public int parent(int i) {
		return (i - 1) / d;
	}

	public int child(int i, int k) {
		return d * i + k;
	}

	/**
	 * public void Insert(DHeap_Item item)
	 * 
	 * precondition: item != null isHeap() size < max_size
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(log_d_(n))
	 */
	public void Insert(DHeap_Item item) {
		if ((size < max_size) && (item != null)) {
			array[size] = item;
			array[size].setPos(size);
			heapifyUp(size);
			size++;
		}
	}

	/**
	 * helping function
	 * 
	 * Switching the heap item in place i with it's parent if needed. Then it
	 * calls itself with the parent's place as the new i. There may be
	 * O(log_d_(n)) calls.
	 * 
	 * 
	 * for one recursive call - O(1) for the entire recursion - O(log_d_(n))
	 */
	private void heapifyUp(int i) {
		if (i == 0)
			return;
		DHeap_Item child = array[i];
		int place = parent(i);
		DHeap_Item parent = array[place];
		counterDecreaseKey++; // delete this line in the end
		if (parent.getKey() > child.getKey()) {
			switchPlaces(array, i, place);
			heapifyUp(place);
		}
	}

	/**
	 * public void Delete_Min()
	 * 
	 * precondition: size > 0 isHeap()
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(dlog_d_(n))
	 */
	public void Delete_Min() {
		if (size == 0)
			return;
		switchPlaces(array, 0, size - 1);
		size--;
		heapifyDown(0);
	}

	/**
	 * helping function
	 * 
	 * Switching the heap item in place i with it's minimum child if needed.
	 * Then it calls itself with the child's place as the new i. There may be
	 * O(log_d_(n)) calls.
	 * 
	 * 
	 * for one recursive call - O(d) for the entire recursion - O(dlog_d_(n))
	 */
	private void heapifyDown(int i) {
		if (size < 2)
			return;
		int min = array[i].getKey();
		DHeap_Item minItem = null;
		DHeap_Item child;
		for (int j = 1; j <= d; j++) {
			int place = child(i, j);
			if (place < size) {
				child = array[place];
				counterHeapifyDown++; // delete this line in the end
				if (child.getKey() < min) {
					min = child.getKey();
					minItem = child;
				}
			}
		}
		if (min == array[i].getKey())
			return;
		int place = minItem.getPos();
		switchPlaces(array, i, place);
		heapifyDown(place);
	}

	/**
	 * public String Get_Min()
	 * 
	 * precondition: heapsize > 0 isHeap() size > 0
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(1)
	 */
	public DHeap_Item Get_Min() {
		return array[0];
	}

	/**
	 * public void Decrease_Key(DHeap_Item item, int delta)
	 * 
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(log_d_(n))
	 */
	public void Decrease_Key(DHeap_Item item, int delta) {
		if (delta < 1)
			return;
		int tmp = item.getKey();
		item.setKey(tmp - delta);
		heapifyUp(item.getPos());
	}

	/**
	 * public void Delete(DHeap_Item item)
	 * 
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(dlog_d_(n))
	 */
	public void Delete(DHeap_Item item) {
		int place = item.getPos();
		switchPlaces(array, place, size - 1);
		size--;
		heapifyDown(place);
		heapifyUp(place);
	}

	/**
	 * Return a sorted array containing the same integers in the input array.
	 * Sorting should be done using the DHeap.
	 * 
	 * O(dnlog_d_(n))
	 */
	public static int[] DHeapSort(int[] array) {
		int n = array.length;
		int[] result = new int[n];
		DHeap_Item[] heapArray = new DHeap_Item[n];

		for (int i = 0; i < n; i++)
			heapArray[i] = new DHeap_Item("", array[i]);

		DHeap heap = new DHeap(bigD, n); // turn bigD into 2 in the end
		heap.arrayToHeap(heapArray);

		for (int i = 0; i < n; i++) {
			result[i] = heap.Get_Min().getKey();
			heap.Delete_Min();
		}
		return result;
	}

	/**
	 * switching the places of the two Heap items in the given places.
	 * 
	 * O(1)
	 */
	public void switchPlaces(DHeap_Item[] array1, int place1, int place2) {
		DHeap_Item tmp = array1[place1];
		array1[place1] = array1[place2];
		array1[place2] = tmp;
		array1[place1].setPos(place1);
		array1[place2].setPos(place2);
	}

	/**
	 * delete everything from here downwards
	 * 
	 */

	public static int counterHeapifyDown = 0;
	public static int counterDecreaseKey = 0;
	public static int bigD = 2;

	public void print() { // to delete in the end, just for checking
		int i = 0;
		while (i < size) {
			System.out.print(array[i].getName() + " ");
			if (i % d == 0)
				System.out.println(" ");
			i++;
		}
	}

	public static void main(String[] args) { // to delete in the end
		DHeap hip1 = new DHeap(3, 10);
		DHeap_Item[] hip = new DHeap_Item[10];
		hip[0] = new DHeap_Item("a", 3);
		hip[1] = new DHeap_Item("b", 20);
		hip[2] = new DHeap_Item("c", 8);
		hip[3] = new DHeap_Item("d", 4);
		hip[4] = new DHeap_Item("e", 29);
		hip[5] = new DHeap_Item("f", 31);
		hip[6] = new DHeap_Item("g", 33);
		hip[7] = new DHeap_Item("h", 41);
		hip[8] = new DHeap_Item("i", 19);
		for (DHeap_Item i : hip)
			hip1.Insert(i);
		hip1.Decrease_Key(hip[4], 27);
		hip1.print();
		System.out.println("");
		System.out.println(hip1.isHeap());
	}

}
