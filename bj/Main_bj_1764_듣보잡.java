package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_bj_1764_듣보잡 {
	
	static int N,M;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		HashSet<String> set = new HashSet<>();
		// HashSet의 contains()는 O(1), ArrayList의 contains()는 O(n)이다. 
		// HashSet은 map을 기반으로 구현되고, ArrayList는 indexOf()를 사용해서 contain여부를 결정된다.
		// 처음에 ArrayList를 사용해서 시간초과가 났는데, 효율성이 필요한 문제라면 HashSet을 사용해야겠다.
		for(int i=0;i<N;i++) {
			String input = br.readLine();
			set.add(input);
		}
		
		ArrayList<String> resultList = new ArrayList<>();
		for(int i=0;i<M;i++) {
			String input = br.readLine();
			
			if(set.contains(input)) {
				resultList.add(input);
			}
		}

		Collections.sort(resultList);
		
		sb.append(resultList.size()+"\n");
		for(String s : resultList) {
			sb.append(s+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
