package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1541_잃어버린_괄호 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), "-");

		int result = Integer.MAX_VALUE;
		while(st.hasMoreTokens()) {
			int temp = 0;
			
			StringTokenizer addition = new StringTokenizer(st.nextToken(),"+");
			
			while(addition.hasMoreTokens()) {
				temp += Integer.parseInt(addition.nextToken());
			}
			
			if(result == Integer.MAX_VALUE) {
				result = temp;
			}
			else {
				result -= temp;
			}
		}
		
		System.out.println(result);
	}

}
