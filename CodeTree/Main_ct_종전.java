package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_ct_종전 {
	
	static int N,result,totalSum;
	static int[][] board;
	static boolean[][] boundary;
	static int[] dx = {-1,-1,1,1};
	static int[] dy = {1,-1,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		board = new int[N][N];
		boundary = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				totalSum += board[i][j];
			}
		} // input end
		result = Integer.MAX_VALUE;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		// (i,j)가 기울어진 직사각형을 만들 수 있는지 확인
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				for(int k=1;k<N;k++) {
					for(int l=1;l<N;l++) {
						if(is_possible_rect(i,j,k,l)) {
							result = Math.min(result, get_diff(i,j,k,l));
						}
					}
				}
			}
		}
		
	}

	private static int get_diff(int x, int y, int k, int l) {
		int[] population = new int[5];
		
		// 경계 표시
		mark_boundary(x,y,k,l);
		
		// 좌측 상단 확인
		// 2번 부족은 기울어진 직사각형의 좌측 상단 경계의 윗부분에 해당하는 지역을 가지게 됩니다.
		// 이때 위쪽 꼭짓점의 위에 있는 칸들은 모두 포함하지만 왼쪽 꼭짓점의 왼쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=0;i<x-l;i++) {
			for(int j=0;j<=y+k-l && !boundary[i][j];j++) {
				population[1] += board[i][j];
			}
		}
		
		// 우측 상단
		// 3번 부족은 기울어진 직사각형의 우측 상단 경계의 윗부분에 해당하는 지역을 가지게 됩니다.
		// 이때 오른쪽 꼭짓점의 오른쪽에 있는 칸들은 모두 포함하지만 윗쪽 꼭짓점의 위쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=0;i<=x-k;i++) {
			for(int j=N-1;j>=y+k-l+1 && !boundary[i][j];j--) {
				population[2] += board[i][j];
			}
		}
		
		// 좌측 하단
		// 4번 부족은 기울어진 직사각형의 좌측 하단 경계의 아랫부분에 해당하는 지역을 가지게 됩니다.
		// 이때 왼쪽 꼭짓점의 왼쪽애 있는 칸들은 모두 포함하지만 아랫쪽 꼭짓점의 아래쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=x-l;i<N;i++) {
			for(int j=0;j<y && !boundary[i][j];j++) {
				population[3] += board[i][j];
			}
		}
		
		// 우측 하단
		// 5번 부족은 기울어진 직사각형의 우측 하단 경계의 아랫부분에 해당하는 지역을 가지게 됩니다.
		// 이때 아랫쪽 꼭짓점의 아랫쪽에 있는 칸들은 모두 포함하지만 오른쪽 꼭짓점의 오른쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=x-k+1;i<N;i++) {
			for(int j=N-1;j>=y && !boundary[i][j];j--) {
				population[4] += board[i][j];
			}
		}
		
		// 우측 하단 구하는 부분 -> 틀린 코드
//		for(int i=x-k+1;i<N;i++) {
//			for(int j=y;j<N && !boundary[i][j];j++) {
//				population[4] += board[i][j];
//			}
//		}
		
		// 1번 부족은 기울어진 직사각형의 경계와 그 안에 있는 지역을 가지게 됩니다.
		population[0] = totalSum - population[1] - population[2] - population[3] - population[4];
		
		int minPop = Integer.MAX_VALUE;
		int maxPop = Integer.MIN_VALUE;
		
		for(int i=0;i<5;i++) {
			minPop = Math.min(minPop, population[i]);
			maxPop = Math.max(maxPop, population[i]);
		}
		
		return maxPop-minPop;
	}

	private static void mark_boundary(int x, int y, int k, int l) {
		int[] moveNum = {k,l,k,l};
		
		boundary = new boolean[N][N];
		
		for(int d=0;d<4;d++) {
			for(int a=0;a<moveNum[d];a++) {
				x += dx[d];
				y += dy[d];
				boundary[x][y] = true;
			}
		}
	}

	// 기울어진 직사각형이 모두 board 범위 안에서 만들어지는지 확인
	private static boolean is_possible_rect(int i, int j, int k, int l) {
		if(is_valid(i-k,j+k) && is_valid(i-k-l,j+k-l) && is_valid(i-l,j-l)) return true;
		return false;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
