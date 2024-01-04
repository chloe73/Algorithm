package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct_2019_fh_pm_1_격자_숫자_놀이 {
	
	static int R,C,K,result;
	static int[][] board;
	static int rSize, cSize;
	static class Number implements Comparable<Number>{
		int num;
		int cnt;
		public Number(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
		
		public int compareTo(Number o) {
			// 출현하는 횟수가 같은 숫자가 있는 경우에는 해당 숫자가 작은 순서대로 정렬을 수행합니다.
			if(this.cnt == o.cnt) {
				return this.num - o.num;
			}
			// 정렬 기준은 출현 빈도 수가 적은 순서대로 정렬을 합니다.
			return this.cnt - o.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken())-1;
		C = Integer.parseInt(st.nextToken())-1;
		K = Integer.parseInt(st.nextToken());

		rSize = 3;
		cSize = 3;
		board = new int[rSize][cSize];
		for(int i=0;i<3;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		// 행이나 열의 길이가 100을 넘어가는 경우에는 처음 100개의 격자를 제외하고는 모두 버립니다.
		while(true) {
			
			if(result > 100) {
				result = -1;
				break;
			}
			
			if(0<=R && R<rSize && 0<=C && C<cSize) {
				if(board[R][C] == K) {
					break;
				}
			}
			
			// 행의 개수가 열의 개수보다 크거나 같은 경우
			if(rSize >= cSize) {
				change_row();
			}
			// 행의 개수가 열의 개수보다 작은 경우
			else {
				change_col();
			}
			
			result++;
//			System.out.println("====== "+result + " ======");
//			print();
		}
	}
	
	private static void print() {
		for(int i=0;i<rSize;i++) {
			for(int j=0;j<cSize;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static void change_col() {
		// cSize는 바뀌지 않고 rSize가 바뀐다.
		
		PriorityQueue<Number>[] pq = new PriorityQueue[cSize];
		
		int renewalRSize = 0;
		for(int j=0;j<cSize;j++) {
			pq[j] = new PriorityQueue<>();
			boolean[] visited = new boolean[101];
			int[] isChecked = new int[101];
			
			// 각 열 별 숫자 빈도수 체크
			for(int i=0;i<rSize;i++) {
				if(board[i][j] == 0) continue;
				isChecked[board[i][j]]++;
			}
			
			for(int i=0;i<rSize;i++) {
				if(board[i][j] == 0) continue;
				int temp = board[i][j];
				if(!visited[temp]) {
					pq[j].add(new Number(temp, isChecked[temp]));
					visited[temp] = true;
				}
			}
			
			renewalRSize = Math.max(renewalRSize, pq[j].size()*2);
		}
		
		rSize = renewalRSize;
		
		// board 업데이트하기
		// 변화된 격자의 크기는 가장 큰 길이를 기준으로 맞추고 나머지는 0을 채워줍니다.
		if(renewalRSize > 100) {
			board = new int[100][cSize];
			// 행의 길이가 100을 넘어가는 경우에는 처음 100개의 격자를 제외하고는 모두 버립니다.
			for(int j=0;j<cSize;j++) {
				for(int i=0;i<100;i+=2) {
					if(pq[j].isEmpty()) break;
					Number temp = pq[j].poll();
					board[i][j] = temp.num;
					board[i+1][j] = temp.cnt;
				}
			}
		}
		else {
			board = new int[renewalRSize][cSize];
			for(int j=0;j<cSize;j++) {
				for(int i=0;i<renewalRSize;i+=2) {
					if(pq[j].isEmpty()) break;
					Number temp = pq[j].poll();
					board[i][j] = temp.num;
					board[i+1][j] = temp.cnt;
				}
			}
		}
	}

	private static void change_row() {
		// rSize는 바뀌지 않고 cSize가 바뀐다.
		
		// a. 모든 행에 대하여 정렬을 수행합니다. 정렬 기준은 출현 빈도 수가 적은 순서대로 정렬을 합니다.
		// b. 출현하는 횟수가 같은 숫자가 있는 경우에는 해당 숫자가 작은 순서대로 정렬을 수행합니다.
		// c. 정렬을 수행할 때 숫자와 해당하는 숫자의 출현 빈도 수를 함께 출력해줍니다.
		
		PriorityQueue<Number>[] pq = new PriorityQueue[rSize];
		
		int renewalCSize = 0;
		for(int i=0;i<rSize;i++) {
			pq[i] = new PriorityQueue<>();
			boolean[] visited = new boolean[101];
			int[] isChecked = new int[101];
			
			// 각 행 별 숫자 빈도수 체크
			for(int j=0;j<cSize;j++) {
				// 연산을 수행할 때는 0을 무시하고 수행해줍니다.
				if(board[i][j] == 0) continue;
				// 현재 board에 있는 숫자들의 빈도수 기록
				isChecked[board[i][j]]++;
			}
			
			for(int j=0;j<cSize;j++) {
				if(board[i][j] == 0) continue;
				int temp = board[i][j];
				if(!visited[temp]) {
					pq[i].add(new Number(temp, isChecked[temp]));
					visited[temp] = true;
				}
			}
			
			renewalCSize = Math.max(renewalCSize, pq[i].size()*2);
		}
		
		cSize = renewalCSize;
		
		// board 업데이트하기
		// 변화된 격자의 크기는 가장 큰 길이를 기준으로 맞추고 나머지는 0을 채워줍니다.
		if(renewalCSize > 100) {
			board = new int[rSize][100];
			// 열의 길이가 100을 넘어가는 경우에는 처음 100개의 격자를 제외하고는 모두 버립니다.
			for(int i=0;i<rSize;i++) {
				for(int j=0;j<100;j+=2) {
					if(pq[i].isEmpty()) {
						break;
					}
					Number temp = pq[i].poll();
					// 정렬을 수행할 때 숫자와 해당하는 숫자의 출현 빈도 수를 함께 출력해줍니다.
					board[i][j] = temp.num;
					board[i][j+1] = temp.cnt;
				}
			}
		}
		else {
			board = new int[rSize][renewalCSize];
			for(int i=0;i<rSize;i++) {
				for(int j=0;j<renewalCSize;j+=2) {
					if(pq[i].isEmpty()) {
						break;
					}
					Number temp = pq[i].poll();
					board[i][j] = temp.num;
					board[i][j+1] = temp.cnt;
				}
			}
		}
		
	}

}
