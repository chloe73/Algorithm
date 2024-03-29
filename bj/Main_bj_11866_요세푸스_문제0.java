package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_11866_요세푸스_문제0 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		Queue<Integer> q = new LinkedList<>();
		for(int i=1;i<=N;i++) {
			
			q.add(i);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		int cnt = 0;
		if(q.size() == 1) {
			sb.append(q.poll());
		}
		else {
			
			while(!q.isEmpty()) {
				cnt++;
				int tmp = q.poll();
				if(q.size() == 0) {
					sb.append(tmp);
					break;
				}
				if(cnt == K) {
					cnt = 0;
					sb.append(tmp+", ");
					continue;
				}
				q.add(tmp);
			}
		}
		sb.append(">");
		System.out.println(sb.toString());
	}

}
