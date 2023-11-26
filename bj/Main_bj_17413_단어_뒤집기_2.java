package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_bj_17413_단어_뒤집기_2 {
	
	static String input;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		input = br.readLine();
		
		check();
		
		System.out.println(sb.toString());
	}
	
	private static void check() {
		
		boolean tag = false;
		Stack<Character> stack = new Stack<>();
		
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i) == '<') {
				// 이전까지의 단어 stack이 쌓여있는지 확인
				if(stack.size() > 0) {
					while(!stack.isEmpty()) {
						sb.append(stack.pop());
					}
				}
				tag = true;
				sb.append(input.charAt(i));
				continue;
			}
			if(input.charAt(i) == '>') {
				tag = false;
				sb.append(input.charAt(i));
				continue;
			}
			if(tag) {
				sb.append(input.charAt(i));
				continue;
			}
			if(!tag && input.charAt(i) != ' ') {
				stack.add(input.charAt(i));
				continue;
			}
			if(input.charAt(i) == ' ') {
				if(stack.size() > 0) {
					while(!stack.isEmpty()) {
						sb.append(stack.pop());
					}
				}
				sb.append(" ");
				continue;
			}
			stack.push(input.charAt(i));
		}
		
		if(stack.size() > 0) {
			while(!stack.isEmpty()) {
				sb.append(stack.pop());
			}
		}
	}

}
