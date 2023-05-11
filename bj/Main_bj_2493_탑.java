package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_2493_탑 {
	
	static int N;
	static Stack<Top> stack;
	static class Top {
		int idx,h;
		public Top(int idx, int h) {
			this.idx = idx;
			this.h = h;
		}
	}
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		stack = new Stack<>();

		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			int height = Integer.parseInt(st.nextToken());
			
			while(!stack.isEmpty()) {
				Top top = stack.peek();
				if(top.h >= height) {
					sb.append(top.idx+" ");
					break;
				}
				stack.pop();
			}
			
			if(stack.isEmpty()) {
				sb.append(0+" ");
				
			}
			
			stack.push(new Top(i, height));
			
		} // input end
		
		System.out.println(sb.toString());
	}

}
