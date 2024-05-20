package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_2588 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int a = Integer.parseInt(br.readLine());
		String b = br.readLine();
		
		StringBuilder sb = new StringBuilder();
		
		int number = a * (b.charAt(2)-'0');
		sb.append(number+"\n");
		
		number = a * (b.charAt(1)-'0');
		sb.append(number+"\n");
		
		number = a * (b.charAt(0)-'0');
		sb.append(number+"\n");
		
		int sum = a * Integer.parseInt(b);
		sb.append(sum);
		
		System.out.println(sb.toString());
	}

}
