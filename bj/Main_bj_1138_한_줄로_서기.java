package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1138_한_줄로_서기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		ArrayList<Integer> result = new ArrayList<>();
		int[] arr = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		for(int i=N;i>0;i--) {
			result.add(arr[i], i);
		}

		for(int i : result) {
			System.out.print(i+" ");
		}
	}

}
