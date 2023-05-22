package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_bj_9012_괄호 {
	
	static int T;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=0;i<T;i++) {
			String input = br.readLine();
			
			Stack<Character> stack = new Stack<>();
			
			stack.push(input.charAt(0));
			for(int j=1;j<input.length();j++) {
				char temp = input.charAt(j);
				
				if(stack.isEmpty()) stack.push(temp);
				
				else if(temp == ')' && stack.peek() == '(') {
					stack.pop();
				}
				else {
					stack.push(temp);
				}
			}
			
			if(stack.isEmpty()) sb.append("YES"+"\n");
			else sb.append("NO"+"\n");
		}
		
		System.out.println(sb.toString());
	}

}