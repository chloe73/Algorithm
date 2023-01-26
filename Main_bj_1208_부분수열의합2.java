package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_1208_부분수열의합2 {
	
	static int N,S;
	static long result;
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
		
		result = 0;
		solve();
		
		if(S == 0) result -= 1;
		
		System.out.println(result);
	}

	private static void solve() {
		int pointL = 0;
		int pointR = rightList.size()-1;
		while(pointL < leftList.size() && pointR >= 0) {
			int valL = leftList.get(pointL);
			int valR = rightList.get(pointR);
			
			if(valL + valR == S) {
				long cntL = 0;
				long cntR = 0;
				
				//왼쪽리스트에서 같은 수 찾기
				while(pointL < leftList.size() && valL == leftList.get(pointL)) {
					cntL++;
					pointL++;
				}
				//오른쪽리스트에서 같은 수 찾기
				while(pointR >= 0 && valR == rightList.get(pointR)) {
					cntR++;
					pointR--;
				}
				result += cntL*cntR;
			}
			if(valL + valR < S) { 
				pointL++;
			}
			if(valL + valR > S) {
				pointR--;
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
