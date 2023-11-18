package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_13913_숨바꼭질_4 {
	
	static int N,K;
	static int[] visited = new int[100001];
	static int[] parent = new int[100001];

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		if(N == K) {
			System.out.println(0);
			System.out.println(N);
		}
		else {
			bfs();
			StringBuilder sb = new StringBuilder();
			sb.append(visited[K]+"\n");
			Stack<Integer> stack = new Stack<>();
			
			int temp = K;
			while(temp != N) {
				stack.push(temp);
				temp = parent[temp];
			}
			stack.push(N);
			
			while(!stack.isEmpty()) {
				sb.append(stack.pop()).append(" ");
			}
			System.out.println(sb.toString());
		}
	}

	private static void bfs() {
		Queue<Integer> q = new LinkedList<>();
		q.add(N);
		
		while(!q.isEmpty()) {
			int X = q.poll();
			
			if(X == K) {
				break;
			}
			
			if(X>0 && visited[X-1] == 0) {
				visited[X-1] = visited[X] + 1;
				parent[X-1] = X;
				q.add(X-1);
			}
			
			if(X+1 < visited.length && visited[X+1] == 0) {
				visited[X+1] = visited[X] + 1;
				parent[X+1] = X;
				q.add(X+1);
			}
			
			if(X*2 < visited.length && visited[X*2] == 0) {
				visited[X*2] = visited[X] + 1;
				parent[X*2] = X;
				q.add(X*2);
			}
		}
		
	}

}
