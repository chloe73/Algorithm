package algo.SW_DX.day9_Segment_Tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_Segment_Tree_연습_1 {
	
	static int N;
	static int[] arr, maxTree, minTree;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			arr = new int[N];
			maxTree = new int[N*4];
			minTree = new int[N*4];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			init(1, 0, N-1);
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int query = Integer.parseInt(st.nextToken());
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				
				if(query == 0) {
					update(1, 0, N-1, left, right);
				}
				else if(query == 1) {
					int max = queryMax(1, 0, N-1, left, right-1);
					int min = queryMin(1, 0, N-1, left, right-1);
					bw.write(max-min+" ");
				}
			}
			bw.write("\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void init(int node, int nodeLeft, int nodeRight) {
		// 기저 조건
		if(nodeLeft == nodeRight) {
			maxTree[node] = arr[nodeLeft];
			minTree[node] = arr[nodeRight];
			return;
		}
		
		// 트리를 반으로 나눠 세그먼트 트리 형성
		int mid = (nodeLeft + nodeRight) / 2;
		
		init(node*2, nodeLeft, mid);
		init(node*2+1, mid+1,nodeRight);
		
		maxTree[node] = Math.max(maxTree[node*2], maxTree[node*2+1]);
		minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}

	private static void update(int node, int nodeLeft, int nodeRight, int queryIndex, int value) {
		if(queryIndex < nodeLeft || nodeRight < queryIndex) return;
		
		if(nodeLeft == nodeRight) {
			maxTree[node] = value;
			minTree[node] = value;
			return;
		}
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		update(node*2, nodeLeft, mid, queryIndex, value);
		update(node*2+1, mid+1, nodeRight, queryIndex, value);
		
		maxTree[node] = Math.max(maxTree[node*2], maxTree[node*2+1]);
		minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}

	private static int queryMax(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		if(queryRight < nodeLeft || nodeRight < queryLeft) return 0;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return maxTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		int leftMax = queryMax(node*2, nodeLeft, mid, queryLeft, queryRight);
		int rightMax = queryMax(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return Math.max(leftMax, rightMax);
	}
	
	private static int queryMin(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		if(queryLeft > nodeRight || queryRight < nodeLeft) return Integer.MAX_VALUE;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return minTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		int leftMin = queryMin(node*2, nodeLeft, mid, queryLeft, queryRight);
		int rightMin = queryMin(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return Math.min(leftMin, rightMin);
	}
}
