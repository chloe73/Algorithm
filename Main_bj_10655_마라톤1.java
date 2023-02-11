package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_10655_마라톤1 {
	
	static int N, result,dist;
	static int[][] checkPoint;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		checkPoint = new int[N][2];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			checkPoint[i][0] = x;
			checkPoint[i][1] = y;
			
			if(i >= 1) { // 총 거리 계산
				dist += Math.abs(checkPoint[i][0] - checkPoint[i-1][0]) + Math.abs(checkPoint[i][1]-checkPoint[i-1][1]);
			}
		}
		result = Integer.MAX_VALUE; // 최솟값을 반환해야 함
		for(int i=1;i<N-1;i++) {
			int temp = Math.abs(checkPoint[i][0]-checkPoint[i-1][0])
					+ Math.abs(checkPoint[i][1]-checkPoint[i-1][1])
					+ Math.abs(checkPoint[i][0]-checkPoint[i+1][0])
					+ Math.abs(checkPoint[i][1]-checkPoint[i+1][1]);
			
			// 현재 체크포인트를 건너 뛴다면
			int jump = Math.abs(checkPoint[i-1][0] - checkPoint[i+1][0])
					+ Math.abs(checkPoint[i-1][1] - checkPoint[i+1][1]);
			
			result = Math.min(result, dist - temp+jump);
		}
		
		System.out.println(result);
	}

}
