package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_10845_큐 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		Queue<Integer> q = new LinkedList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String cmd = st.nextToken();
			
			if(cmd.equals("push")) {
				int num = Integer.parseInt(st.nextToken());
				q.add(num);
			}
			else if(cmd.equals("pop")) {
				if(q.isEmpty()) sb.append(-1+"\n");
				else sb.append(q.poll()+"\n");
			}
			else if(cmd.equals("size")) {
				sb.append(q.size()+"\n");
			}
			else if(cmd.equals("empty")) {
				if(q.isEmpty()) sb.append(1+"\n");
				else sb.append(0+"\n");
			}
			else if(cmd.equals("front")) {
				if(q.isEmpty()) sb.append(-1+"\n");
				else sb.append(q.peek()+"\n");
			}
			else {
				if(q.isEmpty()) sb.append(-1+"\n");
				else {
					int size = q.size();
					Queue<Integer> temp = new LinkedList<>();
					for(int k=0;k<size;k++) {
						if(k == size-1) {
							sb.append(q.peek()+"\n");
							temp.add(q.poll());
						}
						else temp.add(q.poll());
					}
					
					while(!temp.isEmpty()) {
						q.add(temp.poll());
					}
				}
			}
		} // input end

		System.out.println(sb.toString());
	}

}
