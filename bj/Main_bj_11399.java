package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_11399 {
	
	static int N;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		int result = Integer.MAX_VALUE;
		
		Arrays.sort(arr);
		
		int sum = arr[0];
		int num = arr[0];
		for(int i=1;i<N;i++) {
			sum += num + arr[i];
			num += arr[i];
		}
		
		// 1 2 3 3 4
		// 1 + (1+2) + (1+2+3) + (1+2+3+3) + (1+2+3+3+4) = 32
		result = sum;
		
		System.out.println(result);
	}

}
