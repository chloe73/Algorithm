package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_bj_19948_음유시인영재 {
	
	static int[] alphabet;
	static ArrayList<String> words;
	static String input, result;
	static int spacebar;
	static StringTokenizer st;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] input = br.readLine().toCharArray();
		
		char temp = input[0];
		
		Map<Character, Integer> map = new HashMap<>();
		
		// 스페이스바 개수 입력 받고 map에 저장
		map.put(' ', Integer.parseInt(br.readLine()));
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<26;i++) {
			map.put((char) ('a'+i), Integer.parseInt(st.nextToken()));
		}
		
		
	}

}
