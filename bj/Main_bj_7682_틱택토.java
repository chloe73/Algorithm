package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_7682_틱택토 {
	
	static int a,b,blank;
	static String[][] board;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			String input = br.readLine();
			
			if(input.equals("end")) break;
			
			board = new String[3][3];
			a = 0;
			b = 0;
			blank = 0;
			
			for(int i=0;i<9;i++) {
				String temp = input.charAt(i)+"";
				board[i/3][i%3] = temp;
				
				if(temp.equals("X")) a++;
				else if(temp.equals("O")) b++;
				else blank++;
			} // input check
			
			if(Math.abs(a-b) > 1) {
				sb.append("invalid").append("\n");
				continue;
			}
			
			if(b > a) {
				sb.append("invalid").append("\n");
				continue;
			}
			
			if(search()) sb.append("valid").append("\n");
			else sb.append("invalid").append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	private static boolean search() {
		
		boolean flag = false;
		
		for(int i=0;i<3;i++) {
			// row check
			String temp = board[i][0]; // X or O
			
			if(temp.equals(".")) break;
			
			boolean same = true;
			
			for(int j=1;j<3;j++) {
				
				if(!temp.equals(board[i][j])) {
					same = false;
					break;
				}
			}
			
			if(same) {
				if(blank > 0) { // 빈칸이 있을 때
					if(temp.equals("X")) {
						if(a > b && Math.abs(a-b) == 1) return true;
						else return false;
					}
					
					else if(temp.equals("O")) {
						if(a == b) return true;
						else return false;
					}					
				}
				
				else { // 빈칸이 없을 때
					if(temp.equals("O")) return false;
					if(a > b) return true;
					else return false;
				}
			}
		}
		
		for(int j=0;j<3;j++) {
			// col check
			String temp = board[0][j]; // X or O
			
			if(temp.equals(".")) break;
			
			boolean same = true;
			
			for(int i=1;i<3;i++) {
				
				if(!temp.equals(board[i][j])) {
					same = false;
					break;
				}
			}
			
			if(same) {
				if(blank > 0) { // 빈칸이 있을 때
					if(temp.equals("X")) {
						if(a > b && Math.abs(a-b) == 1) return true;
						else return false;
					}
					
					else if(temp.equals("O")) {
						if(a == b) return true;
						else return false;
					}					
				}
				
				else { // 빈칸이 없을 때
					if(temp.equals("O")) return false;
					if(a > b) return true;
					else return false;
				}
			}
		}
		
		// 대각선(오른쪽 아래로) 확인
		boolean same = true;
		
		String temp = board[0][0];
		int x = 0;
		int y = 0;
		
		if(!temp.equals(".")) {
			
			while(x++ < 2 && y++ < 2) {
				if(!temp.equals(board[x][y])) {
					same = false;
					break;
				}
			}
			
			if(same) {
				if(blank > 0) { // 빈칸이 있을 때
					if(temp.equals("X")) {
						if(a > b && Math.abs(a-b) == 1) return true;
						else return false;
					}
					
					else if(temp.equals("O")) {
						if(a == b) return true;
						else return false;
					}					
				}
				
				else { // 빈칸이 없을 때
					if(temp.equals("O")) return false;
					if(a > b) return true;
					else return false;
				}
			}
		}
		
		// 대각선(왼쪽 아래로) 확인
		temp = board[0][2];
		x = 0;y = 2;
		
		same = true;
		if(!temp.equals(".")) {
			
			while(x++ < 2 && y-- > 0) {
				if(!temp.equals(board[x][y])) {
					same = false;
					break;
				}
			}
			
			if(same) {
				if(blank > 0) { // 빈칸이 있을 때
					if(temp.equals("X")) {
						if(a > b && Math.abs(a-b) == 1) return true;
						else return false;
					}
					
					else if(temp.equals("O")) {
						if(a == b) return true;
						else return false;
					}					
				}
				
				else { // 빈칸이 없을 때
					if(temp.equals("O")) return false;
					if(a > b) return true;
					else return false;
				}
			}
		}
		
		if(blank == 0 && a == 5 && b == 4) return true;
		
		return flag;
	}
	
}
