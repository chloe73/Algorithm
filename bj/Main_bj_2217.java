package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_2217 {
	
	static int N,result;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(arr);
		
		result = Integer.MIN_VALUE;
		
		for(int i=0;i<N;i++) {
			result = Math.max(result, arr[i] * (N-i));
		}
		
		System.out.println(result);
	}

}
