package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_5052_전화번호_목록 {
	
	static int N;
	static String[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int tc = Integer.parseInt(br.readLine());
		for(int t=0;t<tc;t++) {
			N = Integer.parseInt(br.readLine());
			arr = new String[N];
			for(int i=0;i<N;i++	) {
				arr[i] = br.readLine();
			}
			
			Arrays.sort(arr);
			boolean flag = false;
			for(int i=0;i<N-1;i++) {
				if(arr[i+1].startsWith(arr[i])) {
					flag = true;
					break;
				}
			}
			
			if(!flag)
				sb.append("YES").append("\n");
			else 
				sb.append("NO").append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
}
