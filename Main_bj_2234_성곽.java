package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2234_성곽 {
	
	static int N,M,roomCnt,roomSize,biggerRoomSize;
	static int[][] arr;
	static boolean[][] visited = new boolean[M][N];
	static int[] dx = {0,-1,0,1};
	static int[] dy = {-1,0,1,0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[M][N];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j]) findRoom(i, j,roomCnt++);
			}
		}
		
		boolean[] check = new boolean[roomCnt];
		
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				
			}
		}
		
		
		System.out.println(roomCnt);
		System.out.println(roomSize);
		System.out.println(biggerRoomSize);
	}

	private static void findRoom(int i, int j, int div) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i,j});
		visited[i][j] = true;
		
		int num = 1;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			
		}
	}

}
