package algo.twoPointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_11728_배열합치기 {
	
	static int N,M;
	static ArrayList<Integer> A;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		A = new ArrayList<Integer>();
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			int num = Integer.parseInt(st.nextToken());
			A.add(num);
		} // input A
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			int num = Integer.parseInt(st.nextToken());
			A.add(num);
		} // input B
		
		Collections.sort(A);
		
		for(int i=0;i<A.size();i++) {
			sb.append(A.get(i)+" ");
		}
		
		System.out.println(sb.toString());
	}

}
