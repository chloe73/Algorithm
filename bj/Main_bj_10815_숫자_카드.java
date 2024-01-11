package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 이분 탐색
public class Main_bj_10815_숫자_카드 {
	
	static int N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		// -10, 2, 3, 6, 10
		Arrays.sort(arr);
		
		int M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			int num = Integer.parseInt(st.nextToken());
			sb.append(solve(num)+" ");
		}

		System.out.println(sb.toString());
	}

	private static int solve(int num) {
		
		int left = 0;
		int right = N-1;
		
		while(left < right) {
			int mid = (left+right) / 2;
			
			if(arr[mid] == num) return 1;
			
			else if(num > arr[mid]) {
				left = mid+1;
			}
			else if(num < arr[mid]) {
				right = mid -1;;
			}
		}
		
		return 0;
	}

}
