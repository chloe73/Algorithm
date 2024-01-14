package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_ct_2020_fh_am_1_2차원_테트리스 {
	
	static int K,score,blockCnt;
	static boolean[][] yellow,red;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		K = Integer.parseInt(br.readLine());
		yellow = new boolean[6][4];
		red = new boolean[4][6];
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			add_block(t,x,y);
			
			// 삭제되는 행,열 없는지 확인 => 점수 획득
			check_full_block();
			
			// 연한 부분에 블록이 있는지 확인
			check_light_board();
			
//			print();
		}

		System.out.println(score);
		System.out.println(blockCnt);
	}
	
	private static void check_light_board() {
		
		// yellow 보드 확인
		int yellowCnt = 0;
		for(int i=0;i<2;i++) {
			boolean flag = false;
			for(int j=0;j<4;j++) {
				if(yellow[i][j]) {
					flag = true;
					break;
				}
			}
			if(flag) yellowCnt++;
		}
		
		// yellowCnt개수만큼 행이 사라짐
		if(yellowCnt > 0) {
			
			int row = 5;
			for(int k=0;k<yellowCnt;k++) {
				for(int j=0;j<4;j++) {
					if(yellow[row-k][j]) blockCnt--;
					yellow[row-k][j] = false;
				}
			}
			
			for(int i=5-yellowCnt;i>=0;i--) {
				for(int j=0;j<4;j++) {
					if(yellow[i][j] && !yellow[i+yellowCnt][j]) {
						yellow[i+yellowCnt][j] = true;
						yellow[i][j] = false;
					}
				}
			}
		}
		
		// red 보드 확인
		int redCnt = 0;
		for(int j=0;j<2;j++) {
			boolean flag = false;
			for(int i=0;i<4;i++) {
				if(red[i][j]) {
					flag = true;
					break;
				}
			}
			if(flag) redCnt++;
		}
		
		// redCnt 개수만큼 열이 사라짐
		if(redCnt > 0) {
			
			int col = 5;
			for(int k=0;k<redCnt;k++) {
				for(int i=0;i<4;i++) {
					if(red[i][col-k]) blockCnt--;
					red[i][col-k] = false;
				}
			}
			
			for(int j=5-redCnt;j>=0;j--) {
				for(int i=0;i<4;i++) {
					if(red[i][j] && !red[i][j+redCnt]) {
						red[i][j+redCnt] = true;
						red[i][j] = false;
					}
				}
			}
		}
		
		
	}

	private static void check_full_block() {
		// yellow 보드 확인
		
		while(true) {
			int num = is_yellow_full();
			
			if(num == -1) break;
			
			// num 행이 꽉 차서 해당 행이 지워지고 위에 있는 블록들이 한칸씩 내려온다.
			for(int j=0;j<4;j++) {
				if(yellow[num][j]) blockCnt--;
				yellow[num][j] = false;
			}
			
			for(int i=num;i>0;i--) {
				for(int j=0;j<4;j++) {
					if(yellow[i-1][j]) {
						yellow[i][j] = true;
						yellow[i-1][j] = false;
						continue;
					}
				}
			}
			
			score++;
		}
		
		// red 보드 확인
		while(true) {
			int num = is_red_full();
			
			if(num == -1) break;
			
			// num 열이 꽉 차서 해당 열이 지워지고 위에 있는 블록들이 한칸씩 내려온다.
			for(int i=0;i<4;i++) {
				if(red[i][num]) blockCnt--;
				red[i][num] = false;
			}
			
			for(int j=num;j>0;j--) {
				for(int i=0;i<4;i++) {
					if(red[i][j-1]) {
						red[i][j] = true;
						red[i][j-1] = false;
						continue;
					}
				}
			}
			
			score++;
		}
	}
	
	private static int is_red_full() {
		
		for(int j=5;j>1;j--) {
			boolean flag = true;
			for(int i=0;i<4;i++) {
				if(!red[i][j]) {
					flag = false;
					break;
				}
			}
			if(flag) return j;
		}
		
		return -1;
	}
	
	private static int is_yellow_full() {
		
		for(int i=5;i>1;i--) {
			boolean flag = true;
			for(int j=0;j<4;j++) {
				if(!yellow[i][j]) {
					flag = false;
					break;
				}
			}
			if(flag) return i;
		}
		
		return -1;
	}

	private static void print() {
		System.out.println("===== yellow =====");
		for(int i=0;i<6;i++) {
			for(int j=0;j<4;j++) {
				System.out.print(yellow[i][j] ? "O " : "X ");
			}
			System.out.println();
		}
		
		System.out.println("===== red =====");
		for(int i=0;i<4;i++) {
			for(int j=0;j<6;j++) {
				System.out.print(red[i][j]?"O ":"X ");
			}
			System.out.println();
		}
	}

	private static void add_block(int t, int x, int y) {
		
		if(t == 1) {
			// yellow 보드에 block 놓기
			int targetRow = 0;
			for(int i=0;i<6;i++) {
				if(yellow[i][y]) {
					break;
				}
				targetRow = i;
			}
			yellow[targetRow][y] = true;
			blockCnt++;
			
			// red 보드에 block 놓기
			int targetCol = 0;
			for(int j=0;j<6;j++) {
				if(red[x][j]) {
					break;
				}
				targetCol = j;
			}
			red[x][targetCol] = true;
			blockCnt++;
		}
		else if(t == 2) {
			// type 2 : 가로2칸
			// yellow 보드에 block 놓기
			int targetRow = 0;
			for(int i=0;i<6;i++) {
				if(yellow[i][y] || yellow[i][y+1]) break;
				targetRow = i;
			}
			yellow[targetRow][y] = true;
			yellow[targetRow][y+1] = true;
			blockCnt += 2;
			
			// red 보드에 block 놓기
			int targetCol = 0;
			for(int j=0;j<5;j++) {
				if(red[x][j]) break;
				if(!red[x][j] && !red[x][j+1]) {
					targetCol = j;
				}
				if(!red[x][j] && red[x][j+1]) break;
			}
			red[x][targetCol] = true;
			red[x][targetCol+1] = true;
			blockCnt += 2;
		}
		else {
			// type 3 : 세로 2칸
			// yellow 보드에 block 놓기
			int targetRow = 0;
			for(int i=0;i<5;i++) {
				if(yellow[i][y]) break;
				if(!yellow[i][y] && yellow[i+1][y]) break;
				if(!yellow[i][y] && !yellow[i+1][y]) {
					targetRow = i;
				}
			}
			yellow[targetRow][y] = true;
			yellow[targetRow+1][y] = true;
			blockCnt += 2;
			
			// red 보드에 block 놓기
			int targetCol = 0;
			for(int j=0;j<6;j++) {
				if(red[x][j]) break;
				if(red[x+1][j]) break;
				if(!red[x][j] && !red[x+1][j]) {
					targetCol = j;
				}
			}
			red[x][targetCol] = true;
			red[x+1][targetCol] = true;
			blockCnt += 2;
		}
	}

}
