package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_2019_sh_pm_1_이상한_다트_게임 {
	
	static int N,M,Q;
	static int sum; // 원판에 적힌 숫자의 총합
	static int count; // 원판에 적힌 숫자의 개수
	static int[][] board;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		board = new int[N+1][M]; // r번째 원판의 m번째 수
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<Q;i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			solve(x,d,k);
		}
		
//		sum = 0;
//		for(int i=1;i<=N;i++) {
//			for(int j=0;j<M;j++) {
//				if(board[i][j] > 0) sum += board[i][j];
//			}
//		}
		
		// 게임판에 남아있는 수의 총합
		System.out.println(sum);
	}

	private static void solve(int x, int d, int k) {
		
		// 1. 회전하는 원판의 번호가 x의 배수일 경우 회전을 한다.
		// d => 0은 시계 방향, 1은 반시계 방향
		// k => k의 경우 몇 칸을 회전시킬지 결정
		rotate(x,d,k);
		
		// 2. 회전시킨 이후 원판에 수가 남아 있으면 인접하면서 숫자가 같은 수를 지웁니다.
		if(is_contains_number()) {
			// 만약 1번부터 n번까지의 원판에 지워지는 수가 없는 경우에는
			if(!remove_number()) {
				// 원판 전체에 적힌 수의 평균을 구해서 정규화해줍니다.
				normalization();
			}
		}
		
	}
	
	private static void normalization() {
		// 전체 원판에서 평균보다 큰 수는 1을 빼고, 작은 수는 1을 더해주는 과정을 말합니다.
		
		int avg = sum / count;
		
		if(count > 0) {
			// 원판에 남은 수가 없을 경우에는 정규화를 진행하지 않습니다.
			for(int i=1;i<=N;i++) {
				for(int j=0;j<M;j++) {
					if(board[i][j] > 0) {
						if(board[i][j] > avg) {
							board[i][j] -= 1;
						}
						else if(board[i][j] < avg) {
							board[i][j] += 1;
						}
					}
				}
			}
		}
		
		sum = 0;
		count = 0;
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] > 0) {
					sum += board[i][j];
					count++;
				}
			}
		}
	}

	private static boolean remove_number() {
		
		boolean flag = false;
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		int[][] arr = copy(board);
		boolean[][] visited = new boolean[N+1][M];
		
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				int temp = board[i][j];
				
				if(temp == 0) continue;
				
				for(int d=0;d<4;d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(nx<1 || nx>N) continue;
					
					if(0<=ny && ny<M && board[nx][ny] == temp && !visited[nx][ny]) {
						flag = true;
						arr[i][j] = 0;
						arr[nx][ny] = 0;
						visited[nx][ny] = true;
						continue;
					}
					
					if(ny == -1 && board[nx][M-1] == temp && !visited[nx][M-1]) {
						flag = true;
						arr[i][j] = 0;
						arr[nx][M-1] = 0;
						visited[nx][M-1] = true;
						continue;
					}
					
					if(ny == M && !visited[nx][0] && board[nx][0] == temp) {
						flag = true;
						arr[i][j] = 0;
						arr[nx][0] = 0;
						visited[nx][0] = true;
						continue;
					}
				}
			}
		}
		
		board = copy(arr);
		
		return flag;
	}
	
	private static int[][] copy(int[][] board) {
		int[][] arr = new int[N+1][M];
		sum = 0; count = 0;
		
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] > 0) {
					sum += board[i][j];
					count++;
				}
				arr[i][j] = board[i][j];
			}
		}
		
		return arr;
	}

	private static boolean is_contains_number() {
		boolean flag = false;
		
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] > 0) return true;
			}
		}
		
		return flag;
	}

	private static void rotate(int x, int d, int k) {
		// 회전할 원판 번호 targetQ에 넣기
		Queue<Integer> targetQ = new LinkedList<>();
		int cnt = 1;
		int num = x; 
		while(num <= N) {
			targetQ.add(num);
			num = x * ++cnt;
		}
		
		while(!targetQ.isEmpty()) {
			int tempNum = targetQ.poll();
			
			if(d == 0) {
				int[] tmp = new int[M];
				// 시계방향 회전 k칸 만큼 이동
				 for(int j=0;j<M;j++) {
					 if(0<=j+k && j+k<M) {
						 // 범위 안인 경우
						 tmp[j+k] = board[tempNum][j];
					 }
					 else {
						 // 범위 밖인 경우
						 tmp[j+k-M] = board[tempNum][j];
					 }
				 }
				 
				 // 기존 board에 회전한 값 갱신
				 for(int i=0;i<M;i++) {
					 board[tempNum][i] = tmp[i];
				 }
			}
			else {
				int[] tmp = new int[M];
				// 반시계방향 회전 k칸 만큼 이동
				for(int j=0;j<M;j++) {
					if(0<=j-k && j-k<M) {
						tmp[j-k] = board[tempNum][j];
					}
					else {
						tmp[j-k+M] = board[tempNum][j];
					}
				}
				
				for(int i=0;i<M;i++) {
					board[tempNum][i] = tmp[i];
				}
			}
		}
	}

}
