package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_11650_좌표_정렬하기 {
	
	static int N;
	static class Point implements Comparable<Point>{
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int compareTo(Point o) {
			if(this.x == o.x)
				return this.y - o.y;
			return this.x - o.x;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		PriorityQueue<Point> pq = new PriorityQueue<>();
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			pq.add(new Point(x,y));
		} // input end
		
		while(!pq.isEmpty()) {
			Point temp = pq.poll();
			sb.append(temp.x+" "+temp.y+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
