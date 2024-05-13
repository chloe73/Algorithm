package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_17219 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		HashMap<String, String> map = new HashMap<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String add = st.nextToken();
			String pwd = st.nextToken();
			map.put(add, pwd);
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<M;i++) {
			String input = br.readLine();
			String pwd = map.get(input);
			sb.append(pwd+"\n");
		}

		System.out.println(sb.toString());
	}

}
