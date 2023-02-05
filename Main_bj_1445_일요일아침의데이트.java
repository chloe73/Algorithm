package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1445_일요일아침의데이트 {
	
	static int N,M,resultA,resultB;
	static char[][] road;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static PriorityQueue<Point> pq;
	static ArrayList<int[]> trashList;
	static Point start;
	static class Point implements Comparable<Point>{
		int x,y,trashCnt,aroundTrash;
		public Point(int x, int y, int trashCnt, int aroundTrash) {
			this.x = x;
			this.y = y;
			this.trashCnt = trashCnt;
			this.aroundTrash = aroundTrash;
		}
		public int compareTo(Point o) {
			if(this.trashCnt == o.trashCnt) {
				return this.aroundTrash-o.aroundTrash;
			}
			return this.trashCnt-o.trashCnt;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		road = new char[N][M];
		trashList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			road[i] = br.readLine().toCharArray();
			for(int j=0;j<M;j++) {
				if(road[i][j] == 'g') {
					trashList.add(new int[] {i,j});
				}
				if(road[i][j] == 'S') {
					start = new Point(i, j, 0, 0);
				}
			}
		} // input end
		
		// 쓰레기 주변에 'a' 표시해두기
		for(int i=0;i<trashList.size();i++) {
			int[] temp = trashList.get(i);
			for(int d=0;d<4;d++) {
				int nx = temp[0] + dx[d];
				int ny = temp[1] + dy[d];
				
				if(nx<0 ||ny<0 || nx>=N || ny>=M) continue;
				
				if(road[nx][ny] == '.') road[nx][ny] = 'a';
			}
		}
		
		visited = new boolean[N][M];
		solve();
		
		System.out.println(resultA+" "+resultB);
	}

	private static void solve() {
		pq = new PriorityQueue<>();
		pq.add(start);
		visited[start.x][start.y] = true;
		
		outer:while(!pq.isEmpty()) {
			Point temp = pq.poll();
			
			for(int i=0;i<4;i++) {
				int nx = temp.x + dx[i];
				int ny = temp.y + dy[i];
				int t = temp.trashCnt;
				int a = temp.aroundTrash;
				
				if(nx<0 ||ny<0 || nx>=N || ny>=M) continue;
				
				if(visited[nx][ny]) continue;
				
				if(road[nx][ny] == 'F') {
					resultA = t;
					resultB = a;
					break outer;
				}
				
				if(road[nx][ny] == 'g') {
					t++;
				}
				
				if(road[nx][ny] == 'a') {
					a++;
				}
				
				visited[nx][ny] = true;
				pq.add(new Point(nx, ny, t, a));
			}
		}
	}

}
