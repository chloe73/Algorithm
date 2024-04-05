package algo.codetree1.firstHalf2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_술래잡기 {
	
	static int N,M,H,K,result;
	static int tIdx,tx,ty,td; // 술래 위치
	static boolean tDir;
	static boolean[][] tree;
	static Queue<Integer>[][] board;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static ArrayList<int[]> route;
	static HashMap<Integer, Runner> rMap;
	static class Runner {
		int x,y,d;
		public Runner(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new LinkedList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				board[i][j] = new LinkedList<>();
			}
		}
		tree = new boolean[N][N];
		rMap = new HashMap<>();
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			
			rMap.put(i, new Runner(x, y, d));
			board[x][y].add(i);
		}
		
		for(int i=0;i<H;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			tree[x][y] = true;
		} // input end
		
		tx = N / 2;
		ty = N / 2;
		
		tIdx = 0;
		tDir = false;
		route = new ArrayList<>();
		makeRoute();
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		int turn = 0;
		
		while(K-- > 0) {
			turn++;
			System.out.println(" ===== turn: "+turn+" =====");
			// 1. 도망자 이동
			moveRunner();
			
			// 2. 술래 이동
			moveTagger(turn);
			System.out.println("tagger : "+tx+","+ty);
		}
		
	}
	
	private static void moveTagger(int turn) {
		// 술래는 1번의 턴 동안 정확히 한 칸 해당하는 방향으로 이동하게 됩니다.
		
		// 이동 후의 위치가 만약 이동방향이 틀어지는 지점이라면, 방향을 바로 틀어줍니다. 
		// 만약 이동을 통해 양끝에 해당하는 위치인 (1행, 1열) 혹은 정중앙에 도달하게 된다면 이 경우 역시 방향을 바로 틀어줘야 함에 유의합니다.
		if(tDir) {
			tIdx--;	
		}
		else {
			tIdx++;
		}
		
		int[] temp = route.get(tIdx);
		tx = temp[0];
		ty = temp[1];
		td = temp[2];
		if(tDir) {
			td = temp[3];
		}
		
		if(tx == 0 && ty == 0) {
			tDir = true;
		}
		else if(tx == N/2 && ty == N/2) {
			tDir = false;
		}
		
		// 이동 직후 술래는 턴을 넘기기 전에 시야 내에 있는 도망자를 잡게 됩니다. 
		// 술래의 시야는 현재 바라보고 있는 방향을 기준으로 현재 칸을 포함하여 총 3칸입니다. 
		// 격자 크기에 상관없이 술래의 시야는 항상 3칸임에 유의합니다.
		int runner = 0;
		for(int cnt=0;cnt<3;cnt++) {
			int nx = tx + dx[td]*cnt;
			int ny = ty + dy[td]*cnt;
			
			if(!isValid(nx, ny)) continue;
			
			// 하지만 만약 나무가 놓여 있는 칸이라면, 해당 칸에 있는 도망자는 나무에 가려져 보이지 않게 됩니다.
			if(tree[nx][ny]) continue;
			
			if(board[nx][ny].size() > 0) {
				runner += board[nx][ny].size();
				while(!board[nx][ny].isEmpty()) {
					int idx = board[nx][ny].poll();
					rMap.remove(idx);
				}
			}
		}
		
		if(runner > 0) {
			result += (turn * runner);
		}
	}
	
	private static void makeRoute() {
		
		int x = N/2;
		int y = N/2;
		int d = 0;
		int cnt = 1;
		int num = 0;
		route.add(new int[] {x,y,d});
		
		outer:while(!(x == 0 && y == 0)) {
			
			for(int i=0;i<cnt;i++) {
				x += dx[d];
				y += dy[d];
				
				if(x == 0 && y == 0) {
					route.add(new int[] {0,0,2,2});
					break outer;
				}
				
				if(i == cnt-1) {
					if(d == 3) {
						route.add(new int[] {x,y,0,1});
					}
					else {
						
						route.add(new int[] {x,y,d+1,d+1 == 3 ? 0 : d+2});
					}
					continue;
				}
				else {
					int dir = changeDir(d);
					route.add(new int[] {x,y,d,dir});					
				}
			}
			
			if(d == 3) {
				d = 0;
			}
			else {
				d++;
			}
			
			num++;
			if(num % 2 == 0) {
				cnt++;
			}
			
		}
		
	}

	private static void moveRunner() {
		
		for(int i : rMap.keySet()) {
			Runner temp = rMap.get(i);
			
			int distance = getDist(temp.x, temp.y, tx, ty);
			
			// 도망자가 움직일 때 현재 술래와의 거리가 3 이하인 도망자만 움직입니다.
			if(distance > 3) continue;
			
			// 현재 바라보고 있는 방향으로 1칸 움직인다 했을 때 격자를 벗어나지 않는 경우
			int nx = temp.x + dx[temp.d];
			int ny = temp.y + dy[temp.d];
			
			// 현재 바라보고 있는 방향으로 1칸 움직인다 했을 때 격자를 벗어나는 경우
			if(!isValid(nx, ny)) {
				// 먼저 방향을 반대로 틀어줍니다. 이후 바라보고 있는 방향으로 1칸 움직인다 했을 때 해당 위치에 술래가 없다면 1칸 앞으로 이동합니다.
				temp.d = changeDir(temp.d);
				nx = temp.x + dx[temp.d];
				ny = temp.y + dy[temp.d];
			}
			
			// 움직이려는 칸에 술래가 있는 경우라면 움직이지 않습니다.
			if(nx == tx && ny == ty) continue;
			
			// 움직이려는 칸에 술래가 있지 않다면 해당 칸으로 이동합니다. 해당 칸에 나무가 있어도 괜찮습니다.
			board[temp.x][temp.y].remove(i);
			board[nx][ny].add(i);
			temp.x = nx;
			temp.y = ny;
			System.out.println("runner : "+nx+","+ny);
		}
	}

	private static int changeDir(int d) {
		if(d == 0) return 2;
		else if(d == 1) return 3;
		else if(d == 2) return 0;
		return 1;
	}
	
	private static int getDist(int ax, int ay, int bx, int by) {
		return Math.abs(ax-bx) + Math.abs(ay-by);
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>= N) return false;
		return true;
	}

}
