package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_8979_올림픽 {
	
	static int N,K;
	static class Country implements Comparable<Country> {
		int idx;
		int gold,silver,bronze;
		public Country(int idx, int gold, int silver, int bronze) {
			this.idx = idx;
			this.gold = gold;
			this.silver = silver;
			this.bronze = bronze;
		}
		public int compareTo(Country o) {
			if(this.gold == o.gold) {
				if(this.silver == o.silver) {
					return o.bronze - this.bronze;
				}
				return o.silver - this.silver;
			}
			return o.gold - this.gold;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		PriorityQueue<Country> pq = new PriorityQueue<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int idx = Integer.parseInt(st.nextToken());
			int gold = Integer.parseInt(st.nextToken());
			int silver = Integer.parseInt(st.nextToken());
			int bronze = Integer.parseInt(st.nextToken());
			
			pq.add(new Country(idx, gold, silver, bronze));
		} // input end
		
		int[] result = new int[N+1];
		
		Country c = pq.poll();
		int g = c.gold;
		int s = c.silver;
		int b = c.bronze;
		int rank = 1;
		int size = 1;
		result[c.idx] = rank;
		
		// 금메달 수가 더 많은 나라 
		// 금메달 수가 같으면, 은메달 수가 더 많은 나라
		// 금, 은메달 수가 모두 같으면, 동메달 수가 더 많은 나라 
		
		boolean isSame = false;
		while(!pq.isEmpty()) {
			Country temp = pq.poll();
			
			if(g == temp.gold && s == temp.silver && b == temp.bronze) {
				if(!isSame) {
					result[temp.idx] = rank;
					size++;
					isSame = true;
				}
				else {
					result[temp.idx] = rank;
					size++;
					continue;
				}
			}
			else {
				g = temp.gold;
				s = temp.silver;
				b = temp.bronze;
				
				result[temp.idx] = size+1;
				
				rank = size+1;
				size++;
				isSame = false;
				
				continue;
			}
		}

		System.out.println(result[K]);
	}

}
