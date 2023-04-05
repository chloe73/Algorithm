package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_ct_싸움땅 {
	
	static int N,M,K;
	static int[] result;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static ArrayList<Integer>[][] boardList, gunBoardList;
	static HashMap<Integer, Player> playerMap;
	static class Player {
		int x,y,d,s,gun;
		public Player(int x, int y, int d, int s, int gun) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.s = s;
			this.gun = gun;
		}
		@Override
		public String toString() {
			return "Player [x=" + x + ", y=" + y + ", d=" + d + ", s=" + s + ", gun=" + gun + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		boardList = new ArrayList[N][N];
		gunBoardList = new ArrayList[N][N];
		playerMap = new HashMap<>();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				boardList[i][j] = new ArrayList<>();
				gunBoardList[i][j] = new ArrayList<>();
			}
		}
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num > 0) gunBoardList[i][j].add(num);
			}
		}
		
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			playerMap.put(i, new Player(x, y, d, s, 0));
			boardList[x][y].add(i);
		} // input end
		
		result = new int[M];
		
		solve();
		
		for(int i : result) System.out.print(i+" ");
		System.out.println();
	}

	private static void solve() {
		for(int i=0;i<K;i++) {
			System.out.println("========= " + i + "=========");
			move_player();	
		}
	}

	private static void move_player() {
		// 1-1. 첫 번째 플레이어부터 순차적으로 본인이 향하고 있는 방향대로 한 칸만큼 이동합니다. 
		for(int temp : playerMap.keySet()) {
			int x = playerMap.get(temp).x;
			int y = playerMap.get(temp).y;
			int d = playerMap.get(temp).d;
			remove_list(temp, x, y, true);

			// 만약 해당 방향으로 나갈 때 격자를 벗어나는 경우에는 정반대 방향으로 방향을 바꾸어서 1만큼 이동합니다.
			if(!isValid(x+dx[d], y+dy[d])) d = change_dir(d);

			// 한 칸 이동
			x += dx[d];
			y += dy[d];
			
			playerMap.get(temp).x = x;
			playerMap.get(temp).y = y;
			playerMap.get(temp).d = d;
			boardList[x][y].add(temp);
			
			// 2-1. 만약 이동한 방향에 플레이어가 없다면 해당 칸에 총이 있는지 확인합니다. 
			if(boardList[x][y].size() == 1) check_gun(temp,x,y);

			// 2-2-1. 만약 이동한 방향에 플레이어가 있는 경우에는 두 플레이어가 싸우게 됩니다. 
			else if(boardList[x][y].size() == 2) {
				int target = boardList[x][y].get(0);
				// 해당 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합을 비교하여 더 큰 플레이어가 이기게 됩니다. 
				// 만일 이 수치가 같은 경우에는 플레이어의 초기 능력치가 높은 플레이어가 승리하게 됩니다. 
				// 이긴 플레이어는 각 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합의 차이만큼을 포인트로 획득하게 됩니다.
				int tempSum = playerMap.get(temp).s + playerMap.get(temp).gun;
				int targetSum = playerMap.get(target).s + playerMap.get(target).gun;
				
				int winnerIdx = 0;
				int loserIdx = 0;
				int dif = Math.abs(tempSum-targetSum);
				
				if(tempSum > targetSum) {
					winnerIdx = temp;
					loserIdx = target;
				}
				else if(tempSum < targetSum) {
					winnerIdx = target;
					loserIdx = temp;
				}
				else {
					if(playerMap.get(temp).s > playerMap.get(target).s) {
						winnerIdx = temp;
						loserIdx = target;
					}
					else {
						winnerIdx = target;
						loserIdx = temp;
					}
				}
				result[winnerIdx-1] += dif;
				boardList[x][y].clear();
				
				move_loser(loserIdx,x,y);
				
				// 2-2-3. 이긴 플레이어는 승리한 칸에 떨어져 있는 총들과 원래 들고 있던 총 중 가장 공격력이 높은 총을 획득하고, 
				// 나머지 총들은 해당 격자에 내려 놓습니다.
				if(gunBoardList[x][y].size() > 0) check_gun(winnerIdx, x, y);
				boardList[x][y].add(winnerIdx);
			}
			System.out.println(playerMap.get(temp).toString());
		}
	}
	
	private static void move_loser(int loserIdx, int x, int y) {
		// 2-2-2. 진 플레이어는 본인이 가지고 있는 총을 해당 격자에 내려놓고, 
		Player p = playerMap.get(loserIdx);
		gunBoardList[x][y].add(p.gun);
		playerMap.get(loserIdx).gun = 0;
		int d = p.d;
		// 해당 플레이어가 원래 가지고 있던 방향대로 한 칸 이동합니다.
		if(!isValid(x+dx[d], y+dy[d]) || boardList[x+dx[d]][y+dy[d]].size() > 0) {
			// 만약 이동하려는 칸에 다른 플레이어가 있거나 격자 범위 밖인 경우에는 오른쪽으로 90도씩 회전하여 빈 칸이 보이는 순간 이동합니다. 
			while(true) {
				if(d == 3) d = 0;
				else d++;
				
				if(isValid(x+dx[d], y+dy[d]) && boardList[x+dx[d]][y+dy[d]].size() == 0) break;
			}
		}
		playerMap.get(loserIdx).x += dx[d];
		playerMap.get(loserIdx).y += dy[d];
		playerMap.get(loserIdx).d = d;
		int nx = playerMap.get(loserIdx).x;
		int ny = playerMap.get(loserIdx).y;
		boardList[nx][ny].add(loserIdx); // 새로 이동한 칸에 idx 추가
		
		// 만약 해당 칸에 총이 있다면, 해당 플레이어는 가장 공격력이 높은 총을 획득하고 나머지 총들은 해당 격자에 내려 놓습니다.
		check_gun(loserIdx, nx, ny);
	}

	private static void check_gun(int temp, int x, int y) {
		// 총이 있는 경우, 해당 플레이어는 총을 획득합니다. 
		if(gunBoardList[x][y].size() > 0) {
			
			if(playerMap.get(temp).gun != 0) {
				// 플레이어가 이미 총을 가지고 있는 경우에는 놓여있는 총들과 플레이어가 가지고 있는 총 가운데 공격력이 더 쎈 총을 획득하고, 
				// 나머지 총들은 해당 격자에 둡니다.
				int tempGun = playerMap.get(temp).gun;
				Collections.reverse(gunBoardList[x][y]);
				int num = gunBoardList[x][y].get(0);
				if(num > tempGun) {
					gunBoardList[x][y].remove(0);
					playerMap.get(temp).gun = num;
					gunBoardList[x][y].add(tempGun);
				}
			}
			else {
				playerMap.get(temp).gun = gunBoardList[x][y].get(0);
				gunBoardList[x][y].clear();
			}
		}
		
	}

	private static void remove_list(int val, int x, int y, boolean flag) {
		if(flag) {
			if(boardList[x][y].contains(val)) {
				int idx = boardList[x][y].indexOf(val);
				boardList[x][y].remove(idx);				
			}
		}
		else {
			if(gunBoardList[x][y].contains(val)) {
				int idx = gunBoardList[x][y].indexOf(val);
				gunBoardList[x][y].remove(idx);				
			}
		}
	}

	private static int change_dir(int dir) {
		if(dir == 0) return 2;
		else if(dir == 1) return 3;
		else if(dir == 2) return 0;
		else return 1;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
