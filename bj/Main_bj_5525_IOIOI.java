package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_5525_IOIOI {
	
	static int N,M,result;
	static char[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		arr = br.readLine().toCharArray();
		result = 0;
		
		// N = 2
		// OO IOIOI OIIOII
		// OOIO IOIOI IOII
		int[] memo = new int[M]; // 현재 칸까지 'OI'가 몇 개 반복되었는지
		for(int i=1;i<M-1;i++) {
			if(arr[i] == 'O' && arr[i+1] == 'I') {
				memo[i+1] = memo[i-1] + 1;
				
				if(memo[i+1] >= N && arr[i-2*N+1] == 'I') {
					result++;
				}
			}
		}
		
		System.out.println(result);
	}

}