package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main_bj_16916_부분_문자열 {
	
	static String S,P;
	static int N,M;
	static int[] pi;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		S = br.readLine();
		P = br.readLine();
		
		N = S.length();
		M = P.length();
		
		pi = new int[M];
		
		getPi();
		
		bw.write(kmp()+"");
		
		bw.flush();
		br.close();
		bw.close();
	}

	private static int kmp() {
		int index = 0;
		
		for(int i=0;i<N;i++) {
			while(index > 0 && S.charAt(i) != P.charAt(index)) {
				index = pi[index-1];
			}
			
			if(S.charAt(i) == P.charAt(index)) {
				if(index == M-1) {
					return 1;
				}
				else index++;
			}
		}
		
		return 0;
	}

	private static void getPi() {
		int index = 0;
		
		for(int i=1;i<M;i++) {
			while(index > 0 && P.charAt(i) != P.charAt(index)) {
				index = pi[index-1];
			}
			
			if(P.charAt(i) == P.charAt(index)) {
				index++;
				pi[i] = index;
			}
		}
	}

}
