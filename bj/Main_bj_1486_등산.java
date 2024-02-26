package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1486_등산 {
	
	static int N,M,T,D,result;
	static int[][] board;
	static int[][][][] dist;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Node implements Comparable<Node>{
		int x,y,d;
		public Node(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
		public int compareTo(Node o) {
			return this.d - o.d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		dist = new int[N][M][N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				for(int k=0;k<N;k++) {
					for(int l=0;l<M;l++) {
						dist[i][j][k][l] = Integer.MAX_VALUE;
					}
				}
			}
		}
		
		board = new int[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j) >= 'a' ? s.charAt(j)-'a' : s.charAt(j)-'A';
			}
		} // input end
		result = 0;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				solve(i,j);
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(dist[i][j][0][0] + dist[0][0][i][j] <= D)
					result = Math.max(result, board[i][j]);
			}
		}
		
		System.out.println(result);
	}

	private static void solve(int i, int j) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(i, j, 0));
		dist[i][j][i][j] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(dist[temp.x][temp.y][i][j] < temp.d) continue;
			
			for(int dir=0;dir<4;dir++) {
				int nx = temp.x + dx[dir];
				int ny = temp.y + dy[dir];
				int nz;
				
				if(!isValid(nx, ny)) continue;
				
				if(Math.abs(board[nx][ny]-board[temp.x][temp.y]) > T) continue;
				
				if(board[nx][ny] <= board[temp.x][temp.y])
					nz = temp.d + 1;
				else 
					nz = (int) Math.pow(board[nx][ny]-board[temp.x][temp.y], 2) + temp.d;
				
				if(dist[nx][ny][i][j] > nz && nz <= D) {
					dist[nx][ny][i][j] = nz;
					pq.add(new Node(nx, ny, nz));
				}
			}
		}
		
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
