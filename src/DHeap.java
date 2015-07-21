import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

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
		if (n > max_size)
			max_size = n;		
		size = n;
		for (int i=0;i<n;i++){
			array[i] = array1[i];
			array[i].setPos(i);
		}	
		heapifyDown(parent(n-1));
		for (int i=n-2;i>0;i--){
			if (parent(i)!=parent(i+1))
				heapifyDown(parent(i));
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
		if (array[0] == null)
			return false;
		if (array[0].getPos()!= 0)
			return false;
		for (int i=1;i<size;i++){
			DHeap_Item tmp = array[i]; 
			if (tmp == null)
				return false;
			if (tmp.getPos()!=i)
				return false;
			DHeap_Item parent = array[parent(i)];
			if (parent.getKey()>tmp.getKey())
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
		return (i-1)/d;
	} 

	public int child(int i, int k) {
		return d*i + k;
	} 

	/**
	 * public void Insert(DHeap_Item item)
	 * 
	 * precondition: item != null isHeap() size < max_size
	 * 
	 * postcondition: isHeap()
	 */
	public void Insert(DHeap_Item item) {
		array[size++] = item;
		// TODO: Heapify up
	}

	/**
	 * public void Delete_Min()
	 * 
	 * precondition: size > 0 isHeap()
	 * 
	 * postcondition: isHeap()
	 * 
	 * O(dlogn)
	 */
	public void Delete_Min() {
		if (size == 0)
			return;
		switchPlaces(array, 0, size-1);
		size--;
		heapifyDown(0);
	}

	/**
	 * helping function 
	 * 
	 * Switching the heap item in place i with it's minimum
	 * child if needed. Then it calls itself with the child's
	 * place as the new i.
	 * There may be O(logn) calls.
	 * 
	 * 
	 * for one recursive call - O(d)
	 * for the entire recursion - O(dlogn) 
	 */
	private void heapifyDown(int i) {
		if (size<2)
			return;
		int min = array[i].getKey();
		DHeap_Item minItem = null;
		DHeap_Item tmp;
		for (int j=1;j<=d;j++){
			int place = child(i,j);	
			if (place < size){
				tmp = array[place];
				if (tmp.getKey()<min){
					min = tmp.getKey();
					minItem = tmp;
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
	 * O(?)
	 */
	public void Decrease_Key(DHeap_Item item, int delta) {
		if (delta<1)
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
	 * O(dlogn)
	 * O(?)
	 */
	public void Delete(DHeap_Item item) {
		int place = item.getPos();
		switchPlaces(array, place, size-1);
		size--;
		heapifyDown(place);
		heapifyUp(place);
	}

	/**
	 * Return a sorted array containing the same integers in the input array.
	 * Sorting should be done using the DHeap.
	 * 
	 * O(dnlogn)
	 */
	public static int[] DHeapSort(int[] array) {
		int n = array.length;
		int[] result = new int[n];		
		DHeap_Item[] heapArray = new DHeap_Item[n];
		
		for (int i=0;i<n;i++)
			heapArray[i] = new DHeap_Item("",array[i]);
		
		DHeap heap = new DHeap(2,n);
		heap.arrayToHeap(heapArray);
		
		for (int i=0;i<n;i++){
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
	
	public static void main(String[] args) {
		DHeap hip1 = new DHeap(3, 5);
		System.out.println(hip1.child(0,4));
		DHeap_Item[] hip = new DHeap_Item[5];
		hip[0] = new DHeap_Item("a",15); 
		hip[1] = new DHeap_Item("b",10);
		hip[2] = new DHeap_Item("c",9);
		hip[3] = new DHeap_Item("d",4);
		hip[4] = new DHeap_Item("e",5);
		//for (DHeap_Item i:hip)
		//	System.out.println(i.getName());
		hip1.arrayToHeap(hip);
		//for (DHeap_Item i:hip1.array)
			//System.out.println(i.getName());
		System.out.println(hip1.Get_Min().getName());
	}
	
}
