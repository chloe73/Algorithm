package algo.codetree1.secondHalf2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Main_ct_싸움땅 {
	
	static int N,M,K;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static int[][] pBoard;
	static PriorityQueue<Integer>[][] gBoard;
	static TreeMap<Integer, Player> playerMap;
	static class Player {
		int x,y,d,s;
		int gun,point;
		boolean isHaveGun;
		public Player(int x, int y, int d, int s) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.s = s;
			this.isHaveGun = false;
			this.gun = 0;
			this.point = 0;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		pBoard = new int[N][N];
		gBoard = new PriorityQueue[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				gBoard[i][j] = new PriorityQueue<>(Collections.reverseOrder());
				int num = Integer.parseInt(st.nextToken());
				if(num > 0) {
					gBoard[i][j].add(num);
				}
			}
		}
		
		playerMap = new TreeMap<>();
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			playerMap.put(i, new Player(x, y, d, s));
			pBoard[x][y] = i;
		} // input end
		
		solve();

		// k 라운드 동안 게임을 진행하면서 각 플레이어들이 획득한 포인트를 공백을 사이에 두고 출력하세요.
		StringBuilder sb = new StringBuilder();
		for(int i : playerMap.keySet()) {
			sb.append(playerMap.get(i).point+" ");
		}
		System.out.println(sb.toString());
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(pBoard[i][j]+"\t");
			}
			System.out.println();
		}
	}

	private static void solve() {
		
		while(K-- > 0) {
			for(int i : playerMap.keySet()) {
				// 1-1. 첫 번째 플레이어부터 순차적으로 본인이 향하고 있는 방향대로 한 칸만큼 이동합니다. 
				Player temp = playerMap.get(i);
				// 이동 전, 기존 칸 0으로 세팅
				pBoard[temp.x][temp.y] = 0;
				int d = temp.d;
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
				// 만약 해당 방향으로 나갈 때 격자를 벗어나는 경우에는 정반대 방향으로 방향을 바꾸어서 1만큼 이동합니다.
				if(!isValid(nx, ny)) {
					d = changeDir(d);
					nx = temp.x + dx[d];
					ny = temp.y + dy[d];
				}
				
				// 2-1. 만약 이동한 방향에 플레이어가 없다면 해당 칸에 총이 있는지 확인합니다. 
				if(pBoard[nx][ny] == 0) {
					pBoard[nx][ny] = i;
					temp.x = nx;
					temp.y = ny;
					temp.d = d;
					
					// 해당 칸에 총이 있는 경우, 해당 플레이어는 총을 획득합니다. 
					if(!gBoard[nx][ny].isEmpty()) {
						if(!temp.isHaveGun) {
							temp.isHaveGun = true;
							int gun = gBoard[nx][ny].poll();
							temp.gun = gun;
						}
						// 플레이어가 이미 총을 가지고 있는 경우에는 놓여있는 총들과 플레이어가 가지고 있는 총 가운데 공격력이 더 쎈 총을 획득하고, 나머지 총들은 해당 격자에 둡니다.
						else {
							// 현재 플레이어가 가지고 있는 총을 해당 칸에 내려두고 그 중에서 가장 공격력이 쎈 총을 획득한다.
							gBoard[nx][ny].add(temp.gun);
							temp.gun = gBoard[nx][ny].poll();
						}
					}
					
					continue;
				}
				
				// 2-2-1. 만약 이동한 방향에 플레이어가 있는 경우에는 두 플레이어가 싸우게 됩니다. 
				if(pBoard[nx][ny] > 0) {
					temp.x = nx;
					temp.y = ny;
					temp.d = d;
					
					Player target = playerMap.get(pBoard[nx][ny]);
					// 해당 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합을 비교하여 더 큰 플레이어가 이기게 됩니다.
					int winner = target.s + target.gun > temp.s + temp.gun ? pBoard[nx][ny] : i;
					int loser = target.s + target.gun < temp.s + temp.gun ? pBoard[nx][ny] : i;
					
					// 만일 이 수치가 같은 경우에는 플레이어의 초기 능력치가 높은 플레이어가 승리하게 됩니다. 
					if(target.s + target.gun == temp.s + temp.gun) {
						winner = target.s > temp.s ? pBoard[nx][ny] : i;
						loser = target.s < temp.s ? pBoard[nx][ny] : i;
					}
					
					// 이긴 플레이어는 각 플레이어의 초기 능력치와 가지고 있는 총의 공격력의 합의 차이만큼을 포인트로 획득하게 됩니다.
					int a = target.s+target.gun;
					int b = temp.s+temp.gun;
					playerMap.get(winner).point += (Math.abs(a-b));
					
					// 2-2-2. 진 플레이어는 본인이 가지고 있는 총을 해당 격자에 내려놓고, 
					if(playerMap.get(loser).isHaveGun) {
						playerMap.get(loser).isHaveGun = false;
						gBoard[nx][ny].add(playerMap.get(loser).gun);
						playerMap.get(loser).gun = 0;
					}
					
					// 진 플레이어가 원래 가지고 있던 방향대로 한 칸 이동합니다. 
					int nnx = nx + dx[playerMap.get(loser).d];
					int nny = ny + dy[playerMap.get(loser).d];
					
					// 만약 이동하려는 칸에 다른 플레이어가 있거나 격자 범위 밖인 경우에는 오른쪽으로 90도씩 회전하여 빈 칸이 보이는 순간 이동합니다. 
					while(!isValid(nnx, nny) || pBoard[nnx][nny] > 0) {
						if(playerMap.get(loser).d == 3) {
							playerMap.get(loser).d = 0;
						}
						else {
							playerMap.get(loser).d++;
						}

						nnx = nx + dx[playerMap.get(loser).d];
						nny = ny + dy[playerMap.get(loser).d];
					}
					
					playerMap.get(loser).x = nnx;
					playerMap.get(loser).y = nny;
					pBoard[nnx][nny] = loser;
					
					// 만약 해당 칸에 총이 있다면, 해당 플레이어는 가장 공격력이 높은 총을 획득하고 나머지 총들은 해당 격자에 내려 놓습니다.
					if(gBoard[nnx][nny].size() > 0) {
						playerMap.get(loser).gun = gBoard[nnx][nny].poll();
						playerMap.get(loser).isHaveGun = true;
					}
					
					// 2-2-3. 이긴 플레이어는 승리한 칸에 떨어져 있는 총들과 원래 들고 있던 총 중 가장 공격력이 높은 총을 획득하고, 
					// 나머지 총들은 해당 격자에 내려 놓습니다.
					pBoard[nx][ny] = winner;
					playerMap.get(winner).x = nx;
					playerMap.get(winner).y = ny;
					if(playerMap.get(winner).isHaveGun) {
						gBoard[nx][ny].add(playerMap.get(winner).gun);
					}
					if(gBoard[nx][ny].size() > 0) {
						playerMap.get(winner).isHaveGun = true;
						playerMap.get(winner).gun = gBoard[nx][ny].poll();						
					}
				}
			}
			
//			print();
//			System.out.println(" ================= ");
		}
	}
	
	private static int changeDir(int d) {
		if(d == 0) return 2;
		if(d == 2) return 0;
		if(d == 1) return 3;
		return 1;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
