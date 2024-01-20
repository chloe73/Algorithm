package algo.SW_DX.day5_이분탐색;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Solution_촛불_이벤트 {
	
	static long N;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			N = Long.parseLong(br.readLine());
			
			long result = -1;
			long left = 1;
			long right = (long) Math.sqrt(N*2);
			
			while(left <= right) {
				long k = (left+right) / 2;
				long num = k * (k+1) / 2;
				
				if(num == N) {
					result = k;
					break;
				}
				
				if(num > N) {
					right = k-1;
				}
				else {
					left = k+1;
				}
			}
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}
