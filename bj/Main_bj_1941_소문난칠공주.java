package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_bj_1941_소문난칠공주 {
	
	static int result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[] visited;
	static int[] checked;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new char[5][5];
		for(int i=0;i<5;i++) {
			String s = br.readLine();
			board[i] = s.toCharArray();
		} // input end
		checked = new int[7];
		comb(0,0,0);
		
		System.out.println(result);
	}
	
	private static void comb(int cnt, int start, int dasomCnt) {
		if(cnt - dasomCnt >= 4) return;
		
		if(cnt == 7) {
			visited = new boolean[7];
			bfs(checked[0]/5, checked[0]%5);
			return;
		}
		
		for(int i=start;i<25;i++) {
			int row = i/5, col = i%5;
			checked[cnt] = i;
			comb(cnt+1, i+1,(board[row][col]=='S'?dasomCnt+1 : dasomCnt));
		}
	}

	private static void bfs(int i, int j) {
		int num = 1;
		visited[0] = true;
		Queue<int[]> q =new LinkedList<>();
		q.add(new int[] {i,j});
		
		while(!q.isEmpty()) {
			int[] rowCol = q.poll();
			int x = rowCol[0], y = rowCol[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 || ny<0 || nx>=5 ||ny>= 5) continue;
				
				int nxt = 5*nx + ny;
				
				for(int k=0;k<7;k++) {
					if(!visited[k] && checked[k] == nxt) {
						visited[k] = true;
						num++;
						q.add(new int[] {nx,ny});
					}
				}
			}
		}
		
		if(num == 7) result++;
	}

}
