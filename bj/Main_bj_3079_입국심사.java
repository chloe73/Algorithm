package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_3079_입국심사 {
	
	static int N;
	static long M,Max,result;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Long.parseLong(st.nextToken());
		
		arr = new int[N];
		Max = 0;
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
			Max = Math.max(Max, arr[i]);
		} // input end
		
		Arrays.sort(arr);
		
		result = Long.MAX_VALUE;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		long left = 0;
		long right = M * Max;
		
		while(left <= right) {
			long mid = (left + right) / 2;
			long sum = 0;
			
			for(long index : arr) {
				long count = mid / index;
				
				if(sum >= M) {
					break;
				}
				
				sum += count;
			}
			
			if(sum >= M) {
				right = mid - 1;
				result = Math.min(result, mid);
			}
			else {
				left = mid + 1;
			}
		}
	}

}
