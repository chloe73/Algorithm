package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_20056_마법사상어와파이어볼 {
	
	static int N,M,K,result,f_num;
	static Queue<Integer>[][] board;
	static HashMap<Integer, Fireball> fireballList;
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};
	static class Fireball {
		int r,c,m,s,d;
		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
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
		
		fireballList = new HashMap<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			board[r][c].add(i);
			fireballList.put(i,new Fireball(r, c, m, s, d));
		}
		f_num = M; // 앞으로 계속 추가될 fireball 인덱스를 위한 변수
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int cnt=0;cnt<K;cnt++) {
			
			// 1. 파이어볼 자신의 방향으로 속력만큼 이동
			move_fireball();
			
			// 2. 한 칸에 2개 이상의 파이어볼이 있는 칸
			check();
		}
		
		count_m(); // 남아있는 총 질량의 합 구하기
	}

	private static void check() {
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j].size() > 1) {
					int sum_m = 0; // 총 질량의 합
					int sum_s = 0; // 총 속력의 합
					int flag_d = -1; // 주어진 방향이 홀수인지 짝수인지 체크하기 위한 변수
					boolean flag = true; // 모두 홀수이거나 짝수인 경우 true
					int size = board[i][j].size(); // 파이어볼의 총 개수
					ArrayList<Integer> removeList = new ArrayList<>(); // 합쳐져서 삭제해야 할 fireball 인덱스 추가
					
					while(!board[i][j].isEmpty()) {
						int idx = board[i][j].poll();
						removeList.add(idx);
						
						int m = fireballList.get(idx).m;
						int s = fireballList.get(idx).s;
						int d = fireballList.get(idx).d;
						
						sum_m += m;
						sum_s += s;
						
						// 방향 홀수인지 짝수인지 체크
						if(d % 2 == 0) {
							if(flag_d == -1) flag_d = 0;
							else {
								if(flag_d != 0) flag = false;
							}
						} else {
							if(flag_d == -1) flag_d = 1;
							else {
								if(flag_d != 1) flag = false;
							}
						}
					}
					// 기존 파이어볼 삭제 작업
//					board[i][j].removeAll(removeList);
					for(int idx : removeList) {
						fireballList.remove(idx);
					}
					
					// 각 파이어볼 4개에 나눠서 들어갈 질량과 속력 값
					int item_m = sum_m / 5;
					int item_s = sum_s / size;
					
					// 질량이 0보다 크다면 파이어볼 생성
					if(item_m > 0) {
						int[] even = {0,2,4,6};
						int[] odd = {1,3,5,7};
						// 파이어볼 4개 생성
						for(int a=0;a<4;a++) {
							if(flag) {
								fireballList.put(f_num, new Fireball(i, j, item_m, item_s, even[a]));
								board[i][j].add(f_num);
								f_num++;
							}
							else {
								fireballList.put(f_num, new Fireball(i, j, item_m, item_s, odd[a]));
								board[i][j].add(f_num);
								f_num++;
							}
						}
						
					}
				}
				else continue;
			}
		}
	}

	private static void count_m() {
		
		for(int i : fireballList.keySet()) {
			result += fireballList.get(i).m;
		}
	}

	private static void move_fireball() {
		
		ArrayList<Integer> removeList = new ArrayList<>();
		
		for(int i : fireballList.keySet()) {
			removeList = new ArrayList<>();
			int r = fireballList.get(i).r; // 행 
			int c = fireballList.get(i).c; // 열 
			int s = fireballList.get(i).s; // 속력 
			int d = fireballList.get(i).d; // 방향 
			
			// 이동하기 전 위치 board에서 삭제
			removeList.add(i);
			board[r][c].removeAll(removeList);
			
			int nr = r;
			int nc = c;
			nr = (N + r + dx[d] * (s % N)) % N;
			nc = (N + c + dy[d] * (s % N)) % N;
//			nr = r + dx[d] * s;
//			nc = c + dy[d] * s;
//			
//			if(Math.abs(nr) > N) {
//				if(nr > 0) nr = Math.abs(nr) % N;
//				else {
//					nr = Math.abs(nr) % N;
//					nr = N - nr;;
//				}
//			}
//			else {
//				if(nr < 0) nr = N + nr;
//				else if(nr == N) nr = 0;
//			}
//			
//			if(Math.abs(nc) > N) {
//				if(nc > 0) nc = Math.abs(nc) % N;
//				else {
//					nc = Math.abs(nc) % N;
//					nc = N - nc;
//				}
//			}
//			else {
//				if(nc < 0) nc = N + nc;
//				else if(nc == N) nc = 0;
//			}
			
			// 속력만큼 칸 이동 계산 -> 시간초과 원인인 것 같음....
//			for(int t=0;t<s;t++) {
//				nr += dx[d];
//				nc += dy[d];
//				
//				if(nr<0 || nc<0 || nr>=N ||nc>=N) {
//					if(nr<0) {
//						nr = N + nr;
//					}
//					if(nc<0) {
//						nc = N + nc;
//					}
//					if(nr>=N) {
//						nr = N - nr;
//					}
//					if(nc>=N) {
//						nc = N - nc;
//					}
//				}
//				else continue;
//			}
			
			board[nr][nc].add(i);
			fireballList.get(i).r = nr;
			fireballList.get(i).c = nc;
		}
		
	}

}
