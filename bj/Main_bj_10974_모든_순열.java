package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_10974_모든_순열 {
	
	static int N;
	static int[] arr;
	static boolean[] isChecked;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		isChecked = new boolean[N+1];
		sb = new StringBuilder();
		
		solve(0);
		
		System.out.println(sb.toString());
	}

	private static void solve(int cnt) {
		
		if(cnt == N) {
			for(int i=0;i<N;i++) {
				sb.append(arr[i]+" ");
			}
			sb.append("\n");
			
			return;
		}
		
		for(int i=1;i<=N;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				arr[cnt] = i;
				solve(cnt+1);
				isChecked[i] = false;
			}
		}
	}

}
