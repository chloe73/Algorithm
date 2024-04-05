package algo.codetree1.firstHalf2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_나무박멸 {
	
	static int N,M,K,C,result;
	static int[][] board;
	static int[][] spray;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		spray = new int[N][N];
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
		
		while(M-- > 0) {
			// 1. 인접한 네 개의 칸 중 나무가 있는 칸의 수만큼 나무가 성장합니다. 성장은 모든 나무에게 동시에 일어납니다.
			grow();
			System.out.println("grow 후");
			print();
			// 2. 기존에 있었던 나무들은 인접한 4개의 칸 중 벽, 다른 나무, 제초제 모두 없는 칸에 번식을 진행합니다. 
			// 이때 각 칸의 나무 그루 수에서 총 번식이 가능한 칸의 개수만큼 나누어진 그루 수만큼 번식이 되며, 나눌 때 생기는 나머지는 버립니다. 
			// 번식의 과정은 모든 나무에서 동시에 일어나게 됩니다.
			spread();
			System.out.println("spread 후");
			print();
			// 3. 각 칸 중 제초제를 뿌렸을 때 나무가 가장 많이 박멸되는 칸에 제초제를 뿌립니다.
			spray();
			System.out.println("spray 후");
			print();
			System.out.println(" ===================== ");
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(spray[i][j] > 0) spray[i][j]--;
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
	
	private static void spray() {
		int num = 0;
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		int[] ddx = {-1,-1,1,1};
		int[] ddy = {-1,1,-1,1};
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] == -1) continue;
				
				// 나무가 없는 칸에 제초제를 뿌리면 박멸되는 나무가 전혀 없는 상태로 끝이 나지만,
				if(board[i][j] == 0) {
					if(num == board[i][j]) {
						if(x > i) {
							x = i;
							y = j;
						}
						else if(x == i && y > j) {
							y = j;
						}
					}
					continue;
				}
				
				// 나무가 있는 칸에 제초제를 뿌리게 되면 4개의 대각선 방향으로 k칸만큼 전파되게 됩니다.
				int cnt = board[i][j];
				for(int d=0;d<4;d++) {
					for(int k=1;k<=K;k++) {
						int nx = i + ddx[d]*k;
						int ny = j + ddy[d]*k;
						
						// 단 전파되는 도중 벽이 있거나 나무가 아얘 없는 칸이 있는 경우, 그 칸 까지는 제초제가 뿌려지며 그 이후의 칸으로는 제초제가 전파되지 않습니다.
						if(!isValid(nx, ny) || board[nx][ny] == -1 || board[nx][ny] == 0) break;
//						if(board[nx][ny] == 0) {
//							spray[nx][ny] = C;
//							break;
//						}
						
						// 제초제가 뿌려진 칸에는 c년만큼 제초제가 남아있다가 c+1년째가 될 때 사라지게 됩니다.
						// 제초제가 뿌려진 곳에 다시 제초제가 뿌려지는 경우에는 새로 뿌려진 해로부터 다시 c년동안 제초제가 유지됩니다.
						if(board[nx][ny] > 0) {
							cnt += board[nx][ny];
						}
					}
				}
				
				if(cnt > num) {
					num = cnt;
					x = i;
					y = j;
				}
				else if(cnt == num) {
					if(x > i) {
						x = i;
						y = j;
					}
					else if(x == i && y > j) {
						y = j;
					}
				}
			}
		}
		
		result += num;
		board[x][y] = 0;
		spray[x][y] = C+1;
		for(int d=0;d<4;d++) {
			for(int k=1;k<=K;k++) {
				int nx = x + ddx[d]*k;
				int ny = y + ddy[d]*k;
				
				// 단 전파되는 도중 벽이 있거나 나무가 아얘 없는 칸이 있는 경우, 그 칸 까지는 제초제가 뿌려지며 그 이후의 칸으로는 제초제가 전파되지 않습니다.
				if(!isValid(nx, ny) || board[nx][ny] == -1) break;
				if(board[nx][ny] == 0) {
					spray[nx][ny] = C+1;
					break;
				}
				
				// 제초제가 뿌려진 칸에는 c년만큼 제초제가 남아있다가 c+1년째가 될 때 사라지게 됩니다.
				// 제초제가 뿌려진 곳에 다시 제초제가 뿌려지는 경우에는 새로 뿌려진 해로부터 다시 c년동안 제초제가 유지됩니다.
				if(board[nx][ny] > 0) {
					board[nx][ny] = 0;
					spray[nx][ny] = C+1;
				}
			}
		}
	}

	private static void spread() {
		int[][] arr = new int[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] > 0) {
					int cnt = 0;
					Queue<int[]> q = new LinkedList<>();
					
					for(int d=0;d<4;d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						if(!isValid(nx, ny) || board[nx][ny] == -1 || board[nx][ny] > 0 || spray[nx][ny] > 0) continue;
						
						if(board[nx][ny] == 0) {
							cnt++;
							q.add(new int[] {nx,ny});
						}
					}
					if(cnt == 0) continue;
					int num = board[i][j] / cnt;
					while(!q.isEmpty()) {
						int[] temp = q.poll();
						int x = temp[0];
						int y = temp[1];
						
						arr[x][y] += num;
					}
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				board[i][j] += arr[i][j];
			}
		}
	}

	private static void grow() {
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] <= 0) continue;
				int cnt = 0;
				for(int d=0;d<4;d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(!isValid(nx, ny)) continue;
					
					if(board[nx][ny] > 0) cnt++;
				}
				board[i][j] += cnt;
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
