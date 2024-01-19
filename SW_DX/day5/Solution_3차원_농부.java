package algo.SW_DX.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_3차원_농부 {

	static int N,M;
	static int c1,c2;
	static int[] cow, horse;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			c1 = Integer.parseInt(st.nextToken());
			c2 = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			cow = new int[N];
			for(int i=0;i<N;i++) {
				cow[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.sort(cow); // 이분 탐색을 위한 정렬
			
			horse = new int[M];
			for(int i=0;i<M;i++) {
				horse[i] = Integer.parseInt(st.nextToken());
				
				// 이분 탐색 수행
				
			}
			
			
		}

	}

}
