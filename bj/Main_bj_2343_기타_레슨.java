package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_2343_기타_레슨 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		int left = 0;
		int right = 0;
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			right += arr[i];
			left = Math.max(left, arr[i]);
		} // input end
		
		while(left <= right) {
			int mid = (left + right) / 2;
			
			int cnt = 0;
			int sum = 0;
			
			for(int i=0;i<N;i++) {
				if(sum + arr[i] > mid) {
					cnt++;
					sum = 0;
				}
				
				sum += arr[i];
			}
			
			if(sum > 0) cnt++;
			
			if(cnt > M) {
				left = mid + 1;
			}
			else {
				right = mid - 1;
			}
		}
		
		bw.write(left+"");
		
		bw.flush();
		br.close();
		bw.close();
	}

}
