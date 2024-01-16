package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution_사칙연산 {
	
	static int[][] tree;
	static String[] arr;
	static int N, result;
	static Stack<Double> stack;
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int tc=1;tc<=10;tc++) {
			bw.write("#"+tc+" ");
			
			N = Integer.parseInt(br.readLine());
			tree = new int[N+1][2];
			arr = new String[N+1];
			stack = new Stack<>();
			
			for(int i=1;i<=N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				int idx = Integer.parseInt(st.nextToken());
				String cmd = st.nextToken();
				
				if(cmd.equals("+")|| cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
					int left = Integer.parseInt(st.nextToken());
					int right = Integer.parseInt(st.nextToken());
					tree[idx][0] = left;
					tree[idx][1] = right;
				}
				arr[idx] = cmd;
				
			} // input end
			result = 0;
			postorder(1);
			result = stack.pop().intValue();
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void postorder(int i) {
		if(i != 0) {
			// 후위 순회
			postorder(tree[i][1]);

			postorder(tree[i][0]);

			if(arr[i].equals("+")|| arr[i].equals("-") || arr[i].equals("*") || arr[i].equals("/")) {
				calc(arr[i]);
			}
			else stack.push(Double.parseDouble(arr[i]));
		}
		
		
	}
	
	private static void calc(String c) {
		
		// 연산자인 경우
		double a = stack.pop();
		double b = stack.pop();
		
		if(c.equals("+")) {
			stack.push(a+b);
		}
		else if(c.equals("-")) {
			stack.push(a-b);
		}
		else if(c.equals("*")) {
			stack.push(a*b);
		}
		else {
			stack.push(a/b);
		}
	}

}
