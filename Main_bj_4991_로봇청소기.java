package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_4991_로봇청소기 {
	
	static int W,H,sx,sy,result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<Point> dust = new ArrayList<>();
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
		
		while(true) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			if(W == 0 && H == 0) break;
			
			board = new char[H][W];
			for(int i=0;i<H;i++) {
				board[i] = br.readLine().toCharArray();
				for(int j=0;j<W;j++) {
					if(board[i][j] == 'o') {
						sx = i;
						sy = j;
					} else if(board[i][j] == '*') {
						dust.add(new Point(i, j));
					}
				}
			}
			
		}
	}

}
