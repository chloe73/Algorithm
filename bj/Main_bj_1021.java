package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_bj_1021 {
	
	static int N,M,result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		Deque<Integer> deq = new LinkedList<>();
		
		for(int i=1;i<=N;i++) {
			deq.add(i);
		}
		
		st = new StringTokenizer(br.readLine());
		int[] input = new int[M];
		for(int i=0;i<M;i++) {
			input[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		for(int i=0;i<M;i++) {
			
		}
		
		System.out.println(result);
	}

}
