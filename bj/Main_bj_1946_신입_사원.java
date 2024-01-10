package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_1946_신입_사원 {
	
	static class NewRecruit implements Comparable<NewRecruit>{
		int paper,interview;
		public NewRecruit(int paper, int interview) {
			this.paper = paper;
			this.interview = interview;
		}
		public int compareTo(NewRecruit o) {
			return this.paper - o.paper; // 서류 1등부터 정렬
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1;tc<=T;tc++) {
			int N = Integer.parseInt(br.readLine());
			ArrayList<NewRecruit> list = new ArrayList<>();
			
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				// 서류심사 성적, 면접 성적의 순위
				int paper = Integer.parseInt(st.nextToken());
				int interview = Integer.parseInt(st.nextToken());
				
				list.add(new NewRecruit(paper, interview));
			}
			
			Collections.sort(list);
			
			int result = 1;
			int min = list.get(0).interview;
			for(int i=1;i<N;i++) {
				if(list.get(i).interview < min) {
					result++;
					min = list.get(i).interview;
				}
			}
			
			sb.append(result+"\n");
		} // input end
		
		System.out.println(sb.toString());
	}

}
