package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_13335_트럭 {
	
	static int N,W,L,result;
	static Queue<Integer> q;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		
		q = new LinkedList<Integer>();
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			int num = Integer.parseInt(st.nextToken());
			q.add(num);
		} // input end
		
		Queue<Integer> bridge = new LinkedList<Integer>();
		// 다리 길이 초기 세팅 : 다리 길이 만큼 큐에 0씩 추가
		for(int w=0;w<W;w++) {
			bridge.add(0);
		}
		
		int cnt = 0; // 트럭의 개수
		int sum = 0; // 트럭의 무게
		
		while(!bridge.isEmpty()) {
			sum -= bridge.poll();
			if(!q.isEmpty()) {
				if(sum + q.peek() <= L) {
					int truck = q.poll();
					sum += truck;
					bridge.add(truck);
				}
				else {
					bridge.add(0);
				}
			}
			result++;
		}
		System.out.println(result);
	}

}
