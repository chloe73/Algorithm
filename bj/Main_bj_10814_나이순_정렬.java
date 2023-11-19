package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_10814_나이순_정렬 {
	
	static class Member implements Comparable<Member> {
		int idx;
		int age;
		String name;
		
		public Member(int idx, int age, String name) {
			this.idx = idx;
			this.age = age;
			this.name = name;
		}
		
		public int compareTo(Member o) {
			if(this.age == o.age)
				return this.idx - o.idx;
			return this.age - o.age;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Member> pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int age = Integer.parseInt(st.nextToken());
			String name = st.nextToken();
			
			pq.add(new Member(i, age, name));
		} // input end
		
		while(!pq.isEmpty()) {
			Member temp = pq.poll();
			
			sb.append(temp.age+" "+temp.name).append("\n");
		}
		
		System.out.println(sb.toString());
	}

}
