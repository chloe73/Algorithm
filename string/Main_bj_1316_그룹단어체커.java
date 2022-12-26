package algo.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main_bj_1316_그룹단어체커 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		String[] input = new String[N];
		Set<Character> alpha = new HashSet<Character>();
		for(int i=0;i<N;i++) {
			input[i] = br.readLine();
			for(int j=0;j<input[i].length();j++) {
				alpha.add(input[i].charAt(j));
			}
		}
	}

}
