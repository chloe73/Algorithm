package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_2179_비슷한_단어 {
	
	static int N;
	static String[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		arr = new String[N];
		
		for(int i=0;i<N;i++) {
			arr[i] = br.readLine();
		}
		
		int max = Integer.MIN_VALUE;
		int result1 = -1;
		int result2 = -1;
		
		for(int i=0;i<N-1;i++) {
			String a = arr[i];
			
			for(int j=i+1;j<N;j++) {
				String b = arr[j];
				
				int cnt = 0;
				int size = Math.min(a.length(), b.length());
				
				for(int k=0;k<size;k++) {
					if(a.charAt(k) == b.charAt(k)) cnt++;
					else break;
				}
				
				if(max<cnt) {
					max = cnt;
					result1 = i;
					result2 = j;
				}
				
			}
		}
		
		System.out.println(arr[result1]);
		System.out.println(arr[result2]);
	}

}
