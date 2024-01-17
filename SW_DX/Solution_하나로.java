package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 최소 신장 트리 - 프림 알고리즘
public class Solution_하나로 {
	
	static int N;
	static long result;
	static int[] X,Y;
	static double E;
	static class Node implements Comparable<Node>{
		int to;
		long cost;
		
		Node(int to, long cost){
			this.to = to;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return Long.compare(this.cost, o.cost);
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			N = Integer.parseInt(br.readLine());
			
			// 각 섬들의 정수인 X좌표
			StringTokenizer st = new StringTokenizer(br.readLine());
			X = new int[N];
			for(int i=0;i<N;i++) {
				X[i] = Integer.parseInt(st.nextToken());
			}
			
			// 각 섬들의 정수인 Y좌표
			Y = new int[N];
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				Y[i] = Integer.parseInt(st.nextToken());
			}
			
			// 해저터널 건설의 환경 부담 세율 실수 E
			E = Double.parseDouble(br.readLine());
		
			result = 0;
			
			prim();
		
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void prim() {
		
		// 모든 간선의 비용 저장
		ArrayList<Node>[] list = new ArrayList[N];
		for(int i=0;i<N;i++) {
			list[i] = new ArrayList<>();
			
			for(int j=0;j<N;j++) {
				if(i == j) continue;
				
				list[i].add(new Node(j, get_dist(i, j)));
			}
		}
		
		int nodeCnt = 0;
		boolean[] visited = new boolean[N];
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(0, 0));
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(visited[temp.to]) continue;
			
			result += temp.cost;
			visited[temp.to] = true;
			
			// 모든 노드 확인 다 했으면 끝
			if(++nodeCnt == N) break;
			
			// 현재 node에서 연결된 노드 확인
			for(int i=0;i<list[temp.to].size();i++) {
				Node next = list[temp.to].get(i);
				if(!visited[next.to]) pq.add(next);
			}
		}
		
		result = Math.round(result * E);
	}

	// 해저 터널 길이 구하기
	private static long get_dist(int i, int j) {
		
		long xDist = Math.abs(X[i]-X[j]);
		long yDist = Math.abs(Y[i]-Y[j]);
		
		return xDist*xDist + yDist*yDist;
	}

}
