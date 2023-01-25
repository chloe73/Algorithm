package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_1208_부분수열의합2 {
	
	static int N,S,result;
	static int[] arr;
	static ArrayList<Integer> leftList, rightList;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		leftList = new ArrayList<>();
		rightList = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		makeSum(0, 0, N/2, leftList);
		makeSum(0, N/2, N, rightList);
		
		Collections.sort(leftList);
		Collections.sort(rightList);
		
		solve();
		
		if(S == 0) result -= 1;
		
		System.out.println(result);
	}

	private static void solve() {
		int left = 0;
		int right = rightList.size()-1;
		
		while(left < leftList.size() && right >= 0) {
			int valL = leftList.get(left);
			int valR = rightList.get(right);
			
			if(valL +valR == S) {
				long cntL = 0;
				long cntR = 0;
				
				while(left < leftList.size() && valL == leftList.get(left)) {
					cntL++;
					left++;
				}
				
				while(right >= 0 && valR == rightList.get(right)) {
					cntR++;
					right--;
				}
				
				result += cntL * cntR;
			}
			
			if(valL + valR < S) {
				left++;
			}
			
			if(valL + valR > S) {
				right--;
			}
		}
	}

	private static void makeSum(int sum, int start, int end, ArrayList<Integer> list) {
		if(start == end) {
			list.add(sum);
			return;
		}
		
		makeSum(sum, start+1, end, list);
		makeSum(sum+arr[start], start+1, end, list);
	}
}
