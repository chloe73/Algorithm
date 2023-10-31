package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main_bj_1715_카드_정렬하기 {
	
	static int N;
	static PriorityQueue<Long> pq;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		pq = new PriorityQueue<>();
		for(int i=0;i<N;i++) {
			long num = Long.parseLong(br.readLine());
			
			pq.add(num);
		} // input end
		
		long result = 0;

		while(pq.size() > 1) {
			long temp1 = pq.poll();
			long temp2 = pq.poll();
			
			result += temp1 + temp2;
			pq.add(temp1 + temp2);

		}			

		System.out.println(result);
	}

}
