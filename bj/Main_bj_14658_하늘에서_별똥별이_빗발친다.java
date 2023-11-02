package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_14658_하늘에서_별똥별이_빗발친다 {
	
	static int N,M,L,K,result;
	static ArrayList<int[]> starList;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		starList = new ArrayList<>();
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			starList.add(new int[] {x,y});
		} // input end
		
		result = Integer.MIN_VALUE;
		
		for(int[] s1 : starList) {
			for(int[] s2 : starList) {
				result = Math.max(result, is_valid(s1[0], s2[1]));
			}
		}
		
		System.out.println(K-result);
	}
	
	private static int is_valid(int r, int c) {
		int cnt = 0;
		for(int[] s : starList) {
			if(r<=s[0] && s[0] <= r+L && c<=s[1] && s[1] <= c+L)
				cnt++;
		}
		return cnt;
	}

}
