package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_bj_25757_임스와_함께하는_미니게임 {
	
	static int N,result;
	static HashSet<String> playerSet;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		String gameType = st.nextToken();
		
		playerSet = new HashSet<>();
		for(int i=0;i<N;i++) {
			String name = br.readLine();
			playerSet.add(name);
		}
		
		if(gameType.equals("Y")) {
			// 윷놀이 : 2명
			result = playerSet.size();
		}
		else if(gameType.equals("F")) {
			// 같은 그림 찾기 : 3명
			result = playerSet.size() / 2;
		}
		else {
			// 원카드 : 4명
			result = playerSet.size() / 3;
		}
		
		System.out.println(result);
	}

}
