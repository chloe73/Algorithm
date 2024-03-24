package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_bj_1969_DNA {

	static class Point implements Comparable<Point>{
		char c;
		int cnt;
		public Point(char c, int cnt) {
			this.c = c;
			this.cnt = cnt;
		}
		public int compareTo(Point o) {
			if(this.cnt == o.cnt)
				return this.c - o.c;
			return o.cnt - this.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		String s = "";
		char[][] arr = new char[N][M];
		for(int i=0;i<N;i++) {
			arr[i] = br.readLine().toCharArray();
		} // input end
		
		int result = 0;
		for(int j=0;j<M;j++) {
			TreeMap<Character, Integer> treeMap = new TreeMap<>();
			
			for(int i=0;i<N;i++) {
				if(!treeMap.containsKey(arr[i][j])) {
					treeMap.put(arr[i][j], 1);
				}
				else {
					treeMap.put(arr[i][j], treeMap.get(arr[i][j])+1);
				}
			}
			
			PriorityQueue<Point> pq = new PriorityQueue<>();
			for(char c : treeMap.keySet()){
				pq.add(new Point(c, treeMap.get(c)));
			}
			
			char target = pq.poll().c;
			s += target;
			for(int i=0;i<N;i++) {
				if(arr[i][j] != target) result++;
			}
		}
		
		sb.append(s+"\n");
		sb.append(result);
		
		System.out.println(sb.toString());
	}

}
