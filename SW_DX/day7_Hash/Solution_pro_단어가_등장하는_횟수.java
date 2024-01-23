package algo.SW_DX.day7_Hash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// KMP 알고리즘 
public class Solution_pro_단어가_등장하는_횟수 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			String B = br.readLine();
			String S = br.readLine();
			
			int result = 0;
			
			int[] pi = new int[S.length()];
			int index = 0;
			
			// S 문자열의 접두사, 접미사, 경계를 이용해 일치하는 부분 찾기
			for(int i=1;i<S.length();i++) {
				while(index>0 && S.charAt(i) != S.charAt(index)) {
					index = pi[index-1];
				}
				
				if(S.charAt(i) == S.charAt(index)) {
					index++;
					pi[i] = index;
				}
			}
			
			// 전체 문자열 확인 -> pi 데이터를 활용해 확인
			index = 0;
			for(int i=0;i<B.length();i++) {
				while(index>0 && B.charAt(i) != S.charAt(index)) {
					index = pi[index-1];
				}
				
				if(B.charAt(i) == S.charAt(index)) {
					if(index == S.length()-1) {
						result++;
						index = pi[index];
					}
					else index++;
				}
			}
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}
