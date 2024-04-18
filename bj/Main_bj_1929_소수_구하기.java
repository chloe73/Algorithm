package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1929_소수_구하기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		StringBuilder sb = new StringBuilder();
		
		boolean[] isChecked = new boolean[N+1];
		isChecked[0] = true;
		isChecked[1] = true;
		
		for(int i=2;i*i<=N;i++) {
			if(!isChecked[i]) {
				for(int j=i*i;j<=N;j+=i) {
					isChecked[j] = true;
				}
			}
		}
		
		for(int i=M;i<=N;i++) {
			if(!isChecked[i])
				sb.append(i+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
