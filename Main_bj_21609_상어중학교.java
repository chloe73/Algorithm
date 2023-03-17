package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_21609_상어중학교 {
	
	static int N,M,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static PriorityQueue<Block> pq;
	static class Block implements Comparable<Block>{
		int x,y,rainbowCnt,totalCnt;
		public Block(int x, int y, int rainbowCnt, int totalCnt) {
			this.x = x;
			this.y = y;
			this.rainbowCnt = rainbowCnt;
			this.totalCnt = totalCnt;
		}
		public int compareTo(Block o) {
			if(this.totalCnt == o.totalCnt) {
				if(this.rainbowCnt == o.rainbowCnt) {
					if(this.x == o.x) return o.y - this.y; // 기준 블록의 열의 번호가 가장 큰 것
					return o.x - this.x; // 기준 블록의 행의 번호가 가장 큰 것
				}
				return o.rainbowCnt - this.rainbowCnt; // 무지개 블록 개수가 가장 많은 것
			}
			return o.totalCnt - this.totalCnt; // 블록 그룹의 크기가 가장 큰 것
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(true) {
			
			// 1. 크기가 가장 큰 블록 그룹 찾기
			find_biggest_group();
			
			if(pq.size() == 0) break;
			
			// 2. 1에서 찾은 블록 그룹의 모든 블록을 제거한다. 블록의 수의 제곱의 값만큼 점수 획득한다.
			Block group = pq.poll();
//			System.out.println(group.x + " " + group.y + " " + group.totalCnt + " " + group.rainbowCnt);
			delete_block(group);
			
			// 3. 격자 중력이 작용
			gravity();
			
			// 4. 격자가 90도 반시계 회전
			rotate();
			
			// 5. 다시 격자에 충력이 발생한다.
			gravity();
		}
		
	}

	private static void rotate() {
		// 90도 반시계 회전
		int[][] temp = new int[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				temp[N-j-1][i] = board[i][j];
			}
		}
		
		board = temp;
	}

	private static void gravity() {
		
		for(int i=N-2;i>=0;i--) {
			for(int j=0;j<N;j++) {
				if(board[i][j] >= 0 && board[i][j] <= M) {
					int row = i;
					int cnt = 0;
					
					while(++row < N) {
						if(board[row][j] == -7) cnt++;
						else if(board[row][j] == -1 || (board[row][j] >= 0 && board[i][j] <= M)) break;
					}
					if(cnt > 0) {
						board[i+cnt][j] = board[i][j];
						board[i][j] = -7;
						
					}
				}
			}
		}
		
	}

	private static void delete_block(Block group) {
		
		int r = group.x;
		int c = group.y;
		int blockNum = board[r][c];
		int totalCnt = group.totalCnt;
		// 점수 획득
		result += (int) Math.pow(totalCnt, 2);
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {r,c});
		visited[r][c] = true;
		board[r][c] = -7; // board에서 블록 삭제
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
				
				if(visited[nx][ny] || board[nx][ny] == -1) continue;
				
				if(board[nx][ny] == 0) {
					visited[nx][ny] = true;
					board[nx][ny] = -7; // board에서 블록 삭제
					q.add(new int[] {nx,ny});
				}
				
				else if(board[nx][ny] == blockNum) {
					visited[nx][ny] = true;
					board[nx][ny] = -7; // board에서 블록 삭제
					q.add(new int[] {nx,ny});
				}
				
			}
		}
	}

	private static void find_biggest_group() {
		
		pq = new PriorityQueue<>();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] >= 1 && board[i][j] <= M) { // 일반 블록이라면 탐색 시작
					bfs(i,j);				
				}
			}
		}
		
	}

	private static void bfs(int i, int j) {
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] visited = new boolean[N][N];
		int blockNum = board[i][j];
		q.add(new int[] {i,j,1,0});
		visited[i][j] = true;
		
		int blockCnt = 1;
		int rCnt = 0;
		int px =i, py =j;
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			int rainbowCnt = temp[3];
			
			// 기준 블록 세팅 ( 무지개색 블록이 아닌 일반 블록 중에서 )
			if(board[x][y] > 0) {
				if(px == x) { // 행이 같다면
					if(py > y) py = y; // 열이 작은 걸로 세팅
				} else if(px > x) { // 행이 작은 값으로 세팅
					px = x;
					py = y;
				}				
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
				
				if(visited[nx][ny] || board[nx][ny] == -1) continue;
				
				if(board[nx][ny] == 0) {
					rCnt++;
					blockCnt++;
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,cnt+1,rainbowCnt+1});
				}
				
				else if(board[nx][ny] == blockNum) {
					blockCnt++;
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,cnt+1,rainbowCnt});
				}
				
			}
			
		}
		// 그룹에 속한 블록의 개수가 2개 이상이라면 pq에 추가
		if(blockCnt >= 2) pq.add(new Block(px, py, rCnt, blockCnt));
	}

}