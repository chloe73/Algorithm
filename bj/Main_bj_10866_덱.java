package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_bj_10866_덱 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> deque = new LinkedList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String cmd = st.nextToken();
			
			if(cmd.equals("push_front")) {
				int x = Integer.parseInt(st.nextToken());
				deque.addFirst(x);
			}
			else if(cmd.equals("push_back")) {
				int x = Integer.parseInt(st.nextToken());
				deque.add(x);
			}
			else if(cmd.equals("pop_front")) {
				if(deque.isEmpty()) sb.append(-1+"\n");
				else sb.append(deque.pollFirst()+"\n");
			}
			else if(cmd.equals("pop_back")) {
				if(deque.isEmpty()) sb.append(-1+"\n");
				else sb.append(deque.pollLast()+"\n");
			}
			else if(cmd.equals("size")) {
				sb.append(deque.size()+"\n");
			}
			else if(cmd.equals("empty")) {
				if(deque.isEmpty()) sb.append(1+"\n");
				else sb.append(0+"\n");
			}
			else if(cmd.equals("front")) {
				if(deque.isEmpty()) sb.append(-1+"\n");
				else sb.append(deque.peekFirst()+"\n");
			}
			else {
				if(deque.isEmpty()) sb.append(-1+"\n");
				else sb.append(deque.peekLast()+"\n");
			}			
		} // input end

		System.out.println(sb.toString());
	}

}
