package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_1868_파핑파핑_지뢰찾기 {
	
	static int N,result;
	static int[][] board;
	static int[] dx = {0,1,0,-1, 1,1,-1,-1};
	static int[] dy = {1,0,-1,0, 1,-1,1,-1};
	// 방향 : 8가지()
//	static int[] dx = {-1,1,0,1,1,1,0,-1};
//	static int[] dy = {0,1,1,1,0,-1,-1,-1};
	static boolean[][] visited;

	public static void main(String[] args) throws IOException{
		System.setIn(new FileInputStream("input/input_1868.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			visited = new boolean[N][N];
			for(int i=0;i<N;i++) {
				String s = br.readLine();
				for(int j=0;j<N;j++) {
					if(s.charAt(j) == '*') {
						visited[i][j] = true;
						board[i][j] = -1; // 지뢰는 -1로 표시
					} else board[i][j] = 0;
				}
			} // input end
			
			// board 지뢰 개수 탐색
			for(int x=0;x<N;x++) {
				for(int y=0;y<N;y++) {
					int cnt = 0;
					for(int k=0;k<8;k++) {
						int nx = x + dx[k];
						int ny = y + dy[k];
						
						if(nx<0 || ny<0 || nx>=N ||ny>=N) continue;
						
						// 지뢰 발견하면 1 증가
						if(board[nx][ny] == -1) cnt++;
					}
					
					if(board[x][y] == 0) board[x][y] = cnt;
				}
			}
			result = 0;
			
			// 0인 칸 방문 처리 
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(!visited[i][j] && board[i][j] == 0) {
						bfs(i,j);
						result++;
					}
				}
			}
			
			// 나머지 방문 안된것들 처리
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(!visited[i][j]) result++;
				}
			}
			
			sb.append("#"+tc+" ").append(result).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	private static void bfs(int i, int j) {
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(new int[] {i,j});
		visited[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<8;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 || ny<0 || nx>=N ||ny>=N) continue;
				
				if(visited[nx][ny]) continue;
				
				if(board[nx][ny] == 0) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
				}
				
				else if(board[nx][ny] != 0) visited[nx][ny] = true;
			}
		}
	}

}
