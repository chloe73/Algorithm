package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_1863_스카이라인_쉬운거 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		int result = 0;
		Stack<Integer> stack = new Stack<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			while(!stack.isEmpty() && stack.peek() > y) {
				result++;
				stack.pop();
			}
			
			while(!stack.isEmpty() && stack.peek() == y) {
				continue;
			}
			
			// 최고층 높이 갱신
			stack.push(y);
		}

		while(!stack.isEmpty()) {
			if(stack.peek() > 0) {
				result++;
			}
			stack.pop();
		}
		
		System.out.println(result);
	}

}
