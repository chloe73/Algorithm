package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1395_스위치 {
	
	static int N;
	static int[] seg, lazy;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =  new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		// 단, 초기에는 모든 스위치의 상태는 꺼져있는 상태로 되어있다.
		seg = new int[N*4];
		lazy = new int[N*4];
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int cmd = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			
			if(cmd == 0) {
				// a번 스위치부터 b번 스위치까지 스위치 상태를 반전시키는 일
				update(1, 0, N-1, a, b);
			}
			else {
				// a번 스위치부터 b번 스위치까지 중 켜져 있는 상태의 스위치 개수를 묻는 일
				// query
				sb.append(query(1,0,N-1,a,b)+"\n");
			}
		} // input end
		
		System.out.println(sb.toString());
	}
	
	private static void update(int node, int start, int end, int queryLeft, int queryRight) {
		propagate(node, start, end);
		
		if(queryRight < start || end < queryLeft) return;
		
		if(queryLeft <= start && end <= queryRight) {
			lazy[node] = 1;
			propagate(node, start, end);
			return;
		}
		
		int mid = (start + end) / 2;
		
		update(node*2, start, mid, queryLeft, queryRight);
		update(node*2+1, mid+1, end, queryLeft, queryRight);
		
		seg[node] = seg[node*2] + seg[node*2+1];
	}
	
	private static int query(int node, int start, int end, int queryLeft, int queryRight) {
		propagate(node, start, end);
		
		if(queryRight < start || end < queryLeft) return 0;
		
		if(queryLeft <= start && end <= queryRight) {
			return seg[node];
		}
		
		int mid = (start + end) / 2;
		return query(node*2, start, mid, queryLeft, queryRight) + query(node*2+1, mid+1, end, queryLeft, queryRight);
	}
	
	private static void propagate(int node, int start, int end) {
		if(lazy[node] % 2 == 1) {
			if(start != end) {
				lazy[node*2] += lazy[node];
				lazy[node*2+1] += lazy[node];
			}
			seg[node]  = (end-start+1) - seg[node];
			lazy[node] = 0;
		}
	}
}
