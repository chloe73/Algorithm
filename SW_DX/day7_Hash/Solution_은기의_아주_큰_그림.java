package algo.SW_DX.day7_Hash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_은기의_아주_큰_그림 {
	
	static long MOD = 1000000007;
	static int MAX_SIZE = 2000;
	static int H,W,N,M;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static long[][] row_hash = new long[MAX_SIZE][MAX_SIZE]; // 가로줄 해싱
	static long[][] hash = new long[MAX_SIZE][MAX_SIZE]; // 최종 해싱

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			for(int i=0;i<H;i++) {
				String temp = br.readLine();
				rowHashFind(temp, W, i);
			}
			colHashFind(H, W, H, W);
			long cmp = hash[0][0];

			for(int i=0;i<N;i++) {
				String temp = br.readLine();
				rowHashFind(temp, W, i);
			}
			colHashFind(H, W, N, M);
			
			long result = 0;
			for(int i=0;i<=N-H;i++) {
				for(int j=0;j<=M-W;j++) {
					if(hash[i][j] == cmp) result++;
				}
			}
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void rowHashFind(String s, int m, int line) {
		int n = s.length();
		long power = 1;
		long hash_value = 0;
		
		for(int i=0;i<=n-m;i++) {
			if(i == 0) {
				for(int j=0;j<m;j++) {
					hash_value = mod(mod(hash_value * 37) + s.charAt(i+j));
					if(j != m-1) power = mod(power*37);
				}
			}
			else {
				hash_value = mod(mod(37*mod(hash_value-mod(s.charAt(i-1)*power))) + s.charAt(i+m-1));
			}
			
			row_hash[line][i] = hash_value;
		}
	}
	
	private static void colHashFind(int H, int W, int N, int M) {
		for(int i=0;i<=M-W;i++) {
			long hash_value = 0, power = 1;
			for(int k=0;k<=N-H;k++) {
				if(k == 0) {
					for(int t=0;t<H;t++) {
						hash_value = mod(hash_value + mod(row_hash[H-t-1][i] * power));
						if(t != H-1) power = mod(power * 5831);
					}
				}
				else {
					hash_value = mod(mod(5831*mod((hash_value-mod(row_hash[k-1][i] * power)))) + row_hash[k+H-1][i]);
				}
				
				hash[k][i] = hash_value;
			}
		}
	}
	
	private static long mod(long v) {
		if(v >= 0) return v % MOD;
		return ((-v / MOD + 1) * MOD + v) % MOD;
	}

}
