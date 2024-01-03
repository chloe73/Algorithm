package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 2019(상) 오전 1번.
// Gold 4
public class Main_ct_2019_fh_am_1_시공의_돌풍 {
	
	static int N,M,T,result;
	static int ax,bx; // 시공의 돌풍 위치 좌표
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		boolean flag = false;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(!flag && board[i][j] == -1) {
					ax = i;
					bx = i+1;
					flag = true;
				}
			}
		} // input end
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		while(T-- > 0) {
			// 1. 먼지가 인접한 4방향의 상하좌우 칸으로 확산됩니다.
			spread_dust();
			
			
			// 2. 시공의 돌풍이 청소를 시작합니다.
			clean();
//			for(int i=0;i<N;i++) {
//				for(int j=0;j<M;j++) {
//					System.out.print(board[i][j]+" ");
//				}
//				System.out.println();
//			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == -1) continue;
				result += board[i][j];
			}
		}
	}
	
	private static void clean() {
		// a. 시공의 돌풍의 윗칸에서는 반시계 방향으로 바람을 일으키며, 아랫칸에서는 시계 방향으로 바람을 일으킵니다.
		// b. 바람이 불면 먼지가 바람의 방향대로 모두 한 칸씩 이동합니다.
		// c. 시공의 돌풍에서 나온 바람은 먼지가 없는 청정한 바람이고 시공의 돌풍으로 들어간 먼지는 사라집니다.
		// 시공의 돌풍은 맨 윗 행과 맨 아랫 행과 적어도 두 칸 이상 떨어져 있습니다.
		
		// 윗부분
		// ↓
		for(int x=ax-1;x>=0;x--) {
			if(board[x+1][0] == -1) {
				board[x][0] = 0;
				continue;
			}
			board[x+1][0] = board[x][0];
			board[x][0] = 0;
		}
		
		// ←
		for(int y=1;y<M;y++) {
			board[0][y-1] = board[0][y];
			board[0][y] = 0;
		}
		
		// ↑
		for(int x=1;x<=ax;x++) {
			board[x-1][M-1] = board[x][M-1];
			board[x][M-1] = 0;
		}
		
		// →
		for(int y=M-2;y>=1;y--) {
			board[ax][y+1] = board[ax][y];
			board[ax][y] = 0;
		}
		
		// 아랫부분
		// ↑
		for(int x=bx+1;x<N;x++) {
			if(board[x-1][0] == -1) {
				board[x][0] = 0;
				continue;
			}
			board[x-1][0] = board[x][0];
			board[x][0] = 0;
		}
		
		// ←
		for(int y=1;y<M;y++) {
			board[N-1][y-1] = board[N-1][y];
			board[N-1][y] = 0;
		}
		
		// ↓
		for(int x=N-2;x>=bx;x--) {
			board[x+1][M-1] = board[x][M-1];
			board[x][M-1] = 0;
		}
		
		// →
		for(int y=M-2;y>=1;y--) {
			board[bx][y+1] = board[bx][y];
			board[bx][y] = 0;
		}
	}

	private static void spread_dust() {
		
		int[][] arr = new int[N][M];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == -1) continue;
				int cnt = 0; // 인접 방향 중 확산되는 방향의 개수

				// b. 확산되는 양은 원래 칸의 먼지의 양에 5를 나눈 값이며, 편의상 소숫점은 버립니다.
				int num = board[i][j] / 5;
				
				for(int d=0;d<4;d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					// a. 인접한 방향에 시공의 돌풍이 있거나, 방의 범위를 벗어난다면 해당 방향으로는 확산이 일어나지 않습니다.
					if(!is_valid(nx, ny)) continue;
					
					if(board[nx][ny] == -1) continue;
					
					arr[nx][ny] += num;
					cnt++;
				}
				
				// c. 각 칸에 확산될 때마다 원래 칸의 먼지의 양은 확산된 먼지만큼 줄어듭니다.
				arr[i][j] -= (cnt*num);
			}
		}
		
		// d. 확산된 먼지는 방에 있는 모든 먼지가 확산을 끝낸 다음에 해당 칸에 더해지게 됩니다.
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == -1) continue;
				board[i][j] += arr[i][j];
			}
		}
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
