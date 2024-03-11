package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_10610_30 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		char[] arr = input.toCharArray();
		Arrays.sort(arr);
		
		// 30 = 3 * 10 이므로 30의 배수이면서 3의 배수이다.
		// 즉, 모든 숫자들의 합이 3의 배수이어야 한다.
		// 이와 동시에 꼭 0을 가지고 있어야 30의 배수가 가능해진다.
		int sum = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=input.length()-1;i>=0;i--) {
			sum += arr[i]-'0';
			sb.append(arr[i]-'0');
		}
		
		if(arr[0] != '0' || sum % 3 != 0) {
			System.out.println(-1);
		}
		else {
			System.out.println(sb.toString());
		}
	}

}