package algo.SW_DX.day4_Heap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_중간값_구하기 {
	
	static int N, A;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			
			int sum = 0;
			// 최대 힙과 최소 힙을 이용하여 문제를 해결할 수 있습니다.
			PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
			PriorityQueue<Integer> minHeap = new PriorityQueue<>();
			maxHeap.add(A);

			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				int bigger = Math.max(x, y);
				int smaller = Math.min(x, y);
				
				maxHeap.add(bigger);
				minHeap.add(smaller);
				
				if(maxHeap.peek() > minHeap.peek()) {
					int max = maxHeap.poll();
					int min = minHeap.poll();
					
					maxHeap.add(min);
					minHeap.add(max);
				}

				sum += maxHeap.peek();
				// N개의 중간값들을 더하는 과정에서 32bit 정수 자료형의 최대 범위를 벗어날 수 있음에 주의합니다.
				// 따라서 중간값을 더할 때마다, 20171109로 나눈 나머지를 변수에 기록하는 것이 좋습니다.
				sum %= 20171109;
			}
			
			bw.write(sum+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

}
