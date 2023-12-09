package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_7568_덩치 {
	
	static int N;
	static ArrayList<int[]> arrList;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		arrList = new ArrayList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			// 몸무게, 키
			int weight = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());
			
			arrList.add(new int[] {weight,height});
		} // input end
		
		solve();

		System.out.println(sb.toString());
	}

	private static void solve() {
		// N명의 집단에서 각 사람의 덩치 등수는 자신보다 더 "큰 덩치"의 사람의 수로 정해진다. 
		// 만일 자신보다 더 큰 덩치의 사람이 k명이라면 그 사람의 덩치 등수는 k+1이 된다.
		// 이렇게 등수를 결정하면 같은 덩치 등수를 가진 사람은 여러 명도 가능하다.
		
		for(int i=0;i<N;i++) {
			int[] temp = arrList.get(i);
			int cnt = 0;
			
			for(int j=0;j<N;j++) {
				if(i == j) continue;
				
				int[] next = arrList.get(j);
				
				if(next[0] > temp[0] && next[1] > temp[1]) cnt++;
				
			}
			
			sb.append(cnt+1).append(" ");
		}
	}

}
