package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1543_문서_검색 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		String S2 = br.readLine();

		int size = S.length();
		int size2 = S2.length();
		

		S = S.replace(S2, "");
		System.out.println((size - S.length()) / size2);
	}

}
