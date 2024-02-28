package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.TreeSet;

public class Main_bj_2668_숫자고르기 {

	static int N;
	static boolean flag;
	static int[][] board;
	static boolean[] isChecked;
	static StringBuilder sb;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		board = new int[2][N];
		for(int i=0;i<N;i++) {
			board[0][i] = i+1;
		}
		
		for(int i=0;i<N;i++) {
			board[1][i] = Integer.parseInt(br.readLine());
		} // input end
		
		isChecked = new boolean[N];
		flag = false;
		for(int i=N;i>=1;i--) {
			solve(0, 0, i);
			if(flag)
				break;
		}
		
		System.out.println(sb.toString());
	}

	private static void solve(int idx, int cnt, int num) {
		
		if(cnt == num) {
			if(check()) flag = true;
			return;
		}
		
		for(int i=idx;i<N;i++) {
			isChecked[i] = true;
			solve(i+1, cnt+1, num);
			isChecked[i] = false;
		}
	}
	
	private static boolean check() {
		
		TreeSet<Integer> aSet = new TreeSet<>();
		HashSet<Integer> bSet = new HashSet<>();
		
		for(int i=0;i<N;i++) {
			if(isChecked[i]) {
				aSet.add(board[0][i]);
				bSet.add(board[1][i]);
			}
		}
		
		for(int i : aSet) {
			if(!bSet.contains(i)) return false;
		}
		
		sb.append(aSet.size()+"\n");
		for(int i : aSet) {
			sb.append(i+"\n");
		}
		
		return true;
	}
}
