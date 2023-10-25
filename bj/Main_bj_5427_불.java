package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_5427_불 {
	
	static int H,W,result;
	static int tx,ty;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static Queue<int[]> sq, fq; // 상근이 이동 칸
	static boolean[][] svisited, fvisited; // 상근이가 방문한 칸

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int tc = Integer.parseInt(br.readLine());
		for(int t=0;t<tc;t++) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			board = new char[H][W];
			sq = new LinkedList<>();
			fq = new LinkedList<>();
			svisited = new boolean[H][W];
			fvisited = new boolean[H][W];
			result = Integer.MAX_VALUE;
			
			for(int i=0;i<H;i++) {
				String s = br.readLine();
				for(int j=0;j<W;j++) {
					board[i][j] = s.charAt(j);
					if(board[i][j] == '*') {
						fq.add(new int[] {i,j});
						fvisited[i][j] = true;
					}
					if(board[i][j] == '@') {
						board[i][j] = '.';
						tx = i;
						ty = j;
						sq.add(new int[] {i,j,0});
						svisited[i][j] = true;
					}
				}
			} // input end
			
			solve();
			
			if(result == Integer.MAX_VALUE) {
				sb.append("IMPOSSIBLE").append("\n");
			}
			else {
				sb.append(result).append("\n");
			}
		}
		
		System.out.println(sb.toString());

	}

	private static void solve() {
		
		while(!is_out(tx, ty)) {
			
			// 1. 불이 먼저 이동한다.
//			'.': 빈 공간
//			'#': 벽
//			'@': 상근이의 시작 위치
//			'*': 불
			move_fire();
			
			// 2. 상근이가 그 다음 이동한다.
			// 현재 위치에서 아무데도 이동할 곳이 없으면 IMPOSSIBLE
			// 모두 벽이거나 불일때
			// 불에 잡아먹혀도 IMPOSSIBLE
			if(!move_sang()) break;
			
		}
		
		return;
	}
	
	private static boolean move_sang() {
		// 상근이는 동서남북 인접한 칸으로 이동할 수 있으며, 1초가 걸린다.
		// 상근이는 벽을 통과할 수 없고, 불이 옮겨진 칸 또는 이제 불이 붙으려는 칸으로 이동할 수 없다.
		// 상근이가 있는 칸에 불이 옮겨옴과 동시에 다른 칸으로 이동할 수 있다.

		int size = sq.size();
		boolean flag = false;
		
		for(int i=0;i<size;i++) {
			int[] temp = sq.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(is_out(nx, ny)) {
					if(result > cnt) {
						tx = nx;
						ty = ny;
						result = cnt+1;
						flag = false;
					}
					continue;
				}
				
				if(svisited[nx][ny] || board[nx][ny] == '#') continue;
				
				if(board[nx][ny] == '.') {
					svisited[nx][ny] = true;
					sq.add(new int[] {nx,ny,cnt+1});
					flag = true;
				}
			}
			
		}
		
		return flag;
	}

	private static void move_fire() {
		// 매 초마다, 불은 동서남북 방향으로 인접한 빈 공간으로 퍼져나간다.
		// 벽에는 불이 붙지 않는다.
		
		int size = fq.size();
		for(int i=0;i<size;i++) {
			int[] temp = fq.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(is_out(nx, ny) || fvisited[nx][ny] || board[nx][ny] == '#') continue;
				
				if(board[nx][ny] == '.') {
					fvisited[nx][ny] = true;
					fq.add(new int[] {nx,ny});
					board[nx][ny] = '*';
				}
			}
		}
		
	}

	private static boolean is_out(int r, int c) {
		if(r<0 || c<0 || r>=H || c>=W) return true;
		return false;
	}

}