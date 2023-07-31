package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_원자_충돌 {
	
	static int N,M,K,result;
	// 					↑, ↗, →, ↘, ↓, ↙, ←, ↖
	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	static ArrayList<Atom> atomList;
	static Queue<Atom>[][] board;
	static class Atom {
		int x,y,d,s,m;
		public Atom(int x, int y, int m, int s, int d) {
			this.x = x;
			this.y = y;
			this.m = m; // 질량
			this.s = s; // 속력
			this.d = d; // 방향
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new LinkedList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				board[i][j] = new LinkedList<>();
			}
		}
		
		atomList = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			atomList.add(new Atom(x, y, m, s, d));
			board[x][y].add(new Atom(x, y, m, s, d));
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		int turn = 0;
		while(turn++ < K) {
			// 모든 원자는 1초가 지날 때마다 자신의 방향으로 자신의 속력만큼 이동합니다.
			move_atom();

			// 이동이 모두 끝난 뒤에 하나의 칸에 2개 이상의 원자가 있는 경우에는 다음과 같은 합성이 일어납니다.
			for(int x=0;x<N;x++) {
				for(int y=0;y<N;y++) {
					
					if(board[x][y].size() < 2) continue;
					
					// a. 같은 칸에 있는 원자들은 각각의 질량과 속력을 모두 합한 하나의 원자로 합쳐집니다.
					int cnt = board[x][y].size();
					int sum_m = 0;
					int sum_s = 0;
					int sum_d = 0;
					
					// b. 이후 합쳐진 원자는 4개의 원자로 나눠집니다.
					while(!board[x][y].isEmpty()) {
						Atom temp = board[x][y].poll();
						
						sum_m += temp.m;
						sum_s += temp.s;
						
						// 짝 + 짝 = 짝
						// 홀 + 홀 = 짝
						// 짝 + 홀 = 홀
						// 홀 + 짝 = 홀
						sum_d += temp.d;
					}
					
					// 질량은 합쳐진 원자의 질량에 5를 나눈 값입니다.
					int mm = sum_m / 5;
					
					// 속력은 합쳐진 원자의 속력에 합쳐진 원자의 개수를 나눈 값입니다.
					int ss = sum_s / cnt;
					
					// 방향은 합쳐지는 원자의 방향이 모두 상하좌우 중 하나이거나 대각선 중에 하나이면, 각각 상하좌우의 방향을 가지며 그렇지 않다면 대각선 네 방향의 값을 가집니다.
					int[] dir = new int[4];
					if(sum_d % 2 == 0) {
						dir[0] = 0;
						dir[1] = 2;
						dir[2] = 4;
						dir[3] = 6;
					}
					else {
						dir[0] = 1;
						dir[1] = 3;
						dir[2] = 5;
						dir[3] = 7;
					}
					
					// d. 질량이 0인 원소는 소멸됩니다.
					if(mm <= 0) continue;
					
					// c. 나누어진 원자들은 모두 해당 칸에 위치하고 질량과 속력, 방향은 다음 기준을 따라 결정됩니다.
					for(int i=0;i<4;i++) {
						board[x][y].add(new Atom(x, y, mm, ss, dir[i]));
					}
					
					
				}
			}
			
			update_atom();

		}
		
		// k초가 될 때, 남아있는 원자의 질량 합
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				while(!board[i][j].isEmpty()) {
					result += board[i][j].poll().m;
				}
			}
		}
	}

	private static void update_atom() {
		
		atomList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j].size() > 0) {
					int size = board[i][j].size();
					while(size-- > 0) {
						Atom a = board[i][j].poll();
						atomList.add(new Atom(a.x, a.y, a.m, a.s, a.d));
						board[i][j].add(a);
					}
				}
			}
		}
	}

	private static void move_atom() {
		
		for(Atom a : atomList) {
			board[a.x][a.y].poll();
			
			int nx = (N + a.x + dx[a.d] * (a.s % N)) % N;
			int ny = (N + a.y + dy[a.d] * (a.s % N)) % N;
			
			board[nx][ny].add(new Atom(nx, ny, a.m, a.s, a.d));
			
			a.x = nx;
			a.y = ny;
		}
	}

}
