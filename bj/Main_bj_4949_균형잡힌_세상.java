package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_bj_4949_균형잡힌_세상 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			String input = br.readLine();
			
			if(input.equals(".")) break;
			
			sb.append(solve(input)+"\n");
		}
		
		System.out.println(sb.toString());
	}
	
	private static String solve(String s) {
		
		Stack<Character> stack = new Stack<>();
		boolean flag = true;
		
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i) == ' ') continue;
			
			if(s.charAt(i) == '(') {
				stack.push(s.charAt(i));
				continue;
			}
			
			if(s.charAt(i) == ')') {
				if(!stack.isEmpty() && stack.peek() == '(') {
					stack.pop();
					continue;
				}
				if(stack.empty() || stack.peek() != '(') {
					flag = false;
					break;
				}
				continue;
			}
			
			if(s.charAt(i) == '[') {
				stack.push(s.charAt(i));
				continue;
			}
			
			if(s.charAt(i) == ']') {
				if(!stack.empty() && stack.peek() == '[') {
					stack.pop();
					continue;
				}
				if(stack.empty() || stack.peek() != '[') {
					flag = false;
					break;
				}
				continue;
			}
		}
		
		if(!stack.empty()) return "no";
		
		if(flag) {
			return "yes";
		}

		return "no";
		
	}

}
