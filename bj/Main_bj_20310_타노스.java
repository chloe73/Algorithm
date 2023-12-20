package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_20310_타노스 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String s = br.readLine();
		
		char[] arr = new char[s.length()];
		
		int cnt0 = 0;
		int cnt1 = 0;
		
		for(int i=0;i<arr.length;i++) {
			arr[i] = s.charAt(i);
			if(arr[i] == '0') cnt0++;
			else cnt1++;
		}
		
		cnt0 /= 2;
		cnt1 /= 2;
		
		for(int i=0;i<arr.length && cnt1 != 0;i++) {
			if(arr[i] == '1') {
				cnt1--;
				arr[i] = 0;
			}
		}
		
		for(int i=arr.length-1;i>=0 && cnt0 != 0; i--) {
			if(arr[i] == '0') {
				cnt0--;
				arr[i] = 0;
			}
		}
		
		for(int i=0;i<arr.length;i++) {
			if(arr[i] != 0) {
				sb.append(arr[i]);
			}
		}
		
		System.out.println(sb.toString());
	}

}
