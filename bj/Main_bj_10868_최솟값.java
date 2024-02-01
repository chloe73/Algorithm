package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_10868_최솟값 {
	
	static int N;
	static int[] arr, minTree;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		arr = new int[N+1];
		minTree = new int[getTreeSize()];
		for(int i=1;i<=N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		init(1,1,N);
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if(a>b) {
				int temp = b;
				b = a;
				a = temp;
			}
			
			int minResult = Integer.MAX_VALUE;
			minResult = Math.min(minResult, queryMin(1, 1, N, a, b));
			
			sb.append(minResult+"\n");
		} // input end
		
		bw.write(sb.toString());
		bw.flush();
		br.close();
		bw.close();
	}
	
	private static int getTreeSize() {
		int h = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
		return (int) Math.pow(2, h);
	}
	
	private static void init(int node, int nodeLeft, int nodeRight) {
		if(nodeLeft == nodeRight) {
			minTree[node] = arr[nodeLeft];
			return;
		}
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		init(node*2, nodeLeft, mid);
		init(node*2+1, mid+1, nodeRight);
		
		minTree[node] = Math.min(minTree[node*2], minTree[node*2+1]);
	}

	private static int queryMin	(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
		if(queryRight < nodeLeft || queryLeft > nodeRight) return Integer.MAX_VALUE;
		
		if(queryLeft <= nodeLeft && nodeRight <= queryRight) return minTree[node];
		
		int mid = (nodeLeft + nodeRight) / 2;
		
		int leftMin = queryMin(node*2, nodeLeft, mid, queryLeft, queryRight);
		int rightMin = queryMin(node*2+1, mid+1, nodeRight, queryLeft, queryRight);
		
		return Math.min(leftMin, rightMin);
	}
}
