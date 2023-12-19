package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_21921_블로그 {

	// 누적합 or 슬라이딩 윈도우
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int sum = 0;
		for(int i=0;i<X;i++) {
			sum += arr[i];
		}
		
		int max = sum;
		int maxCnt = 1;
		for(int i=X;i<N;i++) {
			sum += arr[i] - arr[i-X];
			if(max == sum) maxCnt++;
			else if(sum > max) {
				maxCnt = 1;
				max = sum;
			}
		}
		
		if(max == 0) {
			System.out.println("SAD");
		}
		else {
			System.out.println(max);
			System.out.println(maxCnt);
		}
	}

}
