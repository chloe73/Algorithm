package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_7562 {
	
	static int L,tx,ty,ex,ey,result;
	static int[] dx = {-2,-2,-1,-1,2,2,1,1};
	static int[] dy = {-1,1,-2,2,-1,1,-2,2};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for(int tc=1;tc<=T;tc++) {
			L = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			tx = Integer.parseInt(st.nextToken());
			ty = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			ex = Integer.parseInt(st.nextToken());
			ey = Integer.parseInt(st.nextToken());
			
			result = Integer.MAX_VALUE;
			solve();
			sb.append(result+"\n");
		}

		System.out.println(sb.toString());
	}

	private static void solve() {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {tx,ty,0});
		boolean[][] visited = new boolean[L][L];
		visited[tx][ty] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			if(x == ex && y == ey) {
				result = Math.min(result, cnt);
				continue;
			}
			
			for(int d=0;d<8;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny) || visited[nx][ny]) continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny,cnt+1});
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=L || c>=L) return false;
		return true;
	}
}
