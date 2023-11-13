package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_17140_이차원_배열과_연산 {
	
	static int R,C,K,result;
	static int tx,ty;
	static int[][] board;
	static class Point implements Comparable<Point> {
		int num;
		int cnt;
		
		public Point(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
		
		public int compareTo(Point o) {
			// 수의 등장 횟수가 커지는(오름차순) 순으로, 그러한 것이 여러가지면 수가 커지는(오름차순) 순으로 정렬한다.
			if(this.cnt == o.cnt)
				return this.num - o.num;
			return this.cnt - o.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken()) - 1;
		C = Integer.parseInt(st.nextToken()) - 1;
		K = Integer.parseInt(st.nextToken());
		
		board = new int[100][100];
		
		for(int i=0;i<3;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		if(board[R][C] == K) {
			result = 0;
		}
		else {
			tx = 3;
			ty = 3;
			solve();
		}
		
		System.out.println(result);
	}

	private static void solve() {
		int turn = 0;
		
		while(turn++ < 100) {
		
			// R 연산: 배열 A의 모든 행에 대해서 정렬을 수행한다. 
			// 행의 개수 ≥ 열의 개수인 경우에 적용된다.
			if(tx >= ty) {
				// 한 행 또는 열에 있는 수를 정렬하려면, 각각의 수가 몇 번 나왔는지 알아야 한다.
				// 그 다음, 수의 등장 횟수가 커지는 순으로, 그러한 것이 여러가지면 수가 커지는 순으로 정렬한다.
				// 그 다음에는 배열 A에 정렬된 결과를 다시 넣어야 한다. 정렬된 결과를 배열에 넣을 때는, 
				// 수와 등장 횟수를 모두 넣으며, 순서는 수가 먼저이다.
				PriorityQueue<Point>[] pq = new PriorityQueue[tx];
				for(int i=0;i<tx;i++) {
					pq[i] = new PriorityQueue<>();
				}
				int tempCol = ty;	
				for(int i=0;i<tx;i++) {
					// 각 행 별 숫자 체크
					HashSet<Integer> numSet = new HashSet<>();
					int[] isChecked = new int[101];
					
					for(int j=0;j<tempCol;j++) {
						int num = board[i][j];
						if(num > 0)  {
							isChecked[num]++;
							numSet.add(num);						
						}
					}
					// 현재 행의 마지막 열까지 숫자 확인이 모두 끝났으면
					
					for(int temp : numSet) {
						pq[i].add(new Point(temp, isChecked[temp]));
					}
					
					ty = Math.max(ty, pq[i].size() * 2);
				}
				// 모든 행의 숫자 확인이 끝났으면 기존 board에 정렬된 숫자 채워넣기 시작
				
				for(int i=0;i<tx;i++) {
					for(int j=0;j<ty;j+=2) {
						if(ty == 100) {
							ty = 100;
							break;
						}
						
						if(pq[i].size() > 0) {
							Point temp = pq[i].poll();
							
							board[i][j] = temp.num;
							board[i][j+1] = temp.cnt;
						}
						else {
							board[i][j] = 0;
							board[i][j+1] = 0;
						}
					}
				}
			}
			// C 연산: 배열 A의 모든 열에 대해서 정렬을 수행한다.
			// 행의 개수 < 열의 개수인 경우에 적용된다.
			else {
				PriorityQueue<Point>[] pq = new PriorityQueue[ty];
				for(int i=0;i<ty;i++) {
					pq[i] = new PriorityQueue<>();
				}
				int tempRow = tx;	
				for(int j=0;j<ty;j++) {
					// 각 행 별 숫자 체크
					HashSet<Integer> numSet = new HashSet<>();
					int[] isChecked = new int[101];
					
					for(int i=0;i<tempRow;i++) {
						int num = board[i][j];
						if(num > 0)  {
							isChecked[num]++;
							numSet.add(num);						
						}
					}
					// 현재 행의 마지막 열까지 숫자 확인이 모두 끝났으면
					
					for(int temp : numSet) {
						pq[j].add(new Point(temp, isChecked[temp]));
					}
					
					tx = Math.max(tx, pq[j].size() * 2);
				}
				// 모든 행의 숫자 확인이 끝났으면 기존 board에 정렬된 숫자 채워넣기 시작
				
				for(int j=0;j<ty;j++) {
					for(int i=0;i<tx;i+=2) {
						if(tx == 100) {
							tx = 100;
							break;
						}
						if(pq[j].size() > 0) {
							Point temp = pq[j].poll();
							
							board[i][j] = temp.num;
							board[i+1][j] = temp.cnt;
						}
						else {
							board[i][j] = 0;
							board[i+1][j] = 0;
						}
					}
				}
			}
			
			if(board[R][C] == K) {
				result = turn;
				return;
			}
		}
		
		result = -1;
		return;
	}

}