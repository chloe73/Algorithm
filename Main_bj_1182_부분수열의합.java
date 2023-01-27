package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1182_부분수열의합 {
	
	static int N,S,result;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		dfs(0,0);
		
		if(S == 0) result--;
		
		System.out.println(result);
	}

	private static void dfs(int depth, int sum) {
		
		if(depth == N) {
			if(sum == S) result++;
			return;
		}
		
		dfs(depth+1,sum+arr[depth]);
		
		dfs(depth+1,sum);
	}

}
