package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_1940_주몽 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		int left = 0;
		int right = N-1;
		int result = 0;
		
		while(left < right) {
			int sum = arr[left] + arr[right];
			
			if(sum < M) {
				left++;
				continue;
			}
			
			else if(sum == M) {
				result++;
				right--;
				continue;
			}
			
			else {
				right--;
				continue;
			}
		}

		bw.write(result+"");
		bw.flush();
		br.close();
		bw.close();
	}

}
