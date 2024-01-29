package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main_bj_1655_가운데를_말해요 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> minPq = new PriorityQueue<>();
		PriorityQueue<Integer> maxPq = new PriorityQueue<>(Collections.reverseOrder());
		
		for(int i=0;i<N;i++) {
			int input = Integer.parseInt(br.readLine());
			
			if(minPq.size() == maxPq.size()) {
				maxPq.add(input);
				
				if(!minPq.isEmpty() && maxPq.peek() > minPq.peek()) {
					minPq.add(maxPq.poll());
					maxPq.add(minPq.poll());
				}
			}
			else {
				minPq.add(input);
				
				if(maxPq.peek() > minPq.peek()) {
					minPq.add(maxPq.poll());
					maxPq.add(minPq.poll());
				}
			}
			
			bw.write(maxPq.peek()+"\n");
		} // input end
		
		bw.flush();
		br.close();
		bw.close();
	}

}
