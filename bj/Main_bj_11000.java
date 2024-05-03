package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_11000 {
	
	static int N,result;
	static ArrayList<Class> cmd;
	static class Class implements Comparable<Class>{
		int start,end;
		public Class(int start, int end) {
			this.start = start;
			this.end = end;
		}
		public int compareTo(Class o) {
			if(this.start == o.start)
				return this.end-o.end;
			return this.start - o.start;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		cmd = new ArrayList<>();
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			cmd.add(new Class(start, end));
		} // input end
		
		Collections.sort(cmd);
		
		result = 0;
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		pq.add(cmd.get(0).end);
		
		for(int i=1;i<N;i++) {
			if(pq.peek() <= cmd.get(i).start) {
				pq.poll();
			}
			
			pq.add(cmd.get(i).end);
		}
		
		result = pq.size();
	}

}
