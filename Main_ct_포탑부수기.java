package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct_포탑부수기 {
	
	static int N,M,K,result;
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static Point[][] board;
	static PriorityQueue<Weak> weakestpq;
	static class Strong implements Comparable<Strong> {
		int x,y,power,time;
		int sum;
		public Strong(int x, int y, int sum, int power, int time) {
			this.x = x;
			this.y = y;
			this.sum = sum;
			this.power = power;
			this.time = time;
		}
		@Override
		public int compareTo(Strong o) { // 공격자 선정
			if(o.power == this.power) {
				if(o.time == this.time) {
					if(this.sum == o.sum) {
						
					}
					return this.sum - o.sum; // 행과 열의 합이 가장 작은 포탑이 가장 강한 포탑
				}
				return o.time - this.time; // 공격한지 가장 오래된 포탑이 가장 강한 포탑
			}
			return o.power - this.power; // 공격력이 가장 높은 포탑이 가장 강한 포탑
		}
	}
	static class Weak implements Comparable<Weak> {
		int x,y,power,time;
		int sum;
		public Weak(int x, int y, int sum, int power, int time) {
			this.x = x;
			this.y = y;
			this.sum = sum;
			this.power = power;
			this.time = time;
		}
		@Override
		public int compareTo(Weak o) { // 공격자 선정
			if(this.power == o.power) {
				if(this.time == o.time) {
					if(o.sum == this.sum) {
						return o.y - this.y; // 열 값이 가장 큰 포탑이 가장 약한 포탑
					}
					return o.sum - this.sum; // 행과 열의 합이 가장 큰 포탑이 가장 약한 포탑
				}
				return this.time - o.time; // 가장 최근에 공격한 포탑이 가장 약한 포탑
			}
			return this.power - o.power; // 공격력이 가장 낮은 포탑이 가장 약한 포탑
		}
	}
	static class Point {
		int x,y,power,time;
		int sum;
		public Point(int x, int y, int sum, int power, int time) {
			this.x = x;
			this.y = y;
			this.sum = sum;
			this.power = power;
			this.time = time;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new Point[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int p = Integer.parseInt(st.nextToken());
				board[i][j] = new Point(i, j, i+j, p, Integer.MAX_VALUE);
				weakestpq.add(new Weak(i, j, i+j, p, Integer.MAX_VALUE));
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		// TODO Auto-generated method stub
		
	}

}