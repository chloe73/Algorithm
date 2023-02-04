package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_14425_문자열집합 {
	
	static int N,M,result;
	static HashMap<String, Integer> stringArr = new HashMap<>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		for(int i=0;i<N;i++) {
			stringArr.put(br.readLine(),0);
		}

		for(int i=0;i<M;i++) {
			String input = br.readLine();
			
			if(stringArr.containsKey(input)) result++;
		} // input end
		
		System.out.println(result);
	}

}
