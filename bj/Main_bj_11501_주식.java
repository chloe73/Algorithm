package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_11501_주식 {

	static int N;
	static int[] arr;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			} // input end
			
			long result = 0;
			int price = arr[N-1];
			for(int i=N-2;i>=0;i--) {
				int temp = arr[i];
				
				if(temp > price) {
					price = temp;
					continue;
				}
				
				if(temp < price) {
					result += (price - temp);
					
				}
			}
			sb.append(result+"\n");
		}
		System.out.println(sb.toString());
	}

}
