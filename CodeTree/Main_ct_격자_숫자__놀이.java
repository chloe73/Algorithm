package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct_격자_숫자__놀이 {
	
	static int R,C,K,result;
	static int rowSize,colSize;
	static int[][] board;
	static class Point implements Comparable<Point>{
		int num,cnt;
		public Point(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
		@Override
		public int compareTo(Point o) {
			// 출현하는 횟수가 같은 숫자가 있는 경우에는 해당 숫자가 작은 순서대로 정렬
			if(this.cnt == o.cnt) return this.num - o.num;
			// 정렬 기준은 출현 빈도 수가 적은 순서대로 정렬
			return this.cnt - o.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		rowSize = 3;
		colSize = 3;
		
		board = new int[100][100];
		for(int i=0;i<3;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		// 목표 숫자에 도달하는 것이 불가능하거나 답이 100초를 초과한다면 -1을 출력합니다.
		System.out.println(result > 100 ? -1 : result);
	}

	private static void solve() {
		
		while(board[R-1][C-1] != K) {
			// 목표 숫자에 도달하는 것이 불가능하거나 답이 100초를 초과한다면 -1을 출력합니다.
			if(result == 101) break;
			
			if(rowSize >= colSize) calc_row();
			else calc_col();
			print();
			result++;
		}
	}
	
	private static void print() {
		System.out.println("==========================================================");
		for(int i=0;i<rowSize;i++) {
			for(int j=0;j<colSize;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static void calc_col() {
		// 행의 개수가 열의 개수보다 작은 경우
		
		PriorityQueue<Point> pq = new PriorityQueue<>();
		int newRowSize = 0;
		// a. 모든 열에 대하여 정렬을 수행합니다. 정렬 기준은 출현 빈도 수가 적은 순서대로 정렬을 합니다.
		// b. 출현하는 횟수가 같은 숫자가 있는 경우에는 해당 숫자가 작은 순서대로 정렬을 수행합니다.
		// c. 정렬을 수행할 때 숫자와 해당하는 숫자의 출현 빈도 수를 함께 출력해줍니다.
		
		for(int j=0;j<colSize;j++) {
			int[] isChecked = new int[101];
			HashSet<Integer> set = new HashSet<>();
			for(int i=0;i<rowSize;i++) {
				int tempNum = board[i][j];
				if(tempNum == 0) continue;
				set.add(tempNum);
				isChecked[tempNum]++;
				board[i][j] = 0;
			}
			for(int n : set) 
				pq.add(new Point(n, isChecked[n]));
			
			int x = -1;
			while(!pq.isEmpty()) {
				Point p = pq.poll();
				int num = p.num;
				int cnt = p.cnt;
				
				board[++x][j] = num;
				board[++x][j] = cnt;
				if(x == 99) break;
			}
			newRowSize = Math.max(newRowSize, x+1);
		}
		rowSize = newRowSize;
	}

	private static void calc_row() {
		// 행의 개수가 열의 개수보다 크거나 같은 경우
		
		PriorityQueue<Point> pq = new PriorityQueue<>();
		int newColSize = 0;
		// a. 모든 행에 대하여 정렬을 수행합니다. 정렬 기준은 출현 빈도 수가 적은 순서대로 정렬을 합니다.
		// b. 출현하는 횟수가 같은 숫자가 있는 경우에는 해당 숫자가 작은 순서대로 정렬을 수행합니다.
		// c. 정렬을 수행할 때 숫자와 해당하는 숫자의 출현 빈도 수를 함께 출력해줍니다.
		
		for(int i=0;i<rowSize;i++) {
			int[] isChecked = new int[101];
			HashSet<Integer> set = new HashSet<>();
			for(int j=0;j<colSize;j++) {
				int tempNum = board[i][j];
				if(tempNum == 0) continue;
				set.add(tempNum);
				isChecked[tempNum]++;
				board[i][j] = 0;
			}
			for(int n : set) 
				pq.add(new Point(n, isChecked[n]));
			
			int y = -1;
			while(!pq.isEmpty()) {
				Point p = pq.poll();
				int num = p.num;
				int cnt = p.cnt;
				
				board[i][++y] = num;
				board[i][++y] = cnt;
				if(y == 99) break;
			}
			newColSize = Math.max(newColSize, y+1);
		}
		
		colSize = newColSize;
	}
}
