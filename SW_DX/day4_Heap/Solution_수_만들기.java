package algo.SW_DX.day4_Heap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_수_만들기 {
	
	static class Node implements Comparable<Node>{
		int num;
		int cnt;
		
		Node(int num, int cnt){
			this.num = num;
			this.cnt = cnt;
		}
		
		public int compareTo(Node o) {
			return this.cnt - o.cnt; // 최소 횟수
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			int N = Integer.parseInt(br.readLine());
			
			int[] A = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			
			int K = Integer.parseInt(br.readLine());
			
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.add(new Node(K, 0));
			
			int result = 0;
			while(!pq.isEmpty()) {
				Node temp = pq.poll();
				
				if(temp.num == 0) {
					result = temp.cnt;
					break;
				}
				
				// 곱하지 않고 1 빼는 경우
				pq.add(new Node(0, temp.cnt+temp.num));
				
				for(int i=0;i<N;i++) {
					pq.add(new Node(temp.num / A[i], temp.cnt + temp.num % A[i]));
				}
			}
			
			bw.write(result+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

}
