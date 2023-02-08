package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2234_성곽 {
	
	static int N,M,roomCnt,roomSize,biggerRoomSize;
	static int[][] arr;
	static boolean[][] visited = new boolean[M][N];
	static int[] dx = {0,-1,0,1}; // 서 북 동 남
	static int[] dy = {-1,0,1,0}; // 서 북 동 남
	static int[][][] wallCount; // 	[0] : 방의 id 저장 [1] : 이어진 방의 개수
	static class Point{
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[M][N];
		wallCount = new int[M][N][2];
		visited = new boolean[M][N];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j]) {
					findRoom(i, j,roomCnt++);
				}
			}
		}
	
		MaxRoom();
		
		System.out.println(roomCnt);
		System.out.println(roomSize);
		System.out.println(biggerRoomSize);
	}

	private static void findRoom(int x, int y, int id) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(x, y));
		int tempRoom = 0;
		visited[x][y] = true;
		ArrayList<Point> back = new ArrayList<>();
		
		while(!q.isEmpty()) {
			Point temp = q.poll();
			tempRoom++;
			back.add(new Point(temp.x, temp.y));
			
			for(int i=0;i<4;i++) {
				int nx = temp.x + dx[i];
				int ny = temp.y + dy[i];
				
				if((arr[temp.x][temp.y] & (1 << i)) == 0) { // 벽이 없는 경우
					if(nx < 0 || ny<0 || nx>=M || ny>=N) continue;
					
					if(visited[nx][ny]) continue;
					
					visited[nx][ny] = true;
					q.add(new Point(nx, ny));
				}
			}
		}
		
		roomSize = Math.max(roomSize, tempRoom);
		
		for(Point p : back) {
			wallCount[p.x][p.y][1] = tempRoom;
			wallCount[p.x][p.y][0] = id;
			
		}
	}
	
	private static void MaxRoom() {
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				for(int k=0;k<4;k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					
					if(nx < 0 || ny<0 || nx>=M || ny>=N) continue;
					
					if(wallCount[i][j][0] != wallCount[nx][ny][0]) {
						biggerRoomSize = Math.max(biggerRoomSize, wallCount[i][j][1] + wallCount[nx][ny][1]);
					}
				}
			}
		}
	}

}
