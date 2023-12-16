package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1446_지름길 {
	
	static int N,D;
	static int[] dist;
	static Point[] graph;
	static class Point implements Comparable<Point> {
		int start, end, d;
		public Point(int start, int end, int d) {
			this.start = start;
			this.end = end;
			this.d = d;
		}
		
		public int compareTo(Point o) {
			return this.d - o.d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());

		dist = new int[D+1];
		for(int i=0;i<=D;i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		
		graph = new Point[N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int d= Integer.parseInt(st.nextToken());
			
			graph[i] = new Point(start, end, d);
		} // input end
		
		dijkstra(0);
		
		System.out.println(dist[D]);
	}

	private static void dijkstra(int start) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		
		pq.add(new Point(start, 0, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			Point temp = pq.poll();
			int destination = temp.end;
			boolean flag = false;
			
			for(Point p : graph) {
				
				if(p.start >= destination) {
					if(p.end > D) continue;
					
					int tmp = p.start - destination;
					flag = true;
					if(dist[p.end] > dist[destination]+p.d+tmp) {
						dist[p.end] = dist[destination] + p.d + tmp;
						pq.add(new Point(destination, p.end, dist[p.end]));
					}
				}
			}
			
			dist[D] = Math.min(dist[D], dist[destination]+D-destination);
		}
		
	}

}
