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
		for(int i=1;i<=K;i++) {
			move_player();
			System.out.println("===== " +i + " =====");
			print();
		}
	}
	
	private static void print() {
		for(int temp : playerMap.keySet()) {
			System.out.println(playerMap.get(temp).toString());
		}
	}

	private static void move_player() {
		// 1-1. 첫 번째 플레이어부터 순차적으로 본인이 향하고 있는 방향대로 한 칸만큼 이동합니다. 
		for(int temp : playerMap.keySet()) {
			int x = playerMap.get(temp).x;
			int y = playerMap.get(temp).y;
			int d = playerMap.get(temp).d;
			remove_list(temp, x, y, true); // 기존 칸에서 정보 삭제
			
			// 만약 해당 방향으로 나갈 때 격자를 벗어나는 경우에는 정반대 방향으로 방향을 바꾸어서 1만큼 이동합니다.
			if(!isValid(x+dx[d], y+dy[d])) d = change_dir(d);
			
			int nx = x + dx[d];
			int ny = y + dy[d];
			playerMap.get(temp).x = nx;
			playerMap.get(temp).y = ny;
			playerMap.get(temp).d = d;
			boardList[nx][ny].add(temp); // 새로운 칸으로 이동 완료
			
			// 2-1. 만약 이동한 방향에 플레이어가 없다면 해당 칸에 총이 있는지 확인합니다.
			if(boardList[nx][ny].size() == 1) {
				
				// 총이 있는 경우, 해당 플레이어는 총을 획득합니다. 
				if(gunBoardList[nx][ny].size() > 0) {
					Collections.sort(gunBoardList[nx][ny]);
					int lastIdx = gunBoardList[nx][ny].size()-1;
					
					// 플레이어가 이미 총을 가지고 있는 경우에는 놓여있는 총들과 플레이어가 가지고 있는 총 가운데 공격력이 더 쎈 총을 획득하고, 나머지 총들은 해당 격자에 둡니다.
					if(playerMap.get(temp).gun > 0) {
						if(playerMap.get(temp).gun < gunBoardList[nx][ny].get(lastIdx)) {
							gunBoardList[nx][ny].add(playerMap.get(temp).gun);
							playerMap.get(temp).gun = gunBoardList[nx][ny].get(lastIdx);
							gunBoardList[nx][ny].remove(lastIdx);
						}
					}
					else {
						playerMap.get(temp).gun = gunBoardList[nx][ny].get(lastIdx);
						gunBoardList[nx][ny].remove(lastIdx);
					}
				}
			}
			// 2-2-1. 만약 이동한 방향에 플레이어가 있는 경우에는 두 플레이어가 싸우게 됩니다.
			// 해당 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합을 비교하여 더 큰 플레이어가 이기게 됩니다. 
			else if(boardList[nx][ny].size() == 2) {
				int original = boardList[nx][ny].get(0);
				int tempSum = playerMap.get(temp).s + playerMap.get(temp).gun;
				int originalSum = playerMap.get(original).s + playerMap.get(original).gun;
				int dif = Math.abs(tempSum-originalSum);
				
				int winnerIdx = 0;
				int loserIdx = 0;
				
				if(tempSum > originalSum) {
					winnerIdx = temp;
					loserIdx = original;
				}
				else if(tempSum < originalSum) {
					winnerIdx = original;
					loserIdx = temp;
				}
				else {
					// 만일 이 수치가 같은 경우에는 플레이어의 초기 능력치가 높은 플레이어가 승리하게 됩니다.
					if(playerMap.get(temp).s > playerMap.get(original).s) {
						winnerIdx = temp;
						loserIdx = original;
					}
					else {
						winnerIdx = original;
						loserIdx = temp;
					}
				}
				// 이긴 플레이어는 각 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합의 차이만큼을 포인트로 획득하게 됩니다.
				result[winnerIdx-1] += dif;
				
				// 2-2-2. 진 플레이어는 본인이 가지고 있는 총을 해당 격자에 내려놓고
				if(playerMap.get(loserIdx).gun > 0) {
					gunBoardList[nx][ny].add(playerMap.get(loserIdx).gun);
					playerMap.get(loserIdx).gun = 0;
				}
				remove_list(loserIdx, nx, ny, true); // 진 player의 기존 위치에서 정보 삭제
				
				move_loser(loserIdx,nx,ny);
				
				// 2-2-3. 이긴 플레이어는 승리한 칸에 떨어져 있는 총들과 원래 들고 있던 총 중 가장 공격력이 높은 총을 획득하고, 
				// 나머지 총들은 해당 격자에 내려 놓습니다.
				if(gunBoardList[nx][ny].size() > 0) {
					if(playerMap.get(winnerIdx).gun > 0) gunBoardList[nx][ny].add(playerMap.get(winnerIdx).gun);
					Collections.sort(gunBoardList[nx][ny]); // 내림차순 정렬
					int lastIdx = gunBoardList[nx][ny].size()-1;
					playerMap.get(winnerIdx).gun = gunBoardList[nx][ny].get(lastIdx);
					gunBoardList[nx][ny].remove(lastIdx);
				}
			}
		}
	}

	private static void move_loser(int idx, int x, int y) {
		// 해당 플레이어가 원래 가지고 있던 방향대로 한 칸 이동합니다.
		Player p = playerMap.get(idx);
		int d = p.d;
		
		// 만약 이동하려는 칸에 다른 플레이어가 있거나 격자 범위 밖인 경우에는 오른쪽으로 90도씩 회전하여 빈 칸이 보이는 순간 이동합니다. 
		if(!isValid(x+dx[d], y+dy[d]) || boardList[x+dx[d]][y+dy[d]].size()>0) {
			while(true) {
				if(d==3) d=0;
				else d++;
				
				if(isValid(p.x+dx[d], p.y+dy[d]) && boardList[p.x+dx[d]][p.y+dy[d]].size()==0) break;
			}
		}
		
		// 새 위치로 이동
		p.x += dx[d];
		p.y += dy[d];
		p.d = d;
		boardList[p.x][p.y].add(idx);
		
		// 만약 해당 칸에 총이 있다면,
		if(gunBoardList[p.x][p.y].size() > 0) {
			Collections.sort(gunBoardList[p.x][p.y]); // 내림차순 정렬
			int lastIdx = gunBoardList[p.x][p.y].size()-1;
			
			// 해당 플레이어는 가장 공격력이 높은 총을 획득하고 나머지 총들은 해당 격자에 내려 놓습니다.
			if(p.gun > 0) {
				if(p.gun < gunBoardList[p.x][p.y].get(lastIdx)) {
					gunBoardList[p.x][p.y].add(p.gun);
					p.gun = gunBoardList[p.x][p.y].get(lastIdx);
					gunBoardList[p.x][p.y].remove(lastIdx);
				}
			}
			else {
				p.gun = gunBoardList[p.x][p.y].get(lastIdx);
				gunBoardList[p.x][p.y].remove(lastIdx);
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
