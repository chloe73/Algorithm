package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1202_보석_도둑 {
	
	static int N,K;
	static long maxPrice;
	static ArrayList<Jewelry> jList;
	static ArrayList<Integer> bag;
	static class Jewelry implements Comparable<Jewelry> {
		int m;
		int v;
		
		public Jewelry(int m, int v) {
			this.m = m;
			this.v = v;
		}
		
		public int compareTo(Jewelry o) {
			if(this.m == o.m)
				return o.v - this.v;
			return this.m - o.m;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		jList = new ArrayList<>();
		bag = new ArrayList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			int m = Integer.parseInt(st.nextToken()); // 무게
			int v = Integer.parseInt(st.nextToken()); // 가격
			
			jList.add(new Jewelry(m, v));
		}
		
		// 보석 정렬
		Collections.sort(jList);
		
		for(int i=0;i<K;i++) {
			int c = Integer.parseInt(br.readLine()); // 가방에 담을 수 있는 최대 무게
			bag.add(c);
		} // input end
		
		// 가방 정렬
		Collections.sort(bag);
		
		solve();
		
		// 상덕이가 훔칠 수 있는 보석 가격의 합의 최댓값을 출력한다.
		System.out.println(maxPrice);
	}

	private static void solve() {

		// pq는 항상 가격이 내림차순 되도록 설정
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		
		int idx = 0; // 보석의 인덱스
		for(int i=0;i<K;i++) {
			// 현재 가방의 무게보다 작거나 같은 보석들 pq에 넣기
			while(idx < N && jList.get(idx).m <= bag.get(i)) {
				pq.add(jList.get(idx++).v);
			}
			
			if(!pq.isEmpty()) {
				maxPrice += pq.poll();
			}
		}
	}

}