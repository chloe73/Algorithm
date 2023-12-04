package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_1027_고층_건물 {
	
	static int[] arr,visible;
	static int N;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		visible = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		solve();
		
		Arrays.sort(visible); // 오름차순 정렬
		System.out.println(visible[N-1]); // visible의 가장 마지막 원소 값을 출력하면 됨.
	}

	private static void solve() {
		
		for(int i=0;i<N-1;i++) {
			// 현재 건물에서 바로 옆 건물들은 무조건 보인다.
			visible[i] += 1;
			visible[i+1] += 1;
			
			double incline = arr[i+1] - arr[i];
			for(int j=i+2;j<N;j++) {
				double nextIncline = (double)(arr[j] - arr[i]) / (j-i);
				if(nextIncline <= incline) continue;
				incline = nextIncline;
				visible[i]++;
				visible[j]++;
			}
		}
		
	}

}
