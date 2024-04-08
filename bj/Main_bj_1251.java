package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main_bj_1251 {
	
	static boolean[] isChecked;
	static ArrayList<String> target;
	static String s;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		target = new ArrayList<>();
		s = br.readLine();
		
		isChecked = new boolean[s.length()-1];
		comb(0,0);

		Collections.sort(target);
		System.out.println(target.get(0));
	}
	
	private static void comb(int idx, int cnt) {
		if(cnt == 2) {
			String a = "";
			String b = "";
			String c = "";
			ArrayList<Integer> list = new ArrayList<>();
			for(int i=0;i<isChecked.length;i++) {
				if(isChecked[i]) {
					list.add(i);
				}
			}
			int first = list.get(0);
			int second = list.get(1);
			a = s.substring(0,first+1);
			b = s.substring(first+1,second+1);
			c = s.substring(second+1);
			
			if(a.length() > 1) {
				String reverse = "";
				for(int i=a.length()-1;i>=0;i--) {
					reverse += a.charAt(i);
				}
				a = reverse;
			}
			if(b.length() > 1) {
				String reverse = "";
				for(int i=b.length()-1;i>=0;i--) {
					reverse += b.charAt(i);
				}
				b = reverse;
			}
			if(c.length() > 1) {
				String reverse = "";
				for(int i=c.length()-1;i>=0;i--) {
					reverse += c.charAt(i);
				}
				c = reverse;
			}
			String word = a+b+c;
			target.add(word);
			return;
		}
		
		for(int i=idx;i<isChecked.length;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				comb(i+1, cnt+1);
				isChecked[i] = false;
			}
		}
	}

}
