package algo.twoPointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_bj_1644_소수의_연속합 {
	
	static int N,result;
	static ArrayList<Integer> primeNum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		primeNum = new ArrayList<Integer>();
		
		getPrime();
		
		int left = 0;
		int right = 0;
		int sum = 0;
		
		while(true) {
			
			if(sum >= N) {
				sum -= primeNum.get(left++);
			}
			
			else if(right == primeNum.size()) break;
			
			else {
				sum += primeNum.get(right++);
			}
			
			if(N == sum) result++;
		}
		
		System.out.println(result);
	}
	
	private static void getPrime() {
		boolean[] check = new boolean[N+1];
		check[0] = check[1] = true;
		
		for(int i=2;i*i<=N;i++) {
			if(!check[i]) {
				for(int j=i*i;j<=N;j+=i) {
					check[j] = true;
				}
			}
		}
		
		for(int i=2;i<=N;i++) {
			if(!check[i]) primeNum.add(i);
		}
		
	}

}
