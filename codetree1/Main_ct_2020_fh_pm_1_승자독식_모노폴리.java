package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_ct_2020_fh_pm_1_승자독식_모노폴리 {
	
	static int N,M,K,result;
	static int[][][] board; // 독점계약 표시 (플레이어 번호, 계약기간)
	// 1은 위, 2는 아래, 3은 왼쪽, 4는 오른쪽
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static TreeMap<Integer, Player> playerMap;
	static class Player {
		int x,y,d;
		int[][] dirOrder;
		public Player(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
			dirOrder = new int[4][4];
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 격자 크기
		M = Integer.parseInt(st.nextToken()); // 플레이어 수
		K = Integer.parseInt(st.nextToken()); // 독점 계약 턴 수

		board = new int[N][N][2];
		playerMap = new TreeMap<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 0) continue;
				board[i][j][0] = num;
				board[i][j][1] = K;
				playerMap.put(num, new Player(i, j, 0));
			}
		}
		
		// n+2번째 줄에는 각 플레이어의 초기 방향 d가 주어집니다. 1은 위, 2는 아래, 3은 왼쪽, 4는 오른쪽을 의미합니다.
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=M;i++) {
			playerMap.get(i).d = Integer.parseInt(st.nextToken())-1;
		}
		
		// n+3번째부터 m*4개의 줄에는 각 플레이어의 방향에 따른 이동 우선순위가 주어집니다.
		for(int i=1;i<=M;i++) {
			for(int k=0;k<4;k++) {
				st = new StringTokenizer(br.readLine());
				for(int l=0;l<4;l++) {
					playerMap.get(i).dirOrder[k][l] = Integer.parseInt(st.nextToken())-1;
				}
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		boolean flag = false;
		result = 0;
		while(result++ < 1000) {
			
			// 각 플레이어들은 한 칸씩 이동합니다.
			boolean[][] visited = new boolean[N][N];
			movePlayer(visited);
			
			// 독점 계약은 k만큼의 턴 동안만 유효합니다. k 턴이 지나게 되면 해당 칸은 다시 주인이 없는 칸으로 돌아가게 됩니다.
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(visited[i][j]) continue;
					if(board[i][j][1] > 1) {
						board[i][j][1]--;
						continue;
					}
					if(board[i][j][1] == 1) {
						board[i][j][0] = 0;
						board[i][j][1] = 0;
					}
				}
			}

			// 1번 플레이어만 남게 되기까지 걸린 턴의 수
			if(playerMap.size() == 1 && playerMap.containsKey(1)) {
				flag = true;
				break;
			}
		}
		
		if(!flag) result = -1;
	}

	private static void movePlayer(boolean[][] visited) {
		// 각 플레이어는 각 방향별로 이동 우선순위를 가지게 됩니다. 
		int[][] arr = new int[N][N];
		ArrayList<Integer> removeList = new ArrayList<>();
		
		for(int i : playerMap.keySet()) {
			Player temp = playerMap.get(i);
			int x = temp.x;
			int y = temp.y;
			int d = temp.d;	// 플레이어가 보고 있는 방향은 그 직전에 이동한 방향입니다.
			int nx = x;
			int ny = y;
			boolean isMove = false;
			
			// 우선 플레이어는 본인에게 인접한 상하좌우 4 칸 중 아무도 독점계약을 맺지 않은 칸으로 이동하고 
			for(int dir : temp.dirOrder[d]) {
				nx = x + dx[dir];
				ny = y + dy[dir];

				if(isValid(nx, ny) && board[nx][ny][0] == 0) {
					d = dir;
					isMove = true;
					break;
				}
			}
			
			if(!isMove) {
				// 만약 그러한 칸이 없을 경우에는 인접한 4방향 중 본인이 독점계약한 땅으로 이동합니다. 
				// 이때 이동할 수 있는 칸이 여러개일 수 있음으로 이동 우선순위에 따라 움직일 칸을 결정하게 됩니다. 
				
				for(int dir : temp.dirOrder[d]) {
					nx = x + dx[dir];
					ny = y + dy[dir];
					
					if(isValid(nx, ny) && board[nx][ny][0] == i) {
						d = dir;
						isMove = true;
						break;
					}
				}
			}
			
			if(isMove) {
				
				if(arr[nx][ny] == 0) {
					arr[nx][ny] = i;
					visited[nx][ny] = true;
					// 새로운 칸으로 이동하기
					playerMap.get(i).x = nx;
					playerMap.get(i).y = ny;
					playerMap.get(i).d = d;
//					board[nx][ny][0] = i;
//					board[nx][ny][1] = K;
				}
				else {
					if(arr[nx][ny] < i) {
						removeList.add(i);
					}
					else if(arr[nx][ny] > i) {
						removeList.add(arr[nx][ny]);
						arr[nx][ny] = i;
						// 새로운 칸으로 이동하기
						playerMap.get(i).x = nx;
						playerMap.get(i).y = ny;
						playerMap.get(i).d = d;
//						board[nx][ny][0] = i;
//						board[nx][ny][1] = K;
					}
				}
			}
		}
		
		// 모든 플레이어가 이동한 후 한 칸에 여러 플레이어가 있을 경우에는 가장 작은 번호를 가진 플레이어만 살아남고 
		// 나머지 플레이어는 게임에서 사라지게 됩니다.
		if(removeList.size() > 0) {
			for(int i : removeList) {
				playerMap.remove(i);
			}
		}
		
		for(int i : playerMap.keySet()) {
			Player p = playerMap.get(i);
			
			board[p.x][p.y][0] = i;
			board[p.x][p.y][1] = K;
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
