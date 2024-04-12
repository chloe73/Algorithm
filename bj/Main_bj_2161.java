package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_bj_2161 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		Queue<Integer> q = new LinkedList<>();
		for(int i=1;i<=N;i++) {
			q.add(i);
		}
		
		StringBuilder sb = new StringBuilder();
		while(!q.isEmpty()) {
			// 맨 위에 있는 수 바닥에 버림.
			sb.append(q.poll()+" ");
			
			if(q.isEmpty()) break;
			
			int top = q.poll();
			q.add(top);
		}
		
		System.out.println(sb.toString());
	}

}
