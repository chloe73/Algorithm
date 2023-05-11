package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1062_가르침 {
	
	static int N,K,result;
	static boolean[] visited;
	static String[] word;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		visited = new boolean[26];
		word = new String[N];
		
		// K가 5 이하인 경우 -> 단어 읽을 수 없음
		// 백트래킹 - 가지치기 1) 
		if(K < 5) {
			System.out.println(0);
			return;
		} else if(K == 26) {
			System.out.println(N);
			return;
		}
		
		visited['a'-'a'] = true;
		visited['n'-'a'] = true;
		visited['t'-'a'] = true;
		visited['i'-'a'] = true;
		visited['c'-'a'] = true;

		for(int i=0;i<N;i++) {
			String input = br.readLine().replaceAll("anta|tica", "");

			word[i] = input;
		} // input end
		
		result = 0;
		
		solve(0,0);
		
		System.out.println(result);
	}

	private static void solve(int alpha, int cnt) {
		
		if(cnt == K-5) {
			int temp = 0;
			
			for(int i=0;i<N;i++) {
				boolean flag = true;
				
				for(int j=0;j<word[i].length();j++) {
					if(!visited[word[i].charAt(j)-'a']) {
						flag = false;
						break;
					}
				}
				
				if(flag) temp++;
			}
			result = Math.max(temp, result);
			return;
		}
		
		for(int i=alpha;i<26;i++) {
			if(!visited[i]) {
				visited[i] = true;
				solve(i, cnt+1);
				visited[i] = false;
			}
		}
	}


}
