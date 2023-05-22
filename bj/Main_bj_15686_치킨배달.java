package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_15686_치킨배달 {
	
	static int N,M,result;
	static int[][] board;
	static boolean[] isChecked;
	static ArrayList<int[]> chickenList, homeList;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		chickenList = new ArrayList<>();
		homeList = new ArrayList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) chickenList.add(new int[] {i,j});
				if(board[i][j] == 1) homeList.add(new int[] {i,j});
			}
		} // input end
		
		isChecked = new boolean[chickenList.size()];
		result = Integer.MAX_VALUE;
		
		comb(0,0);
		
		System.out.println(result);
	}

	private static void comb(int idx, int k) {
		
		if(k == M) {
			// 치킨집 n개 선택완료
			int sum = 0;
			
			for(int[] h : homeList) {
				int x = h[0];
				int y = h[1];
				int tempDistance = Integer.MAX_VALUE;
				
				for(int i=0;i<chickenList.size();i++) {
					if(isChecked[i]) {
						int[] tmp = chickenList.get(i);
						tempDistance = Math.min(tempDistance, Math.abs(x-tmp[0]) + Math.abs(y-tmp[1]));
					}
				}
				
				sum += tempDistance;
			}

			// 치킨거리 계산
			
			result = Math.min(result, sum);
			return;
		}
		
		for(int i=idx;i<chickenList.size();i++) {
			isChecked[i] = true;
			comb(i+1, k+1);
			isChecked[i] = false;
		}
	}

}