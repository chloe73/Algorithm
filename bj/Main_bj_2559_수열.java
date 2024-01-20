package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_2559_수열 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[] sum = new int[N+1]; // 누적합
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			sum[i] = sum[i-1] + Integer.parseInt(st.nextToken());
		} // input end
		
		int result = Integer.MIN_VALUE;
		
		for(int i=K;i<=N;i++) {
			int num = sum[i] - sum[i-K];
			result = Math.max(result, num);
		}
		
		bw.write(result+"");
		
		bw.flush();
		br.close();
		bw.close();
	}

}
