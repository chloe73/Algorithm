package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main_bj_1300_K번째_수 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());

		long result = 0L;
		long left = 1;
		long right = K;
		
		while(left <= right) {
			long mid = (left+right) / 2;
			
			long sum = 0;
			for(int i=1;i<=N;i++) {
				sum += Math.min(mid/i, N);
			}
			
			if(sum >= K) {
				result = mid;
				right = mid-1;
			}
			else left = mid+1;
		}
		
		bw.write(result+"");
		bw.flush();
		br.close();
		bw.close();
	}

}
