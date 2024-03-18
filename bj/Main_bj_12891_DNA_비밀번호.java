package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_12891_DNA_비밀번호 {
	
	static int S,P,result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		S = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		
		char[] arr = br.readLine().toCharArray();

		st = new StringTokenizer(br.readLine());
		int[] dna = new int[4];
		for(int i=0;i<4;i++) {
			dna[i] = Integer.parseInt(st.nextToken());
		}

		result = 0;
		int[] cnt = new int[4];
		for(int i=0;i<P;i++) {
			switch (arr[i]) {
			case 'A':
				cnt[0]++;
				break;
			case 'C':
				cnt[1]++;
				break;
			case 'G':
				cnt[2]++;
				break;
			case 'T':
				cnt[3]++;
				break;
			}
		}
		
		for(int i=0;i<= S-P;i++) {
			boolean flag = false;
			
			for(int j=0;j<4;j++) {
				if(cnt[j] < dna[j]) {
					flag = true;
					break;
				}
			}
			
			if(!flag)
				result++;
			if(i == S-P) break;
			
			cnt[getPos(arr[i])]--;
			cnt[getPos(arr[i+P])]++;
		}
		
		System.out.println(result);
	}

	private static int getPos(char c) {
		switch (c) {
		case 'A': return 0;
		case 'C': return 1;
		case 'G': return 2;
		case 'T': return 3;
		}
		return -1;
	}
}
