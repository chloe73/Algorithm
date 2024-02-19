package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_1939_중량제한 {
	
	static int N,M;
	static ArrayList<Node>[] list;
	static class Node {
		int to, cost;
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}
		
		int left = 0;
		int right = 0;
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			list[a].add(new Node(b, c));
			list[b].add(new Node(a, c));
			
			right = Math.max(right, c);
		} // input end
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		int result = 0;
		while(left <= right) {
			int mid = (left + right) / 2;
			
			int ret = bfs(start,end,mid);
			
			if(ret == -1) {
				// 중량 줄이기
				right = mid-1;
			}
			else {
				left = mid+1;
				result = mid;
			}
		}
		
		System.out.println(result);
	}
	
	private static int bfs(int start, int end, int num) {
		Queue<Node> q = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
		visited[start] = true;
		q.add(new Node(start, 0));
		
		while(!q.isEmpty()) {
			Node temp = q.poll();
			
			if(temp.to == end) {
				return num;
			}
			
			for(Node next : list[temp.to]) {
				if(!visited[next.to] && next.cost >= num) {
					visited[next.to] = true;
					q.add(new Node(next.to, next.cost));
				}
			}
		}
		
		
		return -1;
	}

}
