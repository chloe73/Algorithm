package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1561_놀이공원 {
	
	static long N,result;
	static int M;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Long.parseLong(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[M+1];
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=M;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		if(N <= M) {
			result = N;
		}
		else {
			result = binarySearch(); // 총 소요시간
			long child = M; // 총 소요시간 직전까지 놀이기구 탄 인원 수
			for(int i=1;i<=M;i++) {
				child += ((result-1)/arr[i]);
			}
			
			for(int i=1;i<=M;i++) {
				if(result % arr[i] == 0) {
					child++;
				}
				if(child == N) {
					result = i;
					break;
				}
			}
		}

		System.out.println(result);
	}
	
	private static long binarySearch() {
		long left = 0;
		long right = N * 30;
		
		while(left <= right) {
			long mid = (left + right) / 2;
			
			long childNum = M;
			for(int i=1;i<=M;i++) {
				childNum += (mid/arr[i]);
			}
			
			if(childNum < N) {
				left = mid+1;
			}
			else {
				right = mid-1;
			}
		}
		
		return left;
	}

}
