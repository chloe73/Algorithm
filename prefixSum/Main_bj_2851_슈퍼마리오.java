package algo.prefixSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_2851_슈퍼마리오 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = 10;
		
		int[] arr = new int[N+1];
		int[] sum = new int[N+1];
		
		for(int i=1;i<=N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
			sum[i] = sum[i-1] + arr[i]; // 이전 인덱스까지의 총합 + 현재 값
		} // input end
		
		
		int result = 0;
		
		for(int num : sum) {
			int tempNum = Math.abs(num - 100);
			int tempResult = Math.abs(result - 100);
			
			if(tempResult < tempNum) continue;
			
			result = num;
			
		}
		
		System.out.println(result);
	}

}
