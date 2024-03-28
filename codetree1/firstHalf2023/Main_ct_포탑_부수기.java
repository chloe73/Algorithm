package algo.codetree1.firstHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_포탑_부수기 {
	
	static int N,M,K;
	static Top[][] board;
	static boolean[][] visited; // 해당 턴에 공격자 및 공격받은 포탑 체크
	// 우/하/좌/상의 우선순위대로 먼저 움직인 경로가 선택됩니다.
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static PriorityQueue<Top> pq;
	static class Top implements Comparable<Top>{
		int x,y,p,time,xySum;
		public Top(int x, int y, int p) {
			this.x = x;
			this.y = y;
			this.p = p;
			this.time = 0;
			this.xySum = x + y;
		}
		public int compareTo(Top o) {
			return this.p - o.p;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		board = new Top[N][M];
		pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int p = Integer.parseInt(st.nextToken());
				board[i][j] = new Top(i, j, p);
				if(board[i][j].p > 0) {
					pq.add(board[i][j]);
				}
			}
		} // input end
		
		solve();
		
		// 첫 번째 줄에 K번의 턴이 종료된 후 남아있는 포탑 중 가장 강한 포탑의 공격력을 출력합니다.
		int result = 0;
		while(!pq.isEmpty()) {
			result = pq.poll().p;
		}
		System.out.println(result);
	}


	private static void solve() {
		while(K-- > 0) {
			
		}
	}

}
