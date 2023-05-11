package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_bj_2866_문자열_잘라내기 {
	
	static int R,C,result;
	static char[][] word;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb;
		
		st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		word = new char[R][C];
		for(int i=0;i<R;i++) {
			word[i] = br.readLine().toCharArray();
		} // input end
		
		Set<String> set;
		String[] arr = new String[C];
		
		for(int i=0;i<C;i++) {
			sb = new StringBuilder();
			for(int j=1;j<R;j++) {
				sb.append(word[j][i]);
			}
			arr[i] = sb.toString();
		}
		
		outer:for(int r=0;r<R;r++) {
			set = new HashSet<>();
			for(int c=0;c<C;c++) {
				String temp = arr[c].substring(r);
				
				if(set.contains(temp)) break outer;
				
				else set.add(temp);
			}
			result++;
		}
		
		System.out.println(result);
	}

}
