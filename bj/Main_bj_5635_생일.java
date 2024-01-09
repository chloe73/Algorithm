package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_5635_생일 {
	
	static class Student implements Comparable<Student>{
		String name;
		int dd;
		int mm;
		int yyyy;
		
		public Student(String name, int dd, int mm, int yyyy) {
			this.name = name;
			this.dd = dd;
			this.mm = mm;
			this.yyyy = yyyy;
		}
		// 가장 나이가 적은 사람이 먼저 pq에서 나온다.
		public int compareTo(Student o) {
			if(this.yyyy == o.yyyy) {
				if(this.mm == o.mm) {
					return o.dd - this.dd;
				}
				return o.mm - this.mm;
			}
			return o.yyyy - this.yyyy;
		}
	}

	public static void main(String[] args) throws IOException{
		// 어떤 반에 있는 학생들의 생일이 주어졌을 때,
		// 가장 나이가 적은 사람과 가장 많은 사람을 구하는 프로그램을 작성하시오.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Student> pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			String name = st.nextToken();
			int dd = Integer.parseInt(st.nextToken());
			int mm = Integer.parseInt(st.nextToken());
			int yyyy = Integer.parseInt(st.nextToken());
			
			pq.add(new Student(name, dd, mm, yyyy));
		}
		sb.append(pq.poll().name+"\n");
		
		String oldestName = "";
		while(!pq.isEmpty()) {
			oldestName = pq.poll().name;
		}
		sb.append(oldestName+"\n");
		
		System.out.println(sb.toString());
	}

}
