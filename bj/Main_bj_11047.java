package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_11047 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		int result = 0;
		
		int rest = K;
		for(int i=N-1;i<N;i--) {
			if(arr[i] <= K) {
				int cnt = 0;
				while(true) {
					if(arr[i] * cnt > rest) break;
					
					cnt++;
				}
				cnt--;
				rest -= arr[i] * cnt;
				result += cnt;
				if(rest == 0) break;
			}
		}
		
		System.out.println(result);
	}

}
