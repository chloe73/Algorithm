package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_2357_최솟값과_최댓값 {
	
	static int N,M;
	static int minResult, maxResult;
	static int[] arr, maxTree, minTree;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N+1];
		int size = getTreeSize();
		minTree = new int[size];
		maxTree = new int[size];
		for(int i=1;i<=N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		init(1, 1, N);

		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			minResult = Integer.MAX_VALUE;
			maxResult = Integer.MIN_VALUE;
			
			if(a>b) {
				int temp = b;
				b = a;
				a = temp;
			}
			
			minResult = Math.min(minResult, queryMin(1, 1, N, a, b));
			maxResult = Math.max(maxResult, queryMax(1, 1, N, a, b));
			
			sb.append(minResult+" "+maxResult+"\n");
		}
		
		// M개의 줄에 입력받은 순서대로 각 a, b에 대한 답을 최솟값, 최댓값 순서로 출력한다.
		bw.write(sb.toString());
		bw.flush();
		br.close();
		bw.close();
	}
	
	private static int getTreeSize() {
		// ceil -> 올림
		int h = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
		return (int) Math.pow(2,h); // 2의 h제곱
	}
	
	private static void init(int node, int nodeLeft, int nodeRight) {
		if(nodeLeft == nodeRight) {
			maxTree[node] = arr[nodeLeft];
			minTree[node] = arr[nodeRight];
			return;
		}
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		init(node*2, nodeLeft, mid);
		init(node*2+1, mid+1, nodeRight);
		
		maxTree[node] = Math.max(maxTree[node*2], maxTree[node*2+1]);
		minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}

	private static int queryMax(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		
		if(queryRight < nodeLeft || queryLeft > nodeRight) return 0;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return maxTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		int leftMax = queryMax(node*2, nodeLeft, mid, queryLeft, queryRight);
		int rightMax = queryMax(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return Math.max(leftMax, rightMax);
	}
	
	private static int queryMin(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		
		if(queryRight < nodeLeft || queryLeft > nodeRight) return Integer.MAX_VALUE;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return minTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		int leftMin = queryMin(node*2, nodeLeft, mid, queryLeft, queryRight);
		int rightMin = queryMin(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return Math.min(leftMin, rightMin);
	}
}
