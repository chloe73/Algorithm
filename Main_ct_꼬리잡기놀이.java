package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_꼬리잡기놀이 {
	
	static int N,M,K,result;
	static int ballDir, ballIdx;
	static int[][] board, teamNum;
	// 					>, ^, <, v
	static int[] dx = {0,0,-1,0,1};
	static int[] dy = {0,1,0,-1,0};
	static Queue<int[]> q;
	static boolean[][] visited;
	static ArrayList<Point>[] teamList;
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		teamNum = new int[N][N]; // 팀 번호 적혀져 있음
		visited = new boolean[N][N];
		q = new LinkedList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) q.add(new int[] {i,j});
			}
		} // input end
		
		teamList = new ArrayList[q.size()+1];
		for(int i=0;i<=q.size();i++) {
			teamList[i] = new ArrayList<>();
		}
		int idx = 1; // 1번부터 시작 !!!!!!!
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			dfs(x,y,1,idx);
			idx++;
		}
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		ballDir = 0;
		ballIdx = 0;
		int turn = 0;
		while(true) {
			if(turn == K) break;
			for(int d=1;d<=4;d++) {
				ballDir = d;
				if(d == 1 || d == 2) {
					for(int i=0;i<N;i++) {
						if(turn == K) return;
						ballIdx = i;
						System.out.println("===== "+turn+" =====");
						move();
						System.out.println("move() 실행 후 : ");
						print();
						move_ball();
						System.out.println("move_ball() 실행 후 : ");
						print();
						turn++;
					}
					
				}
				
				else {
					for(int i=N-1;i>=0;i--) {
						if(turn == K) return;
						ballIdx = i;
						System.out.println("===== "+turn+" =====");
						move();
						System.out.println("move() 실행 후 : ");
						print();
						move_ball();
						System.out.println("move_ball() 실행 후 : ");
						print();
						turn++;
					}
				}
			}
		}
		
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(board[i][j]+"\t");
			}
			System.out.println();
		}
	}

	private static void move_ball() {
		// 점수는 해당 사람이 머리사람을 시작으로 팀 내에서 k번째 사람이라면 k의 제곱만큼 점수를 얻게 됩니다. 
		// 아무도 공을 받지 못하는 경우에는 아무 점수도 획득하지 못합니다. 위의 예시에서 1라운드는 다음과 같이 진행됩니다.
		int idx = 0; // 공을 획득한 팀
		int count = 0; // 공을 획득한 팀 내 최초로 공을 맞은 사람의 순서
		if(ballDir == 1) { // ballIdx = 행
			for(int j=0;j<N;j++) {
				if(board[ballIdx][j] > 0 && board[ballIdx][j] < 4) {
					idx = teamNum[ballIdx][j]; // 몇번째 팀인가
					for(int cnt=0;cnt<teamList[idx].size();cnt++) {
						Point p = teamList[idx].get(cnt);
						if(p.x == ballIdx && p.y == j) {
							count = cnt+1;
							break;
						}
					}
					break;
				}
			}
		}
		else if(ballDir == 3) {
			for(int j=N-1;j>=0;j--) {
				if(board[ballIdx][j] > 0 && board[ballIdx][j] < 4) {
					idx = teamNum[ballIdx][j]; // 몇번째 팀인가
					for(int cnt=0;cnt<teamList[idx].size();cnt++) {
						Point p = teamList[idx].get(cnt);
						if(p.x == ballIdx && p.y == j) {
							count = cnt+1;
							break;
						}
					}
					break;
				}
			}
		}
		else if(ballDir == 4) { // ballIdx = 열
			for(int i=0;i<N;i++) {
				if(board[i][ballIdx] > 0 && board[i][ballIdx] < 4) {
					idx = teamNum[i][ballIdx];
					for(int cnt=0;cnt<teamList[idx].size();cnt++) {
						Point p = teamList[idx].get(cnt);
						if(p.x == i && p.y == ballIdx) {
							count = cnt+1;
							break;
						}
					}
					break;
				}
			}
		}
		else {
			for(int i=N-1;i>=0;i--) {
				if(board[i][ballIdx] > 0 && board[i][ballIdx] < 4) {
					idx = teamNum[i][ballIdx];
					for(int cnt=0;cnt<teamList[idx].size();cnt++) {
						Point p = teamList[idx].get(cnt);
						if(p.x == i && p.y == ballIdx) {
							count = cnt+1;
							break;
						}
					}
					break;
				}
			}
		}
		if(count > 0) result += count*count;
		System.out.println("result :" +result);
		// 공을 획득한 팀의 경우에는 머리사람과 꼬리사람이 바뀝니다. 즉 방향을 바꾸게 됩니다.
		if(idx > 0) {
			ArrayList<Point> temp = new ArrayList<>();
			for(int i=teamList[idx].size()-1;i>=0;i--) {
				Point p = teamList[idx].get(i);
				board[p.x][p.y] = 0;
				temp.add(p);
			}
			teamList[idx].clear();
			teamList[idx] = temp;
			
			for(int i=0;i<teamList[idx].size();i++) {
				Point p = teamList[idx].get(i);
				if(i == 0) board[p.x][p.y] = 1;
				else if(i == teamList[idx].size()-1)
					board[p.x][p.y] = 3;
				else board[p.x][p.y] = 2;
			}
		}
	}

	private static void move() {
		// 1. 먼저 각 팀은 머리사람을 따라서 한 칸 이동합니다.
		int[][] copy = new int[N][N];
		
		for(int i=1;i<teamList.length;i++) {
			
			int beforeX = 0;
			int beforeY = 0;
			for(int idx=0;idx<teamList[i].size();idx++) {
				Point temp = teamList[i].get(idx);
				int num = board[temp.x][temp.y];
				int x = temp.x;
				int y = temp.y;
				if(idx==0) {
					for(int d=1;d<=4;d++) {
						int nx = x + dx[d];
						int ny = y + dy[d];
						
						if(!isValid(nx, ny)) continue;
						
						if(num == 1 && board[nx][ny] == 4 && teamNum[nx][ny] == i) {
							beforeX = x;
							beforeY = y;
							temp.x = nx;
							temp.y = ny;
							copy[nx][ny] = 1;
							break;
						}
						else if(num == 1 && board[nx][ny] == 3 && teamNum[nx][ny] == i) {
							beforeX = x;
							beforeY = y;
							temp.x = nx;
							temp.y = ny;
							copy[nx][ny] = 1;
							break;
						}
					}					
				}
				else {
					copy[beforeX][beforeY] = num;
					temp.x = beforeX;
					temp.y = beforeY;
					beforeX = x;
					beforeY = y;
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(copy[i][j] > 0 && copy[i][j] < 4)
					board[i][j] = copy[i][j];
				if(copy[i][j] == 0 && teamNum[i][j] > 0)
					board[i][j] = 4;
			}
		}
	}

	private static void dfs(int x, int y, int num, int idx) {
		teamNum[x][y] = idx;
		visited[x][y] = true;
		if(num >0 && num < 4) teamList[idx].add(new Point(x, y));
		
//		if(num == 3) return;
		
		for(int i=1;i<=4;i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(!isValid(nx, ny) || visited[nx][ny]) continue;
			
			if(num == 1 && board[nx][ny] == 2) {
				dfs(nx, ny, board[nx][ny], idx);
			}
			if(num == 2 && board[nx][ny] == 2) {
				 dfs(nx, ny, board[nx][ny], idx);
			}
			if(num == 2 && board[nx][ny] == 3) {
				dfs(nx, ny, board[nx][ny], idx);
			}
			if(num == 3 && board[nx][ny] == 4) {
				dfs(nx, ny, board[nx][ny], idx);
			}
			if(num == 4 && board[nx][ny] == 4) {
				dfs(nx, ny, board[nx][ny], idx);
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
