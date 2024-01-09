package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_ct_2019_sh_am_1_종전 {
	
	static int N,result;
	static int sumPopulation; // 총 인구수
	static int[][] board,numBoard;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				sumPopulation += board[i][j];
			}
		} // input end
		
		// 가장 많은 인구 수를 가진 부족과 가장 적은 인구 수를 가진 부족의 인구 차이의 최솟값
		result = Integer.MAX_VALUE;
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		// 탐색할 직사각형 찾기
		// 직사각형 기준점
		for(int x=1;x<N-1;x++) {
			for(int y=0;y<N-2;y++) {
				for(int width=1;width<N;width++) {
					for(int height=1;height<N;height++) {
						int ax = x; int ay = y;
						int bx = x-width; int by = y+width;
						int cx = bx+height; int cy = by+height;
						int dx = x+height; int dy = y+height;
						if(is_valid(bx, by) && is_valid(cx, cy) && is_valid(dx, dy)) {
							numBoard = new int[N][N];
							int[] ddx = {-1,1,1,-1};
							int[] ddy = {1,1,-1,-1};
							// numBoard에 1번 구역 표시
							numBoard[ax][ay] = 1;
							int tx = ax, ty=ay;
							while(true) {
								if(tx == bx && ty == by) break;
								
								tx += ddx[0];
								ty += ddy[0];
								
								numBoard[tx][ty] = 1;
							}
							
							tx = bx; ty = by;
							while(true) {
								if(tx == cx && ty == cy) break;
								
								tx += ddx[1];
								ty += ddy[1];
								
								numBoard[tx][ty] = 1;
							}
							
							tx = cx; ty = cy;
							while(true) {
								if(tx == dx && ty == dy) break;
								
								tx += ddx[2];
								ty += ddy[2];
								
								numBoard[tx][ty] = 1;
							}
							
							tx = dx; ty = dy;
							while(true) {
								if(tx == ax && ty == ay) break;
								
								tx += ddx[3];
								ty += ddy[3];
								
								numBoard[tx][ty] = 1;
							}
							
							// 직사각형 4개의 꼭짓점이 모두 범위 안에 있으면 인구수 계산하기
							result = Math.min(result, calc(ax,ay,bx,by,cx,cy,dx,dy));
						}
					}
				}
			}
		}
		
		return;
	}
	
	private static int calc(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
		
		int[] arr = new int[5];
		int maxSum = 0, minSum = 0;
		int num2 = 0, num3 = 0, num4 = 0, num5 = 0;
		// 구역 2,3,4,5 인구 수 구하고 구역1은 전체 인구 수에서 나머지 4개 구역 수 빼기
		
		// 구역2 구하기
		// 2번 부족은 기울어진 직사각형의 좌측 상단 경계의 윗부분에 해당하는 지역을 가지게 됩니다.
		// 이때 위쪽 꼭짓점의 위에 있는 칸들은 모두 포함하지만 왼쪽 꼭짓점의 왼쪽에 있는 칸들은 포함하지 않습니다.
		// ax,ay, bx,by 기준으로 구하기
		for(int i=ax-1;i>=0;i--) {
			for(int j=0;j<=by;j++) {
				if(numBoard[i][j] == 1) break;
				numBoard[i][j] = 2;
				num2 += board[i][j];
			}
		}
		arr[1] = num2;
		
		// 구역3 구하기
		// 3번 부족은 기울어진 직사각형의 우측 상단 경계의 윗부분에 해당하는 지역을 가지게 됩니다.
		// 이때 오른쪽 꼭짓점의 오른쪽에 있는 칸들은 모두 포함하지만 윗쪽 꼭짓점의 위쪽에 있는 칸들은 포함하지 않습니다.
		for(int j=by+1;j<N;j++) {
			for(int i=0;i<=cx;i++) {
				if(numBoard[i][j] == 1) break;
				numBoard[i][j] = 3;
				num3 += board[i][j];
			}
		}
		arr[2] = num3;
		
		// 구역4 구하기
		// 4번 부족은 기울어진 직사각형의 좌측 하단 경계의 아랫부분에 해당하는 지역을 가지게 됩니다.
		// 이때 왼쪽 꼭짓점의 왼쪽애 있는 칸들은 모두 포함하지만 아랫쪽 꼭짓점의 아래쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=ax;i<N;i++) {
			for(int j=0;j<dy;j++) {
				if(numBoard[i][j] == 1) break;
				numBoard[i][j] = 4;
				num4 += board[i][j];
			}
		}
		arr[3] = num4;
		
		// 구역5 구하기
		// 5번 부족은 기울어진 직사각형의 우측 하단 경계의 아랫부분에 해당하는 지역을 가지게 됩니다.
		// 이때 아랫쪽 꼭짓점의 아랫쪽에 있는 칸들은 모두 포함하지만 오른쪽 꼭짓점의 오른쪽에 있는 칸들은 포함하지 않습니다.
		for(int i=N-1;i>cx;i--) {
			for(int j=N-1;j>=dy;j--) {
				if(numBoard[i][j] == 1) break;
				numBoard[i][j] = 5;
				num5 += board[i][j];
			}
		}
		arr[4] = num5;
		
		int num1 = sumPopulation-num2-num3-num4-num5;
		arr[0] = num1;
		
		Arrays.sort(arr);
		
		maxSum = arr[4];
		minSum = arr[0];
		
//		System.out.println(maxSum - minSum);
		
		return maxSum-minSum;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
