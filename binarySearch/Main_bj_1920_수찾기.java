package algo.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_1920_수찾기 {
	
	static int N,M;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			int key = Integer.parseInt(st.nextToken());
			System.out.println(binarySearch(key, 0, N-1));
		}
	}

	private static int binarySearch(int key, int low, int high) {
		
		int mid;
		
		while(low <= high) {
			mid = (low+high) / 2;
			
			if(key == arr[mid]) return 1;
			
			else if(key < arr[mid]) high = mid - 1;
			
			else low = mid + 1;
		}
		return 0;
	}
}
