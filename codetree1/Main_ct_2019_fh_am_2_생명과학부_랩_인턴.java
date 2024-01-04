package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_2019_fh_am_2_생명과학부_랩_인턴 {
	
	static int N,M,K,result;
	static int sy; // 승용이 탐색 위치(열)
	static HashMap<Integer, Point> moldMap;
	static Queue<Point>[][] board;
	static int[] dx = {0,-1,1,0,0};
	static int[] dy = {0,0,0,1,-1};
	static class Point implements Comparable<Point>{
		@Override
		public String toString() {
			return "Point [idx=" + idx + ", x=" + x + ", y=" + y + ", s=" + s + ", d=" + d + ", b=" + b + "]";
		}

		int idx;
		int x,y;
		int s,d,b;
		public Point(int idx, int x, int y, int s, int d, int b) {
			this.idx = idx;
			this.x = x;
			this.y = y;
			this.s = s;
			this.d = d;
			this.b = b;
		}
		
		public int compareTo(Point o) {
			return o.b - this.b;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new LinkedList[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				board[i][j] = new LinkedList<>();
			}
		}
		
		moldMap = new HashMap<>();
		for(int i=1;i<=K;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			board[x][y].add(new Point(i, x, y, s, d, b));
			moldMap.put(i, new Point(i, x, y, s, d, b));
		} // input end
		
		sy = -1;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(++sy < M) {
			// 1. 승용이가 현재 위치에서 채취할 수 있는 곰팡이 탐색
			find();
			
//			System.out.println("===== "+sy+" =====");
//			System.out.println("find() 후");
//			for(int i : moldMap.keySet()) {
//				System.out.println(moldMap.get(i).toString());
//			}
			
			// 2. 각각의 곰팡이들 이동
			move();
//			System.out.println("move() 후");
//			for(int i : moldMap.keySet()) {
//				System.out.println(moldMap.get(i).toString());
//			}
		}
	}
	
	private static void move() {
		// 모든 곰팡이가 이동을 끝낸 후에 한 칸에 곰팡이가 두마리 이상일 때는 크기가 큰 곰팡이가 다른 곰팡이를 모두 잡아먹습니다.
		
		if(moldMap.size() == 0) return;
		
		ArrayList<Integer> removeList = new ArrayList<>();
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		
		for(int i : moldMap.keySet()) {
			Point temp = moldMap.get(i);
			
			// 해당 곰팡이의 속력이 0이라면 이동하지 않고 그대로 있는다.
			if(temp.s == 0) continue;
			
			// 이동 전 board에서 현재 곰팡이 위치 지우기
			board[temp.x][temp.y].poll();
			// board[temp.x][temp.y].clear(); ->> 이렇게 하니 틀렸었음.
			
//			int nx = temp.x;
//			int ny = temp.y;
//			int d = temp.d;
			for(int s=0;s<temp.s;s++) {
				// 주어진 방향과 속력으로 이동하며 
				int nx = temp.x + dx[temp.d];
				int ny = temp.y + dy[temp.d];
				
				// 격자판의 벽에 도달하면 반대로 방향을 바꾸고 속력을 유지한 채로 이동합니다.
				if(!is_valid(nx, ny)) {
					temp.d = change_dir(temp.d);
				}
				
				temp.x += dx[temp.d];
				temp.y += dy[temp.d];
			}
			
			// 이동 완료 후, moldMap의 현재 곰팡이 정보 업데이트하기
//			temp.x = nx;
//			temp.y = ny;
//			temp.d = d;
			
			// 이동하려는 칸에 다른 곰팡이가 있는 경우
			board[temp.x][temp.y].add(new Point(i, temp.x, temp.y, temp.s, temp.d, temp.b));
		}
		
		for(int i : moldMap.keySet()) {
			Point temp = moldMap.get(i);
			
			if(visited[temp.x][temp.y]) continue;
			
			if(board[temp.x][temp.y].size() >= 2) {
				visited[temp.x][temp.y] = true;
				PriorityQueue<Point> pq = new PriorityQueue<>();
				
				while(!board[temp.x][temp.y].isEmpty()) {
					pq.add(board[temp.x][temp.y].poll());
				}
				
				Point survive = pq.poll();
				
				while(!pq.isEmpty()) {
					Point p = pq.poll();
					removeList.add(p.idx);
				}
				
				board[temp.x][temp.y].add(survive);
			}
		}
		
		for(int i : removeList) {
			moldMap.remove(i);
		}
	}

	private static void find() {
		// 해당 열의 위에서 아래로 내려가며 탐색할 때 제일 빨리 발견한 곰팡이를 채취합니다. 
		// 곰팡이를 채취하고 나면 해당 칸은 빈칸이 되며, 해당 열에서 채취할 수 있는 곰팡이가 없는 경우도 있을 수 있음에 유의합니다.
		
		for(int x=0;x<N;x++) {
			if(!board[x][sy].isEmpty()) {
				Point target = board[x][sy].poll();
				result += target.b;
				moldMap.remove(target.idx);
				break;
			}
		}
	}
	
	private static int change_dir(int d) {
		if(d == 1) return 2;
		else if(d == 2) return 1;
		else if(d == 3) return 4;
		else return 3;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
