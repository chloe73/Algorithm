package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_20061_모노미노도미노2 {
	
	static int N,score,totalBlockCnt;
	static int[][] red,green,blue;
	static class Block {
		int t, x, y;
		public Block(int t, int x, int y) {
			this.t = t;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		red = new int[4][4];
		green = new int[6][4];
		blue = new int[4][6];
		
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			add_block(i,t,x,y);
//			System.out.println("========== "+i+" ==========");
//			print();
//			System.out.println("score :" + score);
		}
		
		count_result();
//		print();
		System.out.println(score);
		System.out.println(totalBlockCnt);
	}
	
	private static void print() {
		System.out.println("===== green =====");
		for(int i=0;i<6;i++) {
			for(int j=0;j<4;j++) {
				System.out.print(green[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("===== blue =====");
		for(int i=0;i<4;i++) {
			for(int j=0;j<6;j++) {
				System.out.print(blue[i][j]+"\t");
			}
			System.out.println();
		}
	}

	private static void count_result() {
		
		// green 보드 check
		for(int i=0;i<6;i++) {
			for(int j=0;j<4;j++) {
				if(green[i][j] > 0) totalBlockCnt++;
			}
		}
		
		// blue 보드 check
		for(int i=0;i<4;i++) {
			for(int j=0;j<6;j++) {
				if(blue[i][j] > 0) totalBlockCnt++;
			}
		}
	}

	private static void add_block(int num, int t, int x, int y) {
		
		if(t == 1) {
			// green 이동 -> y 체크
			int row = -1;
			for(int i=0;i<5;i++) {
				if(green[i][y] == 0 && green[i+1][y] == 0) {
					row = i+1;
					continue;
				}
				else if(green[i][y] == 0 && green[i+1][y] != 0) {
					row = i;
					break;
				}
			}
			green[row][y] = num;
			
			// blue 이동 -> x행 체크
			int col = -1;
			for(int j=0;j<5;j++) {
				if(blue[x][j] == 0 && blue[x][j+1] == 0) {
					col = j+1;
					continue;
				}
				else if(blue[x][j] == 0 && blue[x][j+1] != 0) {
					col = j;
					break;
				}
			}
			blue[x][col] = num;
		}
		
		else if(t == 2) { // (x,y) & (x,y+1)
			// green 이동 -> y, y+1 check!
			int row = -1;
			for(int i=0;i<5;i++) {
				if(green[i][y] == 0 && green[i][y+1] ==0) {
					if(green[i+1][y] == 0 && green[i+1][y+1] == 0) {
						row = i+1;
						continue;
					}
					else break;
				}
			}
			green[row][y] = num;
			green[row][y+1] = num;
			
			// blue 이동 -> x행 check !
			int col = -1;
			for(int j=0;j<5;j++) {
				if(blue[x][j] == 0 && blue[x][j+1] == 0) {
					col = j;
					continue;
				}
				else break;
			}
			blue[x][col] = num;
			blue[x][col+1] = num;
		}
		
		else if(t == 3) { // (x,y) & (x+1,y)
			// green 이동 -> 
			int row = -1;
			outer:for(int i=0;i<5;i++) {
				if(green[i][y] == 0 && green[i+1][y] == 0) {
					row = i;
					continue;
				}
				else {
					break outer;
				}
			}
			green[row][y] = num;
			green[row+1][y] = num;
			
			// blue 이동 -> 
			int col = -1;
			outer:for(int j=0;j<5;j++) {
				if(blue[x][j] == 0 && blue[x+1][j] == 0) {
					if(blue[x][j+1] == 0 && blue[x+1][j+1] == 0) {
						col = j+1;
						continue;
					}
					else {
						col = j;
						break outer;
					}
				}
			}
			blue[x][col] = num;
			blue[x+1][col] = num;
		}
		
		check_green();
		
		check_blue();
	}

	private static void check_blue() {
		
		int count = 4;
		int col = 5;
		
		while(count-- > 0) {
			if(blue[0][col] > 0 && blue[1][col]>0 && blue[2][col]>0 && blue[3][col]>0) {
				score++;
				
				blue[0][col] = 0;
				blue[1][col] = 0;
				blue[2][col] = 0;
				blue[3][col] = 0;
				
				for(int k=col-1;k>=0;k--) {
					for(int j=0;j<4;j++) {
						blue[j][k+1] = blue[j][k];
						blue[j][k] = 0;
					}
				}
				col++;

			}
			// 해당 열이 다 채워진 행이 아니면 col--
			col--;
		}

		// 0,1번 행 특별한 칸 : check !
		int cnt = 0;
		for(int j=0;j<2;j++) {
			for(int i=0;i<4;i++) {
				if(blue[i][j] > 0) {
					cnt++;
					break;
				}
			}
		}
		
		if(cnt > 0) {
			for(int k=5;k>=2;k--) { // 열
				for(int i=0;i<4;i++) { // 행
					blue[i][k] = blue[i][k-cnt];
				}
			}
			
			// 초기화
			for(int j=0;j<2;j++) {
				for(int i=0;i<4;i++) {
					blue[i][j] = 0;
				}
			}
		}
		
	}

	private static void check_green() {

		int count = 4;
		int row = 5;
		
		while(count-- > 0) {
			if(green[row][0] > 0 && green[row][1]>0 && green[row][2]>0 && green[row][3]>0) {
				score++;
				
				green[row][0] = 0;
				green[row][1] = 0;
				green[row][2] = 0;
				green[row][3] = 0;
				
				for(int k=row-1;k>=0;k--) {
					for(int j=0;j<4;j++) {
						green[k+1][j] = green[k][j];
						green[k][j] = 0;
					}
				}
				row++;

			}
			// 현재 행이 다 안 채워져 있다면 row--
			row--;
		}

		// 0,1번 행 특별한 칸 : check !
		int cnt = 0;
		for(int i=0;i<2;i++) {
			for(int j=0;j<4;j++) {
				if(green[i][j] > 0) {
					cnt++;
					break;
				}
			}
		}
		
		if(cnt > 0) {
			
			for(int k=5;k>=2;k--) { // 행
				for(int j=0;j<4;j++) { // 열
					green[k][j] = green[k-cnt][j];
				}
			}
			
			for(int i=0;i<2;i++) {
				for(int j=0;j<4;j++) {
					green[i][j] = 0;
				}
			}
		}
	}

}