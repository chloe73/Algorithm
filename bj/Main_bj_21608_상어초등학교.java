package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_21608_상어초등학교 {
	
	static int N,result,studentNum;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point implements Comparable<Point> {
		int x,y,favCnt,emptyCnt;
		public Point(int x, int y, int favCnt, int emptyCnt) {
			this.x = x;
			this.y = y;
			this.favCnt = favCnt; // 인접한 칸에 좋아하는 학생이 몇명있는지
			this.emptyCnt = emptyCnt; // 인접한 칸에  빈칸이 몇개 있는지
		}
		public int compareTo(Point o) {
			if(this.favCnt == o.favCnt) {
				if(this.emptyCnt == o.emptyCnt) {
					if(this.x == o.x) {
						return this.y - o.y;
					}
					return this.x - o.x;
				}
				return o.emptyCnt - this.emptyCnt;
			}
			return o.favCnt - this.favCnt;
		}
	}
	static int[][] favList;
	static PriorityQueue<Point> favQ;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		studentNum = (int) Math.pow(N, 2);
		favList = new int[studentNum+1][5];
		
		for(int i=1;i<=studentNum;i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0;j<5;j++) {
				int num = Integer.parseInt(st.nextToken());
				favList[i][j] = num;
			}
		} // input end
		
		solve();
		
		find_answer();
		
		System.out.println(result);
	}

	private static void find_answer() {
		// 각 학생의 인접 칸에 본인이 좋아하는 학생이 있다면
		for(int x=0;x<N;x++) {
			for(int y=0;y<N;y++) {
				int favCnt = 0;
				int idx = board[x][y];
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
					
					if(board[nx][ny] > 0) {
						if(find_favNum(idx,board[nx][ny])) favCnt++;
					}
					
				}
				int score = (int) Math.pow(10, favCnt-1);
				
				result += score;
			}
		}
		
		
	}

	private static void solve() {
		
		for(int i=1;i<=studentNum;i++) {
			favQ = new PriorityQueue<>();
			
			// 1. 비어있는 칸 중 좋아하는 학생이 가장 많은 칸 확인
			int temp = favList[i][0];
			for(int x=0;x<N;x++) {
				for(int y=0;y<N;y++) {
					if(board[x][y] == 0) search(i,x,y);
				}
			}
			
			Point p = favQ.poll(); // 최종 자리 선정
			board[p.x][p.y] = i;
		}
		
	}

	// 4방 탐색하는 함수
	private static void search(int i,int x, int y) {
		
		int emptyCnt = 0;
		int favCnt = 0;
		
		for(int d=0;d<4;d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
			
			if(board[nx][ny] == 0) emptyCnt++;
			
			if(board[nx][ny] > 0) {
				if(find_favNum(i,board[nx][ny])) favCnt++;
			}
			
		}
		
		// pq에 저장
		favQ.add(new Point(x, y, favCnt, emptyCnt));
	}

	private static boolean find_favNum(int i, int target) {
		int targetNum = favList[target][0];
		
		for(int idx=1;idx<5;idx++) {
			int num = favList[i][idx];
			if(num == targetNum) return true;
		}
		
		return false;
	}

}