package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main_bj_11656_접미사_배열 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String input = br.readLine();
		
		ArrayList<String> list = new ArrayList<>();
		
		for(int i=0;i<input.length();i++) {
			if(i == 0) {
				list.add(input);
			}
			else {
				String temp = input.substring(i);
				list.add(temp);
			}
		}

		Collections.sort(list);
		for(String s : list) {
			sb.append(s+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
