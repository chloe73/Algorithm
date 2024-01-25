package algo.SW_DX.day9_Segment_Tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_Segment_Tree_연습_2 {
	
	static int N;
	static int[] arr;
	static long[] sumTree;

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
			sumTree = new long[N*4];
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				if(i % 2 == 1) {
					arr[i] = -arr[i];
				}
			}
			
			 init(1,0,N-1);
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int query = Integer.parseInt(st.nextToken());
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				
				if(query == 0) {
					if(left % 2 == 1) {
						right = -right;
					}
					update(1, 0, N-1, left, right);
				}
				else if(query == 1) {
					long sum = querySum(1, 0, N-1, left, right-1);
					if(left % 2 == 1) {
						sum = -sum;
					}
					bw.write(sum+" ");
				}
			}
			
			bw.write("\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void init(int node, int nodeLeft, int nodeRight) {
		if(nodeLeft == nodeRight) {
			sumTree[node] = arr[nodeLeft];
			return;
		}
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		init(node*2, nodeLeft, mid);
		init(node*2+1, mid+1, nodeRight);
		
		sumTree[node] = sumTree[node*2] + sumTree[node*2 + 1];
	}
	
	private static void update(int node, int nodeLeft, int nodeRight, int queryIndex, int value) {
		
		if(queryIndex < nodeLeft || nodeRight < queryIndex) return;
		
		if(nodeLeft == nodeRight) {
			sumTree[node] = value;
			return;
		}
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		update(node*2, nodeLeft, mid, queryIndex, value);
		update(node*2+1, mid+1, nodeRight, queryIndex, value);
		
		sumTree[node] = sumTree[node*2] + sumTree[node*2+1];
	}
	
	private static long querySum(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		if(queryRight < nodeLeft || nodeRight < queryLeft) return 0;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return sumTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		long leftSum = querySum(node*2, nodeLeft, mid, queryLeft, queryRight);
		long rightSum = querySum(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return leftSum + rightSum;
	}

}
