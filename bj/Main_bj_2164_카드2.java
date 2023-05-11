package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_bj_2164_카드2 {
	
	static int N, result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		Queue<Integer> q = new LinkedList<>();
		for(int i=1;i<=N;i++) {
			q.add(i);
		}
		
		while(true) {
			
			if(q.size() == 1) break;
			
			// 1. 우선, 제일 위 카드 버
//			System.out.print("1번 버린 카드 : "+q.peek()+"\n");
			q.poll();
			
			// 2. 제일 위 카드 제일 아래에 있는 카드 밑으로 옮긴다.
//			System.out.println("2번 카드 : "+q.peek());
			int top = q.poll();
			q.add(top);
			
		}
		
		System.out.println(q.poll());
	}

}
