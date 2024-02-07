package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_10999_구간_합_구하기_2 {
	
	static int N;
	static long[] arr, tree, lazy;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		arr = new long[N];
		for(int i=0;i<N;i++) {
			arr[i] = Long.parseLong(br.readLine());
		}
		
		tree = new long[N*4];
		lazy = new long[N*4];
		
		init(1, 0, N-1);
		
		for(int i=0;i<M+K;i++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			
			if(cmd == 1) {
				int a = Integer.parseInt(st.nextToken())-1;
				int b = Integer.parseInt(st.nextToken())-1;
				long c = Long.parseLong(st.nextToken());
				
				// 구간 a~b까지 숫자 c 더하기
				update(1, 0, N-1, a, b, c);
			}
			else {
				int a = Integer.parseInt(st.nextToken())-1;
				int b = Integer.parseInt(st.nextToken())-1;
				
				// 구간 a~b까지 숫자 합 출력
				bw.write(querySum(1, 0, N-1, a, b)+"\n");
			}
			
		}

		bw.flush();
		br.close();
		bw.close();
	}
	
	static long init(int node, int start, int end) {
		if(start == end) {
			return tree[node] = arr[start];
		}
		
		int mid = (start + end) / 2;
		
		return tree[node] = init(node*2, start, mid) + init(node*2+1, mid+1, end);
	}
	
	static void propagate(int node, int start, int end) {
		if(lazy[node] != 0) {
			if(start != end) {
				lazy[node*2] += lazy[node];
				lazy[node*2+1] += lazy[node];
			}
			tree[node] += lazy[node] * (end-start+1);
			lazy[node] = 0;
		}
	}

	static void update(int node, int start, int end, int queryLeft, int queryRight, long dif) {
		propagate(node, start, end);
		
		if(queryRight < start || end < queryLeft ) return;
		
		if(queryLeft <= start && end <= queryRight) {
			lazy[node] = dif;
			propagate(node, start, end);
			return;
		}
		
		int mid = (start + end) / 2;
		
		update(node*2, start, mid, queryLeft, queryRight, dif);
		update(node*2+1, mid+1, end, queryLeft, queryRight, dif);
		
		tree[node] = tree[node*2] + tree[node*2+1];
	}
	
	static long querySum(int node, int start, int end, int queryLeft, int queryRight) {
		propagate(node, start, end);
		
		if(queryRight < start || end < queryLeft ) return 0;
		
		if(queryLeft <= start && end <= queryRight)
			return tree[node];
		
		int mid = (start + end) / 2;
		return querySum(node*2, start, mid, queryLeft, queryRight) + querySum(node*2+1, mid+1, end, queryLeft, queryRight);
	}
}
