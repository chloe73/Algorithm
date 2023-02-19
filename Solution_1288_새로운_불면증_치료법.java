package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_1288_새로운_불면증_치료법 {
	
	static int N,result;
	static boolean[] visited; // 0~9까지 숫자를 모두 봤는지 확인하기 위한 변수

	public static void main(String[] args) throws IOException{
		System.setIn(new FileInputStream("input/input_1288.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			
			visited = new boolean[10];
			result = 0;
			solve();
			
			sb.append("#"+tc+" "+result+"\n");
		}
		System.out.println(sb.toString());
	}

	private static void solve() {
		int k = 1;
		
		while(true) {
			if(check()) {
				result = N*(k-1);
				break;
			}
			
			int temp = k * N; 

			String sNum = temp + ""; // "1295"
			
			for(int i=0;i<sNum.length();i++) {
				char c = sNum.charAt(i);
				numCheck(c);
			}
			k++;
		}
	}
	
	private static void numCheck(char c) {
		
		switch (c) {
		case '0':
			visited[0] = true;
			break;
		case '1':
			visited[1] = true;
			break;
		case '2':
			visited[2] = true;
			break;
		case '3':
			visited[3] = true;
			break;
		case '4':
			visited[4] = true;
			break;
		case '5':
			visited[5] = true;
			break;
		case '6':
			visited[6] = true;
			break;
		case '7':
			visited[7] = true;
			break;
		case '8':
			visited[8] = true;
			break;
		case '9':
			visited[9] = true;
			break;
		}
	}
	
	private static boolean check() {
		boolean flag = true;
		
		for(int i=0;i<10;i++) {
			if(!visited[i]) {
				flag = false;
				break;
			}
		}
		
		return flag;
	}
}
