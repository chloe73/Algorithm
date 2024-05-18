package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_14916 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int result = 0;
		
		if(N %5 == 0) {
			result = N / 5;
		}
		else {
			for(int i=N/5;i>=0;i--) {
				if((N - (5*i)) % 2 == 0) {
					result = i + (N-(5*i)) / 2;
					break;
				}
			}
			
		}

		System.out.println(result == 0 ? -1 : result);
	}

}