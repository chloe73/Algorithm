package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_나무박멸 {
	
	static int N,M,K,C,result;
	static int[][] board,drug;
	static int[] dx = {-1,1,0,0,-1,1,-1,1};
	static int[] dy = {0,0,-1,1,-1,-1,1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		drug = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		for(int i=0;i<M;i++) {
			
			// 1. 인접한 칸 중 나무가 있는 수만큼 나무 성장
			grow_tree();
			System.out.println("===== grow tree 후 =====");
			print();
			// 2. 나무 번식 : 인접한 칸 중 clean한 칸의 개수만큼 빈칸에 나무 생김
			add_tree();
			System.out.println("===== add tree 후 =====");
			print();
			// 3. 제초제 뿌림 => 해당 칸에 c년 동안 제초제 유지됨
			suppress_tree();
			System.out.println("===== suppress tree 후 =====");
			print();
			// 1년 끝 -> 제초제 1씩 증가시킴
			minus_drug();
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

	private static void minus_drug() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(drug[i][j] > 0) drug[i][j]--;
			}
		}
	}

	private static void suppress_tree() {
		
		// 제초제를 뿌릴 위치 선정
		int maxNum = Integer.MIN_VALUE;
		int tx = N-1; int ty = N-1;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] >= 0) {
					int num = spread(i,j);
					
					if(num > maxNum) {
						tx = i;
						ty = j;
						maxNum = num;
					} else if(num == maxNum) {
						if(tx > i) {
							tx = i;
							ty = j;
						} else if(tx == i) {
							ty = Math.min(ty, j);
						}
					}
				}
			}
		}
		System.out.println(tx+" "+ty);
		result += maxNum;
		System.out.println(maxNum);
		board[tx][ty] = 0;
		drug[tx][ty] = C+1;
		
		// 제초제 뿌리는 작업 진행 - k의 범위만큼 대각선 방향으로 퍼짐
		for(int d=4;d<8;d++) {
			int nx = tx;
			int ny = ty;
			int cnt = 0;
			
			while(cnt++ < K) {
				nx += dx[d];
				ny += dy[d];
				
				if(!isValid(nx,ny)) break;
				
				// 벽인 경우 || 나무가 없는 빈칸인 경우 : 
				if(board[nx][ny] == -1) break;
				
				else if(board[nx][ny] == 0) {
					drug[nx][ny] = C+1;
					break;
				}
				board[nx][ny] = 0;
				drug[nx][ny] = C+1;
			}
		}
	}

	private static int spread(int x, int y) {
		
		if(board[x][y] == 0) return 0;
		
		int cnt = board[x][y];
		
		for(int d=4;d<8;d++) {
			int nx = x;
			int ny = y;
			int num = 0;
			
			while(num++ < K) {
				nx += dx[d];
				ny += dy[d];
				
				if(!isValid(nx,ny)) break;
				
				// 벽인 경우 || 나무가 없는 빈칸인 경우 : 
				if(board[nx][ny] == -1 || board[nx][ny] == 0) break;
				
				cnt += board[nx][ny];
			}
		}
		
		return cnt;
	}

	private static void add_tree() {
		int[][] copy = new int[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] > 0) {

					ArrayList<int[]> targetList = new ArrayList<>();
					
					for(int d=0;d<4;d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						if(!isValid(nx, ny)) continue;
						
						// 벽, 다른 나무, 제초제 모두 없는 칸인 경우 ok
						if(board[nx][ny] == 0 && drug[nx][ny] == 0) {
							targetList.add(new int[] {nx,ny});
						}
					}
					if(targetList.size() == 0) continue;
					int num = board[i][j] / targetList.size();
					for(int [] temp : targetList) {
						copy[temp[0]][temp[1]] += num;
					}
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(copy[i][j] > 0) board[i][j] = copy[i][j];
			}
		}
	}

	private static void grow_tree() {
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] > 0 && drug[i][j] == 0) {
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

	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
