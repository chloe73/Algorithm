package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_15683_감시 {
	
	static int N,M,result;
	static int[][] board;
	static ArrayList<int[]> cctvList;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		cctvList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] >= 1 && board[i][j] <= 5)
					cctvList.add(new int[] {i,j});
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		
		int[] a = cctvList.get(0);
		int x = a[0];
		int y = a[1];
		solve(0,board[x][y],0);
		
		System.out.println(result);
	}

	private static void solve(int idx, int num, int cnt) {
		if(idx == cctvList.size()) {
			result = Math.min(result, cnt);
			return;
		}
		
		if(cnt > result) return;
		
		
	}
	
	private static int[][] copy() {
		int[][] arr = new int[N][M];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				arr[i][j] = board[i][j];
			}
		}
		
		return arr;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
