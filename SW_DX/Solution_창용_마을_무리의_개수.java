package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_창용_마을_무리의_개수 {
	
	static int N,M,result;
	static int[] parent, rank;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			parent = new int[N+1];
			rank = new int[N+1];
			// parent init
			for(int i=1;i<=N;i++) {
				parent[i] = i;
			}
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				union(a,b);
			} // input end
			
			result = 0;
			for(int i=1;i<=N;i++) {
				if(parent[i] == i) result++;
			}
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		if(A == B) return;
		
		if(A > B) 
			parent[A] = B;
		else
			parent[B] = A;
	}

	private static int find(int x) {
		if(x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}

}
