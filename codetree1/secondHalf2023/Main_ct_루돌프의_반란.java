package algo.codetree1.secondHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_ct_루돌프의_반란 {
	
//	C: 루돌프의 힘 (1≤C≤N)
//	D: 산타의 힘 (1≤D≤N)
	static int N,M,P,C,D;
	static int rx,ry; // 루돌프 위치
	static int[] result; // 게임이 끝났을 때 각 산타가 얻은 최종 점수
	static int[][] sBoard;
	static TreeMap<Integer, Santa> santaMap;
	static class Santa{
		int x,y;
		boolean isFaint, isFail;
		int faintTime;
		public Santa(int x, int y) {
			this.x = x;
			this.y = y;
			faintTime = 0;
			isFaint = false;
			isFail = false;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		sBoard = new int[N][N];

		st = new StringTokenizer(br.readLine());
		rx = Integer.parseInt(st.nextToken())-1;
		ry = Integer.parseInt(st.nextToken())-1;
		
		result = new int[P+1];
		santaMap = new TreeMap<>();
		for(int i=0;i<P;i++) {
			st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			
			santaMap.put(num, new Santa(x, y));
			sBoard[x][y] = num;
		} // input end
		
		solve();
		
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<=P;i++) {
			sb.append(result[i]+" ");
		}
		System.out.println(sb.toString());
	}

	private static void solve() {
//		M 번의 턴에 걸쳐 루돌프, 산타가 순서대로 움직인 이후 게임이 종료됩니다.
		while(M-- >0) {
//			만약 P 명의 산타가 모두 게임에서 탈락하게 된다면 그 즉시 게임이 종료됩니다.
			boolean flag = false;
			for(int i : santaMap.keySet()) {
				if(!santaMap.get(i).isFail) {
					flag = true;
					break;
				}
			}
			
			if(!flag) break;
			
			// 1. 루돌프 이동
			move_r();
			
			// 2. 산타 이동 (번호 순서대로)
			move_santa();
			
			// 기절한 산타 시간 체크 & 턴 종료 후, 살아남은 산타 점수 1점씩 획득
			for(int i : santaMap.keySet()) {
				Santa temp = santaMap.get(i);
				
				if(temp.isFail) continue;
				
				if(temp.isFaint && temp.faintTime > 0) {
					temp.faintTime--;
					if(temp.faintTime == 0) {
						// 다시 다음 턴부터 정상상태
						temp.isFaint = false;
					}
				}
				
//				매 턴 이후 아직 탈락하지 않은 산타들에게는 1점씩을 추가로 부여합니다.
				if(!temp.isFail) {
					result[i]++;
				}
			}
		}
	}

	private static void move_santa() {
		// 상 우 하 좌
		int[] dx = {-1,0,1,0};
		int[] dy = {0,1,0,-1};

//		산타는 1번부터 P번까지 순서대로 움직입니다.
		for(int i : santaMap.keySet()) {
			Santa temp = santaMap.get(i);
//			기절했거나 이미 게임에서 탈락한 산타는 움직일 수 없습니다.
			if(temp.isFaint) continue;
			if(temp.isFail) continue;
			
			// 현재 산타 위치에서 루돌프까지의 거리
			int tempDist = getDist(temp.x, temp.y, rx, ry);
			
			// {dist,x,y,dir}	
			PriorityQueue<int[]> pq = new PriorityQueue<>( (o1,o2) -> {
				if(o1[0] == o2[0])
					return o1[3]-o2[3]; // 방향 우선순위
				return o1[0]-o2[0]; // 가장 가까워지는 거리로
			});
			
//			산타는 상하좌우로 인접한 4방향 중 한 곳으로 움직일 수 있습니다. 
//			이때 가장 가까워질 수 있는 방향이 여러 개라면, 상우하좌 우선순위에 맞춰 움직입니다.
			for(int d=0;d<4;d++) {
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
//				산타는 다른 산타가 있는 칸이나 게임판 밖으로는 움직일 수 없습니다.
				if(!isValid(nx, ny) || sBoard[nx][ny] > 0) continue;
				
				int dist = getDist(nx, ny, rx, ry);
				
				pq.add(new int[] {dist,nx,ny,d});
			}
			
//			산타는 루돌프에게 거리가 가장 가까워지는 방향으로 1칸 이동합니다.
//			움직일 수 있는 칸이 없다면 산타는 움직이지 않습니다.
			if(pq.isEmpty()) continue;
			
			int[] target = pq.poll();
			int dist = target[0];
			int dir = target[3];

//			움직일 수 있는 칸이 있더라도 만약 루돌프로부터 가까워질 수 있는 방법이 없다면 산타는 움직이지 않습니다.
			if(dist >= tempDist) continue;
			
//			산타가 루돌프와 가까워지는 좌표로 이동
			int nx = target[1];
			int ny = target[2];
			
//			산타와 루돌프가 충돌하는 경우
			if(nx == rx && ny == ry) {
//				산타가 움직여서 충돌이 일어난 경우, 해당 산타는 D만큼의 점수를 얻게 됩니다.
				result[i] += D;
				sBoard[temp.x][temp.y] = 0;
//				이와 동시에 산타는 자신이 이동해온 반대 방향으로 D 칸 만큼 밀려나게 됩니다.
				dir = changeDir(dir);
				int nnx = nx + dx[dir] * D;
				int nny = ny + dy[dir] * D;
				
//				만약 밀려난 위치가 게임판 밖이라면 산타는 게임에서 탈락됩니다.
				if(!isValid(nnx, nny)) {
					temp.isFail = true;
					continue;
				}
				
//				만약 밀려난 칸에 다른 산타가 있는 경우 상호작용이 발생합니다.
				if(sBoard[nnx][nny] > 0) {
					santaMap.get(i).isFaint = true;
					santaMap.get(i).faintTime = 2;
					int num = i;
					while(isValid(nnx, nny)) {
						if(sBoard[nnx][nny] > 0) {
							int tmp = sBoard[nnx][nny];
							sBoard[nnx][nny] = num;
							santaMap.get(num).x = nnx;
							santaMap.get(num).y = nny;
							num = tmp;							
						}
						else {
							sBoard[nnx][nny] = num;
							santaMap.get(num).x = nnx;
							santaMap.get(num).y = nny;
							break;
						}
						nnx += dx[dir];
						nny += dy[dir];
						if(!isValid(nnx, nny) && num > 0) {
							santaMap.get(num).isFail = true;
							break;
						}
					}
					continue;
				}
				
				santaMap.get(i).x = nnx;
				santaMap.get(i).y = nny;
				santaMap.get(i).isFaint = true;
				santaMap.get(i).faintTime = 2;
				sBoard[nnx][nny] = i;
				continue;
			}
			
			sBoard[temp.x][temp.y] = 0;
			santaMap.get(i).x = target[1];
			santaMap.get(i).y = target[2];
			sBoard[temp.x][temp.y] = i;
		}
	}

	private static void move_r() {
		int[] dx = {-1,1,0,0,-1,-1,1,1};
		int[] dy = {0,0,-1,1,-1,1,-1,1};

		int minDist = Integer.MAX_VALUE;
		// 가장 가까운 산타 x,y 좌표
		int tx = -1;
		int ty = -1;
		
//		루돌프는 가장 가까운 산타를 향해 1칸 돌진합니다. 
		for(int i : santaMap.keySet()) {
			Santa temp = santaMap.get(i);
			
//			단, 게임에서 탈락하지 않은 산타 중 가장 가까운 산타를 선택해야 합니다.
			if(temp.isFail) continue;
//			루돌프는 기절한 산타를 돌진 대상으로 선택할 수 있습니다.
//			if(temp.isFaint) continue;
			
			int dist = getDist(rx, ry, temp.x, temp.y);
			
			if(minDist > dist) {
				minDist = dist;
				tx = temp.x;
				ty = temp.y;
				continue;
			}
//			만약 가장 가까운 산타가 2명 이상이라면, r 좌표가 큰 산타를 향해 돌진합니다. 
			else if(minDist == dist) {
				if(temp.x > tx) {
					tx = temp.x;
					ty = temp.y;
					continue;
				}
//				r이 동일한 경우, c 좌표가 큰 산타를 향해 돌진합니다.
				if(temp.x == tx && temp.y > ty) {
					ty = temp.y;
					continue;
				}
			}
		}
		
//		루돌프는 상하좌우, 대각선을 포함한 인접한 8방향 중 하나로 돌진할 수 있습니다. (편의상 인접한 대각선 방향으로 전진하는 것도 1칸 전진하는 것이라 생각합니다.) 
//		가장 우선순위가 높은 산타를 향해 8방향 중 가장 가까워지는 방향으로 한 칸 돌진합니다.
		minDist = Integer.MAX_VALUE;
		int dir = -1;
		int rx2 = -1;
		int ry2 = -1;
		for(int d=0;d<8;d++) {
			int nx = rx + dx[d];
			int ny = ry + dy[d];
			
			if(!isValid(nx, ny)) continue;
			
			int dist = getDist(nx, ny, tx, ty);
			
			if(minDist > dist) {
				minDist = dist;
				rx2 = nx;
				ry2 = ny;
				dir = d;
			}
		}
		
		// 루돌프 새 좌표로 이동
		rx = rx2;
		ry = ry2;
		
//		루돌프가 움직여서 충돌이 일어난 경우, 해당 산타는 C만큼의 점수를 얻게 됩니다. 
		if(tx == rx2 && ty == ry2) {
			int num = sBoard[tx][ty]; // 충돌한 산타 번호
			result[num] += C;
			sBoard[tx][ty] = 0;
			
//			이와 동시에 산타는 루돌프가 이동해온 방향으로 C 칸 만큼 밀려나게 됩니다.
			int nx = tx + dx[dir] * C;
			int ny = ty + dy[dir] * C;
			
//			만약 밀려난 위치가 게임판 밖이라면 산타는 게임에서 탈락됩니다.
			if(!isValid(nx, ny)) {
				santaMap.get(num).isFail = true;
				return;
			}
			
//			만약 밀려난 칸에 다른 산타가 있는 경우 상호작용이 발생합니다.
			if(sBoard[nx][ny] > 0) {
//				산타는 충돌 후 착지하게 되는 칸에 다른 산타가 있다면 그 산타는 1칸 해당 방향으로 밀려나게 됩니다. 
//				그 옆에 산타가 있다면 연쇄적으로 1칸씩 밀려나는 것을 반복하게 됩니다. 게임판 밖으로 밀려나오게 된 산타의 경우 게임에서 탈락됩니다.
				int number = num; // 현재 산타 번호
				santaMap.get(num).isFaint = true;
				santaMap.get(num).faintTime = 2;
				while(isValid(nx, ny)) {
					if(sBoard[nx][ny] > 0) {
						int tmp = sBoard[nx][ny];
						sBoard[nx][ny] = number;
						santaMap.get(number).x = nx;
						santaMap.get(number).y = ny;
						number = tmp;						
					}
					else {
						sBoard[nx][ny] = number;
						santaMap.get(number).x = nx;
						santaMap.get(number).y = ny;
						break;
					}
					nx += dx[dir];
					ny += dy[dir];
					if(!isValid(nx, ny) && number > 0) {
						santaMap.get(number).isFail = true;
						break;
					}
					
				}
				return;
			}
			
			// 충돌된 산타는 밀려나고 기절하게 된다.
			santaMap.get(num).x = nx;
			santaMap.get(num).y = ny;
			santaMap.get(num).isFaint = true;
			santaMap.get(num).faintTime = 2;
			sBoard[nx][ny] = num;
		}
		
		return;
	}
	
	private static int changeDir(int d) {
		if(d == 0) return 2;
		if(d == 1) return 3;
		if(d == 2) return 0;
		return 1;
	}
	
	private static int getDist(int a, int b, int c, int d) {
		return Math.abs(a-c)*Math.abs(a-c) + Math.abs(b-d)* Math.abs(b-d);
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
