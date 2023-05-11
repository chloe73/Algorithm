package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_2931_가스관 {
	
	static int R,C,sx,sy,ex,ey;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	 //                                 ↑→       ↓→         
	static int[][] mx = { {0,0,0,0}, {-1,0,0,0}, {1,0,0,0}, {}, {}, {}, {}, {} };
	static int[][] my = { {0,0,0,0}, {0,1,0,0}, {0,1,0,0}, {}, {}, {}, {}, {} };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		for(int i=0;i<R;i++) {
			board[i] = br.readLine().toCharArray();
			for(int j=0;j<C;j++) {
				if(board[i][j] == 'M') {
					sx = i;
					sy = j;
				}
				if(board[i][j] == 'Z') {
					ex = i;
					ey = j;
				}
			}
		} // input end
		
		
	}

}
