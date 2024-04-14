package algo.codetree1;

import java.util.Arrays;

public class Main_perm_comb {

	static int N;
	static boolean[] isChecked;
	static int[] output;
	
	public static void main(String[] args) {
		
		N = 5;
		
		isChecked = new boolean[N];
		output = new int[N];
		System.out.println("순열 !");
		perm(0);
		
		isChecked = new boolean[N];
		System.out.println("조합 !");
		comb(0, 0);
	}
	
	private static void comb(int idx, int cnt ) {
		if(cnt == 3) {
			for(int i=0;i<N;i++) {
				if(isChecked[i]) {
					System.out.print(i+" ");
				}
			}
			System.out.println();
			return;
		}
		
		for(int i=idx;i<N;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				comb(idx+1,cnt+1);
				isChecked[i] = false;
			}
		}
	}
	
	private static void perm(int idx) {
		if(idx == N) {
			for(int i=0;i<N;i++) {
				System.out.print(output[i]+" ");
			}
			System.out.println();
			
			return;
		}
		
		for(int i=0;i<N;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				output[idx] = i;
				perm(idx+1);
				isChecked[i] = false;
				output[idx] = -1;
			}
		}
	}

}
