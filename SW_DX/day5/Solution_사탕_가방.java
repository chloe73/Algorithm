package algo.SW_DX.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_사탕_가방 {
	
	static int N;
	static long M;
	static long[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Long.parseLong(st.nextToken());
			
			arr = new long[N];
			long max = 0;
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arr[i] = Long.parseLong(st.nextToken());
				max = Math.max(max, arr[i]);
			} // input end
			
			long left = 1;
			long right = max;
			long result = 0;
			
			while(left <= right) {
				long mid = (left+right) / 2;
				long temp = 0;
				
				for(int i=0;i<N;i++) {
					temp += (arr[i] / mid);
				}
				
				if(temp < M) {
					right = mid-1;
				}
				else {
					result = mid;
					left = mid+1;
				}
			}
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}
