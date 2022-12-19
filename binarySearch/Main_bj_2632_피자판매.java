package algo.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_2632_피자판매 {
	
	static int p,m,n,result;
	static int[] A,B;
	static boolean[] check;
	static ArrayList<Integer> Alist = new ArrayList<>();
	static ArrayList<Integer> Blist = new ArrayList<>();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		p = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine());
		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());

		A = new int[m];
		B = new int[n];
		
		for(int i=0;i<m;i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		
		for(int i=0;i<n;i++) {
			B[i] = Integer.parseInt(br.readLine());
		} // input end
		
		for(int i=0;i<m;i++) {
			check = new boolean[m];
			check[i] = true;
			getSum(A[i], i, i+1, check, A, Alist);
		}
		
		for(int i=0;i<n;i++) {
			check = new boolean[n];
			check[i] = true;
			getSum(B[i], i, i+1, check, B, Blist);
		}
		
		Alist.add(0);
		Blist.add(0);
		
		Collections.sort(Alist);
		Collections.sort(Blist);
		
		int left = 0;
		int right = Blist.size()-1;
		
		while(left < Alist.size() && right >= 0) {
			
			int lv = Alist.get(left);
			int rv = Blist.get(right);
			
			if(lv + rv == p) {
				int lc = 0;
				int rc = 0;
				
				while(left < Alist.size() && Alist.get(left) == lv) {
					lc++;
					left++;
				}
				
				while(right >= 0 &&Blist.get(right) == rv) {
					rc++;
					right++;
				}
				
				result += lc * rc;
			}
			
			if(lv + rv > p) right--;
			if(lv + rv < p) left++;
		}
		
		System.out.println(result);
	}
	
	private static void getSum(int sum, int startIdx, int idx, boolean[] check, int[] arr, ArrayList<Integer> list) {
		
		if(idx == arr.length) {
			idx = 0;
		}
		
		list.add(sum);
		
		if(!check[idx] && sum <= p && idx != startIdx-1) {
			check[idx] = true;
			getSum(sum+arr[idx], startIdx, idx+1, check, arr, list);
		} else return;
	}
}
