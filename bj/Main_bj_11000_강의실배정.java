package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_11000_강의실배정 {
	
	static int N;
	static ArrayList<lesson> arr;
	static PriorityQueue<Integer> pq;
	static PriorityQueue<lesson> qq;
	static class lesson implements Comparable<lesson>{
		int start, end;
		
		public lesson(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(lesson o) {
			if(this.start == o.start)
				return this.end - o.end;
			return this.start - o.start;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		arr = new ArrayList<lesson>();
		pq = new PriorityQueue<>();
		qq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			arr.add(new lesson(x, y));
			qq.add(new lesson(x, y));
		} // input end
		
		Collections.sort(arr);
		
		solve();
		
		System.out.println(pq.size());
	}

	private static void solve() {
		
		pq.add(arr.get(0).end);
		for(int i=1;i<N;i++) {
			lesson next = arr.get(i);
			
			if(next.start < pq.peek())
				pq.add(next.end);
			else {
				pq.poll();
				pq.add(next.end);
			}
		}
	}

}
