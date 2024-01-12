package algo.bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.io.IOException;

public class Main_bj_18258_큐2 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> q = new LinkedList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String cmd = st.nextToken();
			
			// push X: 정수 X를 큐에 넣는 연산이다.
			if(cmd.equals("push")) {
				int num = Integer.parseInt(st.nextToken());
				q.add(num);
				continue;
			}
			// pop: 큐에서 가장 앞에 있는 정수를 빼고, 그 수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
			else if(cmd.equals("pop")) {
				if(!q.isEmpty()) sb.append(q.poll()+"\n");
				else sb.append(-1+"\n");
				continue;
			}
			// size: 큐에 들어있는 정수의 개수를 출력한다.
			else if(cmd.equals("size")) {
				sb.append(q.size()+"\n");
				continue;
			}
			// empty: 큐가 비어있으면 1, 아니면 0을 출력한다.
			else if(cmd.equals("empty")) {
				if(q.isEmpty()) sb.append(1+"\n");
				else sb.append(0+"\n");
				continue;
			}
			// front: 큐의 가장 앞에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
			else if(cmd.equals("front")) {
				if(q.isEmpty()) sb.append(-1+"\n");
				else sb.append(q.peek()+"\n");
				continue;
			}
			// back: 큐의 가장 뒤에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
			else {
				if(q.isEmpty()) sb.append(-1+"\n");
				else sb.append(q.peekLast()+"\n");
				continue;
			}
		} // input end
		
		System.out.println(sb.toString());
	}

}
