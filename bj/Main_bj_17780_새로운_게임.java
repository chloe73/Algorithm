package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_17780_새로운_게임 {
	
	static int N,K,result;
	static int[][] board;
	static Queue<Horse>[][] q;
	static ArrayList<Horse> hList;
	// 					→, ←, ↑, ↓
	static int[] dx = {0,0,-1,1};
	static int[] dy = {1,-1,0,0};
	static class Horse {
		int num;
		int x,y,d;
		public Horse(int num, int x, int y, int d) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		q = new LinkedList[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				q[i][j] = new LinkedList<>();
			}
		}
		
		hList = new ArrayList<>();
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			q[r][c].add(new Horse(i, r, c, d));
			hList.add(new Horse(i, r, c, d));
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		int turn = 0;
		
		outer:while(turn++ < 1000) {
			// 1번 말부터 순서대로 이동한다.
			// 0은 흰색, 1은 빨간색, 2는 파란색이다.
			// 턴 한 번은 1번 말부터 K번 말까지 순서대로 이동시키는 것이다. 
			// 한 말이 이동할 때 위에 올려져 있는 말도 함께 이동하며, 가장 아래에 있는 말만 이동할 수 있다. 
			// 말의 이동 방향에 있는 칸에 따라서 말의 이동이 다르며 아래와 같다. 
			
			for(int i=0;i<hList.size();i++) {
				Horse temp = hList.get(i);
				
				// 가장 아래에 있는 말만 이동할 수 있다.
				if(q[temp.x][temp.y].peek().num != temp.num) continue;
				
				int nx = temp.x + dx[temp.d];
				int ny = temp.y + dy[temp.d];
				int nd = temp.d;

				// 현재 말이 이동하려는 칸이 파란색인 경우 or 체스판을 벗어난 경우
				if(!is_valid(nx, ny) || board[nx][ny] == 2) {
					// 파란색인 경우에는 A번 말의 이동 방향을 반대로 하고 한 칸 이동한다.
					// 체스판을 벗어나는 경우에는 파란색과 같은 경우이다.
					nd = change_dir(nd);
					nx = temp.x + dx[nd];
					ny = temp.y + dy[nd];
					
					// 방향을 반대로 한 후에 이동하려는 칸이 파란색인 경우에는 이동하지 않고 방향만 반대로 바꾼다.
					if(!is_valid(nx, ny) || board[nx][ny] == 2) {
						hList.get(temp.num).d = nd;
						continue;
					}
					
					if(board[nx][ny] == 0) {
						is_white(temp.x, temp.y, nx, ny);
						hList.get(temp.num).d = nd;
						
						// 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.
						if(q[nx][ny].size() >= 4) break outer;
						
						continue;
					}
					
					if(board[nx][ny] == 1) {
						is_red(temp.x, temp.y, nx, ny);
						hList.get(temp.num).d = nd;
						
						// 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.
						if(q[nx][ny].size() >= 4) break outer;
						
						continue;
					}
				}
				
				// 현재 말이 이동하려는 칸이 흰색인 경우
				if(board[nx][ny] == 0) {
					// 흰색인 경우에는 그 칸으로 이동한다. 이동하려는 칸에 말이 이미 있는 경우에는 가장 위에 A번 말을 올려놓는다.
					// A번 말의 위에 다른 말이 있는 경우에는 A번 말과 위에 있는 모든 말이 이동한다.
					// 예를 들어, A, B, C로 쌓여있고, 이동하려는 칸에 D, E가 있는 경우에는 A번 말이 이동한 후에는 D, E, A, B, C가 된다.
					is_white(temp.x, temp.y, nx, ny);
					
					// 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.
					if(q[nx][ny].size() >= 4) break outer;
				}
				
				// 현재 말이  이동하려는 칸이 빨간색인 경우
				if(board[nx][ny] == 1) {
					// 빨간색인 경우에는 이동한 후에 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서를 반대로 바꾼다.
					// A, B, C가 이동하고, 이동하려는 칸에 말이 없는 경우에는 C, B, A가 된다.
					// A, D, F, G가 이동하고, 이동하려는 칸에 말이 E, C, B로 있는 경우에는 E, C, B, G, F, D, A가 된다.
					is_red(temp.x, temp.y, nx, ny);
					
					// 턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.
					if(q[nx][ny].size() >= 4) break outer;
				}
				
			}

		}

		if(turn >= 1000) result = -1;
		else result = turn;
	}
	
	private static void is_white(int x, int y, int nx, int ny) {
		// 흰색인 경우에는 그 칸으로 이동한다. 이동하려는 칸에 말이 이미 있는 경우에는 가장 위에 A번 말을 올려놓는다.
		// A번 말의 위에 다른 말이 있는 경우에는 A번 말과 위에 있는 모든 말이 이동한다.
		// 예를 들어, A, B, C로 쌓여있고, 이동하려는 칸에 D, E가 있는 경우에는 A번 말이 이동한 후에는 D, E, A, B, C가 된다.
		while(!q[x][y].isEmpty()) {
			Horse h = q[x][y].poll();
			q[nx][ny].add(new Horse(h.num, h.x, h.y, h.d));
			hList.get(h.num).x = nx;
			hList.get(h.num).y = ny;
		}
	}
	
	private static void is_red(int x, int y, int nx, int ny) {
		// 빨간색인 경우에는 이동한 후에 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서를 반대로 바꾼다.
		// A, B, C가 이동하고, 이동하려는 칸에 말이 없는 경우에는 C, B, A가 된다.
		// A, D, F, G가 이동하고, 이동하려는 칸에 말이 E, C, B로 있는 경우에는 E, C, B, G, F, D, A가 된다.
		Stack<Horse> stack = new Stack<>();
		while(!q[x][y].isEmpty()) {
			Horse h = q[x][y].poll();
			stack.add(new Horse(h.num, h.x, h.y, h.d));
			hList.get(h.num).x = nx;
			hList.get(h.num).y = ny;
		}
		
		while(!stack.isEmpty()) {
			Horse h = stack.pop();
			q[nx][ny].add(new Horse(h.num, h.x, h.y, h.d));
		}
	}
	
	private static int change_dir(int d) {
		if(d == 0) return 1;
		if(d == 1) return 0;
		if(d == 2) return 3;
		return 2;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}