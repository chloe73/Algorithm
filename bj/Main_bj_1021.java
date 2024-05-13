package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_bj_1021 {
	
	static int N,M,result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		LinkedList<Integer> deq = new LinkedList<>();
		
		for(int i=1;i<=N;i++) {
			deq.add(i);
		}
		
		st = new StringTokenizer(br.readLine());
		int[] input = new int[M];
		for(int i=0;i<M;i++) {
			input[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		result = 0;
		for(int i=0;i<M;i++) {
			int target = deq.indexOf(input[i]);
			int half;
			
			if(deq.size() % 2 == 0) {
				half = deq.size() / 2 -1;
			}
			else {
				half = deq.size() / 2;
			}
			
			if(target <= half) {
				
				for(int j=0;j<target;j++) {
					int temp = deq.pollFirst();
					deq.addLast(temp);
					result++;
				}
			}
			else {
				for(int j=0;j<deq.size()-target;j++) {
					int temp = deq.pollLast();
					deq.addFirst(temp);
					result++;
				}
			}
			
			deq.pollFirst();
		}
		
		System.out.println(result);
	}

}
