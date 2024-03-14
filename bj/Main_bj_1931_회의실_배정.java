package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1931_회의실_배정 {
	
	static int N,result;
	static ArrayList<int[]> list;
	static PriorityQueue<Point> pq;
	static class Point implements Comparable<Point>{
		int start,end;
		public Point(int start, int end) {
			this.start = start;
			this.end =end;
		}
		public int compareTo(Point o) {
			if(this.end == o.end)
				return this.start - o.start;
			return this.end - o.end;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			list.add(new int[] {start,end});
			pq.add(new Point(start, end));
		} // input end
		
		Collections.sort(list, (o1,o2) -> {
			if(o1[1] == o2[1])
				return o1[0]-o2[0];
			return o1[1]-o2[1];
		});
		
		int cnt = 1;
		int temp = pq.poll().end;
		
		while(!pq.isEmpty()) {
			Point next = pq.poll();
			
			if(temp <= next.start) {
				cnt++;
				temp = next.end;
			}
		}
		
		System.out.println(cnt);
	}

}
