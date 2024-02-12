package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_11505_구간_곱_구하기 {
	
	static int N;
	static int[] arr;
	static long[] seg;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// M은 수의 변경이 일어나는 횟수이고, K는 구간의 곱을 구하는 횟수이다. 
		// 그리고 둘째 줄부터 N+1번째 줄까지 N개의 수가 주어진다. 
		arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		seg = new long[4*N];
		init(1, 0, N-1);
		
		for(int i=0;i<M+K;i++) {
			// 그리고 N+2번째 줄부터 N+M+K+1 번째 줄까지 세 개의 정수 a,b,c가 주어지는데, 
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			if(a == 1) {
				// a가 1인 경우 b번째 수를 c로 바꾸고 
				 update(1, 0, N-1, b-1, c);
			}
			else {
				// a가 2인 경우에는 b부터 c까지의 곱을 구하여 출력하면 된다.
				// 구간의 곱을 1,000,000,007로 나눈 나머지를 출력한다.
				 long result = query(1, 0, N-1, b-1, c-1);
				 // result = result % 1000000007;
				 bw.write(result+"\n");
			}
		} // input end

		bw.flush();
		br.close();
		bw.close();
	}

	private static void init(int node, int start, int end) {
		if(start == end) {
			seg[node] = arr[start];
			return;
		}
		
		int mid = (start + end) / 2;
		
		init(node*2, start, mid);
		init(node*2+1, mid+1, end);
		
		seg[node] = seg[node*2] * seg[node*2+1] % 1000000007;
	}
	
	private static void update(int node, int start, int end, int queryIndex, int value) {
		if(queryIndex < start || queryIndex > end) return;
		
		if(start == end) {
			seg[node] = value;
			return;
		}
		
		int mid = (start + end) / 2;
		
		update(node*2, start, mid, queryIndex, value);
		update(node*2+1, mid+1, end, queryIndex, value);
		
		seg[node] = seg[node*2] * seg[node*2+1] % 1000000007;
	}
	
	private static long query(int node, int start, int end, int queryLeft, int queryRight) {
		if(queryLeft > end || queryRight < start) return 1;
		
		if(queryLeft <= start && end <= queryRight) return seg[node];
		
		int mid = (start + end) / 2;
		
		long left = query(node*2, start, mid, queryLeft, queryRight);
		long right = query(node*2+1, mid+1, end, queryLeft, queryRight);
		
		return left * right % 1000000007;
	}
}
