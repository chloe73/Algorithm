package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_14938_서강그라운드 {
	
	static int N,M,R,result;
	static ArrayList<Point>[] graph;
	static int[] item;
	static class Point implements Comparable<Point> {
		int num, dist;
		public Point(int num, int dist) {
			this.num = num;
			this.dist = dist;
		}
		public int compareTo(Point o) {
			return this.dist - o.dist;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		item = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
			item[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0;i<R;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			
			graph[a].add(new Point(b, l));
			graph[b].add(new Point(a, l));
		} // input end
		
		result = 0;
		for(int i=1;i<=N;i++)
			result = Math.max(result, solve(i));
		
		System.out.println(result);
	}

	private static int solve(int start) {
		int ret = 0;
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(start, 0));
		int[] d = new int[N+1];
		Arrays.fill(d, Integer.MAX_VALUE);
		d[start] = 0;
		
		while(!pq.isEmpty()) {
			Point temp = pq.poll();
			
			for(Point next : graph[temp.num]) {
				if(d[next.num] > temp.dist + next.dist) {
//					ret += item[next.num];
					d[next.num] = temp.dist + next.dist;
					pq.add(new Point(next.num, d[next.num]));
				}
			}
		}
		
		for(int i=1;i<=N;i++) {
			if(d[i] <= M) {
				ret += item[i];
			}
		}
		
		return ret;
	}

}
