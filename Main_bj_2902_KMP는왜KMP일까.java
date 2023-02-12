package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_2902_KMP는왜KMP일까 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		
		// 첫번째 문자열은 무조건 대문자이니까 result에 더함
		String result = input.charAt(0)+"";
		
		boolean flag = false;
		// 1번째 인덱스부터 마지막
		for(int i=1;i<input.length();i++) {
			char tmp = input.charAt(i); // 현재 탐색하는 문자열
			
			if(flag) result += tmp+"";
			
			if(tmp == '-') flag = true;
			else flag = false;
		}
		
		System.out.println(result);
	}

}
