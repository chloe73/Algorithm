package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_1759_암호_만들기 {
	
	static int L,C;
	static ArrayList<String> aList, resultList;
	static boolean[] isChecked;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		aList = new ArrayList<>();
		resultList = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<C;i++) {
			String s = st.nextToken();
			
			aList.add(s);
		} // input end
		
		solve();
		
		Collections.sort(resultList);
		
		for(int i=0;i<resultList.size();i++) {
			System.out.println(resultList.get(i));
		}
		
	}

	private static void solve() {
		// 최소 1개 모음 포함 + 최소 2개 자음 포함
		
		Collections.sort(aList);
		
		isChecked = new boolean[C];
		
		comb(0, 0);
	}
	
	private static void comb(int idx, int cnt) {
		
		if(cnt == L) {
			int aeiou = 0;
			int another = 0;
			String target = "";
			
			for(int i=0;i<C;i++) {
				if(isChecked[i]) {
					
					String s = aList.get(i);
					
					if(s.equals("a") || s.equals("e") || s.equals("i") || s.equals("o") || s.equals("u"))
						aeiou++;
					else another++;
					
					target += s;
				}
			}

			if(aeiou >= 1 && another >=2) resultList.add(target);
			
			return;
		}
		
		for(int i=idx;i<C;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				comb(i, cnt+1);
				isChecked[i] = false;
			}
		}
	}

}
