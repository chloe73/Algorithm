package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct_놀이기구_탑승 {
	
	static int N,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<Integer>[] studentList;
	static ArrayList<Integer> orderList;
	static class Point implements Comparable<Point> {
		int x,y,favNum,emptyNum;
		public Point(int x, int y, int favNum, int emptyNum) {
			this.x = x;
			this.y = y;
			this.favNum = favNum;
			this.emptyNum = emptyNum;
		}
		
		@Override
		public int compareTo(Point o) {
			if(this.favNum == o.favNum) {
				if(this.emptyNum == o.emptyNum) {
					if(this.x == o.x) return this.y - o.y;
					return this.x - o.x;
				}
				return o.emptyNum - this.emptyNum;
			}
			return o.favNum - this.favNum;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		studentList = new ArrayList[N*N+1];
		for(int i=0;i<=N*N;i++) {
			studentList[i] = new ArrayList<>();
		}
		
		orderList = new ArrayList<>();
		
		for(int i=1;i<=N*N;i++) {
			st = new StringTokenizer(br.readLine());
			int target = Integer.parseInt(st.nextToken());
			orderList.add(target);
			for(int j=1;j<5;j++) {
				studentList[target].add(Integer.parseInt(st.nextToken()));
			}
		}
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int idx=0;idx<orderList.size();idx++) {
			int target = orderList.get(idx);
			PriorityQueue<Point> pq = new PriorityQueue<>();
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(board[i][j] > 0) continue;
					
					int fav = 0;
					int empty = 0;
					
					// 현재 좌표에서 좋아하는 학생과 빈 자리 카운팅
					for(int d=0;d<4;d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						if(!is_valid(nx,ny)) continue;
						
						if(board[nx][ny] == 0) empty++;
						
						if(studentList[target].contains(board[nx][ny])) fav++;
					}
					
					pq.add(new Point(i, j, fav, empty));
				}
			}
			
			// 우선순위가 높은 자리 뽑아내기
			Point temp = pq.poll();
			board[temp.x][temp.y] = target;
		}
		
		// 획득 점수 계산
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				int target = board[i][j];
				
				int favCnt = 0;
				
				for(int d=0;d<4;d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(!is_valid(nx,ny)) continue;
					
					if(studentList[target].contains(board[nx][ny])) favCnt++;
				}
				
				if(favCnt == 1) result += 1;
				else if(favCnt == 2) result += 10;
				else if(favCnt == 3) result += 100;
				else if(favCnt == 4) result += 1000;
			}
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
