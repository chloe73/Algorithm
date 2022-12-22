package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_14719_빗물 {
	
	static int H,W,result;
	static int[] heights;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		heights = new int[W];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<W;i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		result = 0;
		
		solve();
		
		System.out.println(result);
		
	}

	private static void solve() {
		
		for(int i=1;i<W-1;i++) {
			int left = 0;
			int right = 0;
			
			for(int j=0;j<i;j++) { // 왼쪽 탐색
				left = Math.max(left, heights[j]);
			}
			
			for(int j=i+1;j<W;j++) { // 오른쪽 탐색
				right = Math.max(right, heights[j]);
			}
			
			if(heights[i] < left && heights[i] < right) {
				result += Math.min(left, right) - heights[i];
			}
		}
		
	}
}
