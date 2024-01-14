package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_14002_가장_긴_증가하는_부분_수열_4 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] dp = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		int max = 1;
		dp[0] = 1;
		for(int i=1;i<N;i++) {
			dp[i] = 1;
			
			for(int j=0;j<i;j++) {
				if(arr[j] < arr[i]) {
					dp[i] =  Math.max(dp[i], dp[j]+1);
					max = Math.max(max, dp[i]);
				}
			}
		}

		sb.append(max+"\n");
		Stack<Integer> stack = new Stack<>();
		for(int i=N-1;i>=0;i--) {
			if(dp[i] == max) {
				stack.push(arr[i]);
				max--;
			}
		}
		
		while(!stack.isEmpty()) {
			sb.append(stack.pop()+" ");
		}
		
		System.out.println(sb.toString());
	}

}
