package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1167_트리의_지름 {
	
	static int V,maxIdx,result;
	static boolean[] visited;
	static ArrayList<Node>[] tree;
	static class Node{
		int to, dist;
		public Node(int to, int dist) {
			this.to = to;
			this.dist = dist;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		V = Integer.parseInt(br.readLine());
		
		tree = new ArrayList[V+1];
		for(int i=0;i<=V;i++) {
			tree[i] = new ArrayList<>();
		}
		
		for(int i=1;i<=V;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int node = Integer.parseInt(st.nextToken());
			
			outer:while(true) {
				for(int j=0;j<2;j++) {
					int to = Integer.parseInt(st.nextToken());
					if(to == -1) break outer;
					int dist = Integer.parseInt(st.nextToken());
					tree[node].add(new Node(to, dist));
				}
			}
		} // input end

		result = Integer.MIN_VALUE;
		
		visited = new boolean[V+1];
		solve(1,0);
		
		visited = new boolean[V+1];
		solve(maxIdx,0);
		
		bw.write(result+"");
		bw.flush();
		br.close();
		bw.close();
	}

	private static void solve(int idx,  int distance) {
		
		visited[idx] = true;
		
		if(result < distance) {
			result = distance;
			maxIdx = idx;
		}
		
		for(Node next : tree[idx]) {
			if(visited[next.to]) continue;
			
			solve(next.to, distance+next.dist);
		}
	}

}
