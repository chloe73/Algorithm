package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main_bj_11286_절댓값_힙 {
	
	static class Number implements Comparable<Number>{
		int num;
		int absoluteValue;
		
		public Number(int num, int absoluteValue) {
			this.num = num;
			this.absoluteValue = absoluteValue;
		}
		
		public int compareTo(Number o) {
			// 절댓값이 가장 작은 값이 여러개일 때는, 가장 작은 수를 출력
			if(this.absoluteValue == o.absoluteValue) {
				return this.num - o.num;
			}
			// 절댓값이 가장 작은 값을 출력
			return this.absoluteValue - o.absoluteValue;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		
		PriorityQueue<Number> pq = new PriorityQueue<>();
		for(int i=0;i<N;i++) {
			int x = Integer.parseInt(br.readLine());
			
			//  만약 x가 0이 아니라면 배열에 x라는 값을 넣는(추가하는) 연산
			if(x != 0) {
				pq.add(new Number(x, Math.abs(x)));
				continue;
			}
			// x가 0이라면 배열에서 절댓값이 가장 작은 값을 출력하고 그 값을 배열에서 제거하는 경우
			else {
				if(pq.isEmpty()) sb.append(0+"\n");
				else sb.append(pq.poll().num+"\n");
				continue;
			}
		} // input end
		
		System.out.println(sb.toString());
	}

}
