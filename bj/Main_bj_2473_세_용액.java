package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2473_세_용액 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		long[] arr = new long[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Long.parseLong(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		long[] result = new long[3];
		long target = Math.abs(0-Long.MAX_VALUE);
		
		for(int i=0;i<N-2;i++) {
			int start = i;
			int mid = i+1;
			int end = N-1;
			
			while(mid < end) {
				long sum = arr[start] + arr[mid] + arr[end];
				
				if(target > Math.abs(sum)) {
					target = Math.abs(sum);
					result[0] = arr[start];
					result[1] = arr[mid];
					result[2] = arr[end];
				}
				
				if(sum == 0) {
					break;
				}
				else if(sum > 0) {
					end--;
				}
				else {
					mid++;
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(long i : result) {
			sb.append(i+" ");
		}
		
		bw.write(sb.toString()+"");
		bw.flush();
		br.close();
		bw.close();
	}

}
