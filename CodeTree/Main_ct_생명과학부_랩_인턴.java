package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct_생명과학부_랩_인턴 {
	
	static int N,M,K,result;
	static int tempY; // 승용이가 탐색하는 열
	static PriorityQueue<Point>[][] board;
	static int[] dx = {0,-1,1,0,0};
	static int[] dy = {0,0,0,1,-1};
	static HashMap<Integer,Point> moldMap;
	static class Point implements Comparable<Point>{
		int key;
		int x,y,s,d,b;
		public Point(int key, int x, int y, int s, int d, int b) {
			this.key = key;
			this.x = x;
			this.y = y;
			this.s = s;
			this.d = d;
			this.b = b;
		}
		public int compareTo(Point o) {
			return o.b - this.b; // 크기가 큰 순서대로
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new PriorityQueue[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				board[i][j] = new PriorityQueue<>();
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
			
			moldMap.put(i,new Point(i, x, y, s, d, b));
			board[x][y].add(new Point(i, x, y, s, d, b));
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {

		// 승용이는 첫번째 열부터 탐색을 시작합니다.
		while(tempY < M) {
			
			// 해당 열의 위에서 아래로 내려가며 탐색할 때 제일 빨리 발견한 곰팡이를 채취합니다. 곰팡이를 채취하고 나면 해당 칸은 빈칸이 됩니다.
			get_mold();
			
			// 해당 열에서 채취가 끝나고 나면 곰팡이는 이동을 시작합니다.
			move_mold();
			tempY++;
		}

	}

	private static void move_mold() {
		
		// 입력으로 주어진 방향과 속력으로 이동하며 격자판의 벽에 도달하면 반대로 방향을 바꾸고 속력을 유지한 채로 이동합니다. 방향을 바꿀 때는 시간이 걸리지 않습니다.
		for(int i : moldMap.keySet()) {
			Point temp = moldMap.get(i);
			
			// 속력이 0인 경우, pass
//			if(temp.s == 0) continue; --> 이 부분 이렇게 처리하면 안됨
			
			board[temp.x][temp.y].clear();
			for(int s=0;s<temp.s;s++) {
				int nx = temp.x + dx[temp.d];
				int ny = temp.y + dy[temp.d];
				
				if(!is_valid(nx,ny)) {
					temp.d = change_dir(temp.d);
					temp.x += dx[temp.d];
					temp.y += dy[temp.d];
				}
				else {
					temp.x = nx;
					temp.y = ny;
				}
			}
		}
		
		for(int i : moldMap.keySet()) {
			Point temp = moldMap.get(i);
			
			board[temp.x][temp.y].add(temp);
		}
		
		boolean[][] visited = new boolean[N][M];
		// 모든 곰팡이가 이동을 끝낸 후에 한 칸에 곰팡이가 두마리 이상일 때는 크기가 큰 곰팡이가 다른 곰팡이를 모두 잡아먹습니다.
		ArrayList<Integer> removeList = new ArrayList<>();
		for(int i : moldMap.keySet()) {
			Point temp = moldMap.get(i);
			
			if(visited[temp.x][temp.y]) continue;
			
			// 한 칸에 두 마리 이상의 곰팡이가 몰린 경우
			int biggestMoldKey = -1;
			if(board[temp.x][temp.y].size() > 1) {
				visited[temp.x][temp.y] = true;
				
				while(!board[temp.x][temp.y].isEmpty()) {
					Point p = board[temp.x][temp.y].poll();
					
					if(biggestMoldKey == -1) biggestMoldKey = p.key;
					else {
						removeList.add(p.key);
					}
				}
				
			}
			else if(board[temp.x][temp.y].size() == 1)	continue;
			// 가장 큰 곰팡이 board에 넣는다.
			if(biggestMoldKey > -1)
				board[temp.x][temp.y].add(moldMap.get(biggestMoldKey));
		}
		
		// 가장 큰 곰팡이를제외하고 기존 moldMap에서 삭제한다.
		for(int i: removeList) {
			moldMap.remove(i);
		}
	}

	private static void get_mold() {
		
		for(int i=0;i<N;i++) {
			if(board[i][tempY].size() > 0) {
				Point p = board[i][tempY].poll();
				result += p.b;
				moldMap.remove(p.key);
				break;
			}
		}
	}
	
	private static int change_dir(int dir) {
		if(dir == 1) return 2;
		else if(dir == 2) return 1;
		else if(dir == 3) return 4;
		else return 3;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0|| r>=N || c>=M) return false;
		return true;
	}

}
