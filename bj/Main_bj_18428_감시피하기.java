package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_18428_감시피하기 {
	
	static int N;
	static String result;
	static char[][] wall;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static ArrayList<Point> teacher;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		wall = new char[N][N];
		
		teacher = new ArrayList<>();
		for(int i=0;i<N;i++) {
			 st = new StringTokenizer(br.readLine());
			 for(int j=0;j<N;j++) {
				 wall[i][j] = st.nextToken().charAt(0);
				 if(wall[i][j] == 'T') teacher.add(new Point(i, j));
			 }
		} // input end
		result = "NO";
		
		solve(0,0,0);
		
		System.out.println(result);
	}

	private static void solve(int cnt,int x,int y) {
		
		// 장애물 3개를 다 골랐을 경우
		if(cnt == 3) {
			if(check()) result = "YES";
			return;
		}
		
		for(int i=x;i<N;i++) {
			for(int j=y;j<N;j++) {
				if(wall[i][j] == 'X') {
					wall[i][j] = 'O';
					solve(cnt+1, i, j+1);
					wall[i][j] = 'X';
				}
			}
			y = 0;
		}
	}
	
	private static boolean check() {
		
		for(int i=0;i<teacher.size();i++) {
			int x = teacher.get(i).x;
			int y = teacher.get(i).y;
			
			for(int d=0;d<4;d++) {
				int nx = x;
				int ny = y;

				while(true) {
					nx += dx[d];
					ny += dy[d];
					
					if(!isValid(nx, ny)) break;
					
					if(wall[nx][ny] == 'S') return false;
					
					if(wall[nx][ny] == 'O') break;
				}
			}
		}
		
		return true;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
