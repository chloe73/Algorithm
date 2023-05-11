package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_14502_연구소 {
	
	static int N,M,result;
	static int[][] board;
	static int[][] wall;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<Point> virusList;
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		wall = new int[N][M];
		virusList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) virusList.add(new Point(i, j));
			}
		} // input end
		
		wall = copy(board);
		
		comb(0);
		
		System.out.println(result);
	}

	private static void comb(int cnt) {
		if(cnt == 3) {
			spreadVirus();
			return;
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(wall[i][j] == 0) {
					wall[i][j] = 1;
					comb(cnt+1);
					wall[i][j] = 0;
				}
			}
		}
	}

	private static void spreadVirus() {
		
		int[][] copyWall = copy(wall);
		
		 Queue<Point> q = new LinkedList<>();
		for(int i=0;i<virusList.size();i++) q.add(virusList.get(i));
		
		while(!q.isEmpty()) {
			Point temp = q.poll();
			
			for(int i=0;i<4;i++) {
				int nx = temp.x + dx[i];
				int ny = temp.y + dy[i];
				
				if(isValid(nx, ny) && copyWall[nx][ny] == 0) {
					q.add(new Point(nx, ny));
					copyWall[nx][ny] = 2;
				}
			}
		}
		
		int safeCnt = safeArea(copyWall);
		result = Math.max(result, safeCnt);
	}

	private static int safeArea(int[][] copyWall) {
		int safe = 0;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(copyWall[i][j] == 0) safe++;
			}
		}
		
		return safe;
	}
	
	private static int[][] copy(int[][] arr) {
		
		int[][] copy = new int[N][M];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				copy[i][j] = arr[i][j];
			}
		}
		
		return copy;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 ||c<0 ||r>=N || c>=M) return false;
		return true;
	}
}
