package algo.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_softeer_슈퍼컴퓨터클러스터 {
	
	static int N;
	static long B;
	static int[] ai;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());
		
		ai = new int[N];
		st = new StringTokenizer(br.readLine()); 
		for(int i=0;i<N;i++) {
			ai[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(ai);
		
		long low = ai[0];
		long high = ai[N-1] + (long)Math.sqrt(B);
		long result = 0;
		
		while(low <= high) {
			long mid = (low +high) / 2;
			
			if(calculate(mid, ai, B)) {
				low = mid + 1;
				result = mid;
			} else high = mid - 1;
		}
		
		System.out.println(result);
	}

	private static boolean calculate(long min, int[] a, long B) {
		long cost = 0;
		
		for(int i : a) {
			if(i < min) {
				cost += (min - i) * (min - i);
				if(cost > B) return false;
			}
		}
		
		return true;
	}
}
