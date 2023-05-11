package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1189_컴백홈 {
	
	static int R,C,K,result;
	static int startX,startY,endX,endY;
	static char[][] road;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[][] visited;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		road = new char[R][C];
		visited = new boolean[R][C];
		for(int i=0;i<R;i++) {
			String s = br.readLine();
			road[i] = s.toCharArray();
		} // input end
		
		startX = R-1; startY = 0;
		endX = 0; endY = C-1;
		visited[startX][startY] = true;
		
		solve(startX,startY,1);
		
		System.out.println(result);
	}

	private static void solve(int x, int y, int cnt) {
		
		if(x == endX && y == endY) {
			if(cnt == K) {
				result++;
			}
			return;
		}
		
		if(cnt > K) return;
		
		for(int d=0;d<4;d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			// 범위 밖을 벗어났으면 pass
			if(nx<0 || ny<0 || nx>=R || ny>=C) continue;
			
			// 이미 방문한 적이 있으면 pass
			if(visited[nx][ny]) continue;
			
			// T인 경우 
			if(road[nx][ny]=='T') continue;
			
			visited[nx][ny] = true;
			solve(nx,ny,cnt+1);
			visited[nx][ny] = false;
		}
		
	}

}
