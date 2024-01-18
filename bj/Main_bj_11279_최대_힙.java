package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main_bj_11279_최대_힙 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {
			return o2 - o1;
		});
		
		for(int i=0;i<N;i++)	{
			int number = Integer.parseInt(br.readLine());
			
			if(number == 0) {
				if(pq.isEmpty()) bw.write(0+"\n");
				else bw.write(pq.poll()+"\n");
			}
			else {
				pq.add(number);
			}
		} // input end

		bw.flush();
		br.close();
		bw.close();
	}

}
