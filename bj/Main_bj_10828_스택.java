package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_10828_스택 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		Stack<Integer> stack = new Stack<>();		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String cmd = st.nextToken();
			
			if(cmd.equals("push")) {
				int num = Integer.parseInt(st.nextToken());
				stack.push(num);
			}
			else if(cmd.equals("pop")) {
				if(stack.isEmpty()) sb.append(-1+"\n");
				else sb.append(stack.pop()+"\n");
			}
			else if(cmd.equals("size")) {
				sb.append(stack.size()+"\n");
			}
			else if(cmd.equals("empty")) {
				if(stack.isEmpty()) sb.append(1+"\n");
				else sb.append(0+"\n");
			}
			else {
				if(stack.isEmpty()) sb.append(-1+"\n");
				else sb.append(stack.peek()+"\n");
			}
		} // input end

		System.out.println(sb.toString());
	}

}
