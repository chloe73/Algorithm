package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2583 {
	
	static int M,N,K,result;
	static boolean[][] board, visited;
	static ArrayList<Integer> resultList;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		board = new boolean[M][N];
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			for(int x=M-b-1;x>=M-d;x--) {
				for(int y=c-1;y>=a;y--) {
					board[x][y] = true;
				}
			}
		} // input end
		
//		for(int i=0;i<M;i++) {
//			for(int j=0;j<N;j++) {
//				if(board[i][j]) System.out.print("* ");
//				else System.out.print("- ");
//			}
//			System.out.println();
//		}
		
		visited = new boolean[M][N];
		resultList = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				if(!board[i][j] && !visited[i][j]) {
					result++;
					solve(i,j);
				}
			}
		}
		Collections.sort(resultList);
		
		// 첫째 줄에 분리되어 나누어지는 영역의 개수를 출력한다.
		System.out.println(result);
		// 둘째 줄에는 각 영역의 넓이를 오름차순으로 정렬하여 빈칸을 사이에 두고 출력한다.
		for(int i : resultList) {
			System.out.print(i+" ");
		}
	}

	private static void solve(int i, int j) {
		Queue<int[]> q = new LinkedList<>();
		visited[i][j] = true;
		q.add(new int[] {i,j});
		int count = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			count++;
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny) || visited[nx][ny]) continue;
				
				if(!board[nx][ny]) {					
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
				}
			}
		}
		
		resultList.add(count);
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=M || c>=N) return false;
		return true;
	}
}
