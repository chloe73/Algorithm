package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1072_게임 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int X = Integer.parseInt(st.nextToken()); // 게임 횟수
		int Y = Integer.parseInt(st.nextToken()); // 이긴 게임
		int Z = calc(X, Y); // 승률
		
		int result = -1;
		// 최소 몇 판 더 해야 Z가 변하는지 구하기
		int left = 0;
		int right = 1000000001;
		while(left <= right) {
			int mid = (left+right) / 2;
			
			if(calc(X+mid, Y+mid) != Z) {
				result = mid;
				right = mid - 1;
			}
			else left = mid + 1;
		}
		
		System.out.println(result);
	}
	
	private static int calc(int x, int y) {
		return (int) ((long) y * 100 / x);
	}

}
