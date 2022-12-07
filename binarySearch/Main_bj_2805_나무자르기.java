package algo.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2805_나무자르기 {
	
	static int N,M,result;
	static int[] arr,rest;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		rest = new int[N];
		
		binarySearch(0, arr[N-1]);
		
		System.out.println(result);
	}

	private static void binarySearch(int low, int high) {
		
		while(low <= high) {
			int mid = (low+high) / 2;
			long sum = 0;
			
			for(int i=0;i<N;i++) {
				if(arr[i] > mid) sum += arr[i] - mid;
			}
			
			if(M <= sum) low = mid +1;
			else high = mid - 1;
		}
		
		result = high;
	}
}
