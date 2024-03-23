package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_1158_요세푸스_문제 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		Queue<Integer> q = new LinkedList<>();
		
		for(int i=1;i<=N;i++) {
			q.add(i);
		}
		
		sb.append("<");
		int cnt = 0;
		while(!q.isEmpty()) {
			int temp = q.poll();
			cnt++;
			
			if(cnt == K) {
				cnt = 0;
				if(q.size() == 0) 
					sb.append(temp);
				
				else 
					sb.append(temp+", ");
				continue;
			}
			
			q.add(temp);
		}
		sb.append(">");
		
		System.out.println(sb.toString());
	}

}
