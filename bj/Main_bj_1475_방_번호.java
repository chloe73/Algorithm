package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1475_방_번호 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String N = br.readLine();
		int[] number = new int[10];
		int result = 0;
		
		for(int i=0;i<N.length();i++) {
			int temp = N.charAt(i)-'0';
			if(temp == 6 || temp == 9) {
				number[6]++;
			}
			else {
				number[temp]++;
			}
		}
		if(number[6] > 1) {
			if(number[6] % 2 == 0)
				number[6] /= 2;
			else
				number[6] = number[6]/2 + 1;
			
		}
		for(int i=0;i<10;i++) {
			result = Math.max(result, number[i]);
		}
		
		System.out.println(result);
	}

}
