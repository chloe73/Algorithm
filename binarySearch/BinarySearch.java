package algo.binarySearch;

public class BinarySearch {
	
	static int[] arr = {1,3,5,7,8,10,20,35,99,100};

	public static void main(String[] args) {
		System.out.println("이진 탐색 : 숫자 20 찾기");
		System.out.print("숫자 20의 index 번호 : "+binarySearch(20, 0, arr.length-1));
	}

	private static int binarySearch(int key, int low, int high) {
		
		int mid;
		
		while(low <= high) {
			mid = (low+high) / 2;
			
			if(key == arr[mid]) return mid;
			
			else if(key < arr[mid]) high = mid - 1;
			
			else low = mid + 1;
		}
		
		return -1;
	}
}
