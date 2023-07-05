package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_2042_구간_합_구하기 {
	
	static int N,M,K;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		int depth = 0;
		
		while(Math.pow(2, depth) < N) {
			depth++;
		}
		
		depth++;
		
		int arraySize = (int) Math.pow(2, depth);
		int startIndex = (int)Math.pow(2, depth-1);
		long[] tree = new long[arraySize];
		
		for(int i=0;i<N;i++) {
			long in = Long.parseLong(br.readLine());
			tree[startIndex+i] = in;
		}
		
		// 초기 트리에 자식들의 합 채우기
		for(int i=startIndex-1;i>=1;i--) {
			tree[i] = tree[i*2] + tree[i*2+1];
		}
		
		for(int i=0;i<M+K;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			long c = Long.parseLong(st.nextToken());
			
			// b번째 수를 c로 변경
			if(a == 1) {
				int targetIndex = (int) (startIndex+b-1);
				long diff = c - tree[targetIndex];
				while(targetIndex > 0) {
					tree[targetIndex] += diff;
					targetIndex /= 2;
				}
			}
			// b부터 c까지의 합
			else {
				int s = (int)b + startIndex -1;
				int e = (int)c + startIndex -1;
				
				long answer = 0;
				
				while(s <= e) {
					if(s % 2 == 1) answer += tree[s];
					if(e % 2 == 0) answer += tree[e];
					s = (s+1) / 2;
					e = (e-1) / 2;
				}
				
				sb.append(answer+"\n");
			}
		}
		
		System.out.println(sb.toString());
	}

}
