package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_rotate {
	
	static int N,M;
	static int[][] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int num = 1;
		arr = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				arr[i][j] = num++;
			}
		}
		
		// 시계 방향으로 90도 회전
		int[][] arr2 = new int[M][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				arr2[j][N-1-i] = arr[i][j];
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				System.out.print(arr[i][j]+"\t");
			}
			System.out.println();
		}
		
		System.out.println(" ======= 시계 방향 90도 회전 ======= ");
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(arr2[i][j]+"\t");
			}
			System.out.println();
		}
		
		arr2 = new int[M][N];
		for(int i=0;i<N;i++	) {
			for(int j=0;j<M;j++) {
				arr2[M-1-j][i] = arr[i][j];
			}
		}
		
		System.out.println(" ======= 반시계 방향 90도 회전 ======= ");
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(arr2[i][j]+"\t");
			}
			System.out.println();
		}
		
		System.out.println(" ======= 부분 시계 방향 90도 회전 ======= ");
		arr2 = rotate(1, 2, 3, 3);
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(arr2[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	private static int[][] rotate(int sx, int sy, int n, int m){
		int[][] board = new int[M][N];
		for(int i=0;i<N;i++) {
			board[i] = Arrays.copyOf(arr[i], N);
		}
		
		for(int i=sx;i<sx+n;i++) {
			for(int j=sy;j<sy+m;j++) {
				int ox = i-sx;
				int oy = j-sy;
				int rx = oy;
				int ry = n-1-ox;
				board[rx+sx][ry+sy] = arr[i][j];
			}
		}
		
		return board;
	}

}
