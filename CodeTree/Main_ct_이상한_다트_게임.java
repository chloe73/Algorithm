package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_이상한_다트_게임 {
	
	static int N,M,Q,result;
	static int[][] board,cmd;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		board = new int[N+1][M];
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cmd = new int[Q][3];
		for(int i=0;i<Q;i++) {
			st = new StringTokenizer(br.readLine());
			
			cmd[i][0] = Integer.parseInt(st.nextToken());
			cmd[i][1] = Integer.parseInt(st.nextToken());
			cmd[i][2] = Integer.parseInt(st.nextToken());
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int q=0;q<Q;q++) {
			int x = cmd[q][0]; // 회전하는 원판의 번호가 x의 배수
			int d = cmd[q][1]; // d의 경우 시계 방향과 반시계 방향
			int k = cmd[q][2]; // k의 경우 몇 칸을 회전시킬지 결정

			// 1. 회전
			rotate(x, d, k);

			// 2. 원판에 수가 남아있으면 인접 숫자 같은 수 지운다.
			if(check_board()) {
				int count = 0; // 원판에서 지워지는 수의 개수
				boolean[][] visited = new boolean[N+1][M];
				
				for(int i=1;i<=N;i++) {
					for(int j=0;j<M;j++) {
						int num = board[i][j];
						// 0은 이미 지워진 숫자임. 따라서 지울 필요 없음.
						if(num == 0) continue;
						int cnt = 1;
						// 인접하다 : 양 옆, 위, 아래
						for(int dir=0;dir<4;dir++) {
							int nx = i + dx[dir];
							int ny = j + dy[dir];
							
							if(!is_valid(nx,ny)) continue;
							
							if(ny == -1) ny = M-1;
							
							if(ny == M) ny = 0;
							
							if(visited[nx][ny]) continue;
							
							if(num == board[nx][ny]) {
								cnt++;
								visited[nx][ny] = true;
							}
						}
						if(cnt == 1) continue;
						count += cnt;
					}
				}
				// 만약 1번부터 n번까지의 원판에 지워지는 수가 없는 경우에는 원판 전체에 적힌 수의 평균을 구해서 정규화해줍니다. 
				if(count == 0) {
					// 정규화란 전체 원판에서 평균보다 큰 수는 1을 빼고, 작은 수는 1을 더해주는 과정을 말합니다. 
					int avg = get_avg();

					// 평균과 같은 수는 따로 변형하지 않으며, 원판에 남은 수가 없을 경우에는 정규화를 진행하지 않습니다. 
					if(avg == -1) continue;
					
					// 정규화 진행
					for(int i=1;i<=N;i++) {
						for(int j=0;j<M;j++) {
							if(board[i][j] == 0) continue;
							if(board[i][j] == avg) continue;
							if(board[i][j] < avg) board[i][j]++;
							if(board[i][j] > avg) board[i][j]--;
						}
					}
				}
				else {
					for(int i=1;i<=N;i++) {
						for(int j=0;j<M;j++) {
							if(visited[i][j]) board[i][j] = 0;
						}
					}
				}
			}

		}
		
		result = get_result();
	}
	
	private static int get_result() {
		int sum = 0;
		
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] > 0) {
					sum += board[i][j];
				}
			}
		}
		
		return sum;
	}
	
	// 평균을 구할 때는 편의상 소숫점 아래의 수는 버립니다.
	private static int get_avg() {
		int sum = 0;
		int cnt = 0;
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == 0) continue;
				sum += board[i][j];
				cnt++;
			}
		}
		
		if(sum == 0) return -1;
		return sum / cnt;
	}
	
	private static boolean is_valid(int i, int j) {
		if(i<=0 || j<-1 || i>=N+1 || j>M+1) return false;
		return true;
	}
	
	// 원판에 수가 남아있는지 확인하는 함수
	private static boolean check_board() {
		boolean flag = false;
		
		for(int i=1;i<=N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] >= 1) return true;
			}
		}
		
		return flag;
	}
	
	// 시계방향 or 반시계 방향 회전
	private static void rotate(int x, int d, int k) {
		
		ArrayList<Integer> targetList = new ArrayList<>();
		
		// x의 배수 원판 찾기
		for(int i=1;i<=N;i++) {
			if(i % x == 0) targetList.add(i);
		}
		
		if(d == 0) { // 시계 방향
			for(int i : targetList) {
				// 각 원판 숫자 이동
				int[] newNum = new int[M];
				for(int j=0;j<M;j++) {
					if(j+k < M) newNum[j+k] = board[i][j];
					else newNum[j+k-M] = board[i][j];
				}
				// 기존 board에 새로운 값 갱신
				for(int j=0;j<M;j++) {
					board[i][j] = newNum[j];
				}
			}
		}
		else { // 반시계 방향
			for(int i : targetList) {
				// 각 원판 숫자 이동
				int[] newNum = new int[M];
				for(int j=M-1;j>=0;j--) {
					if(j-k >= 0) newNum[j-k] = board[i][j];
					else newNum[M+(j-k)] = board[i][j];
				}
				
				// 기존 board에 새로운 값 갱신
				for(int j=0;j<M;j++) {
					board[i][j] = newNum[j];
				}
			}
		}
	}

}
