package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_20437_문자열게임2 {
	
	static int T,K;
	static String W;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=0;i<T;i++) {
			// 2줄 동안 문자열 W와 정수 K가 주어진다.
			W = br.readLine();
			K = Integer.parseInt(br.readLine());
			
			if(K == 1) {
				sb.append(1+" "+1).append("\n");
				continue;
			}
			
			// 문자열 W의 알파벳 개수 체크
			int[] alpha = new int[26];
			for(int j=0;j<W.length();j++) {
				alpha[W.charAt(j)-'a']++;
			}
			
			int min = Integer.MAX_VALUE;
			int max = -1;
			
			for(int j=0;j<W.length();j++) {
				if(alpha[W.charAt(j)-'a'] < K) continue;
				
				int count = 1;
				// 슬라이딩 윈도우 알고리즘
				for(int l = j+1;l<W.length();l++) {
					if(W.charAt(j) == W.charAt(l)) count++;
					if(count == K) {
						min = Math.min(min, l-j+1);
						max = Math.max(max, l-j+1);
						break;
					}
				}
			}
			if(min == Integer.MAX_VALUE || max == -1) sb.append(-1+"\n");
			else sb.append(min+" "+max+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
