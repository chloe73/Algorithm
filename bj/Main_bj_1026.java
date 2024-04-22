package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_1026 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int[] a = new int[N];
		int[] b = new int[N];
		
		for(int i=0;i<N;i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			b[i] =  Integer.parseInt(st.nextToken());
		} // input end
		
		Arrays.sort(a);
		Arrays.sort(b);
		
		int result = 0;
		
		int i = 0;
		int j = N-1;
		
		while(i < N) {
			result += (a[i] * b[j]);
			i++;
			j--;
		}
		
		System.out.println(result);
	}

}
