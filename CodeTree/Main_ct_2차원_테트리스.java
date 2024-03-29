package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_2차원_테트리스 {
	
	static int K,score,totalSum;
	static boolean[][] yellow,red;
	static ArrayList<Tile> cmdList;
	static class Tile {
		int t,x,y;
		public Tile(int t, int x, int y) {
			this.t = t;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		K = Integer.parseInt(br.readLine());
		
		cmdList = new ArrayList<>();
		yellow = new boolean[6][4];
		red = new boolean[4][6];
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			cmdList.add(new Tile(t, x, y));
		} // input end
		
		solve();
		
		System.out.println(score);
		System.out.println(totalSum);
	}

	private static void solve() {
		
		for(int k=0;k<K;k++) {
			Tile temp = cmdList.get(k);
			
			// yellow board와 red board에 각 타일 위치 표시
			mark_tile(temp.t, temp.x, temp.y);
			
			// yellow : 행의 모든 칸이 다 채워졌으면 없애고 1점 획득 후, 위에 칸들 하나씩 내려옴.
			check_yellow();
			
			// red : 열의 모든 칸이 다 채워졌으면 없애고 1점 획득 후, 위에 칸들 하나씩 내려옴.
			check_red();
			
			// yellow 연한 부분에 행을 차지하고 있는 칸이 몇칸인지 확인 후, 그 수만큼 가장 끝 행의 타일 사라짐.
			check_light_yellow();
			// red 연한 부분에 열을 차지하고 있는 칸이 몇칸인지 확인 후, 그 수만큼 가장 끝 열의 타일 사라짐.
			check_light_red();
			// 사라지고 사라진만큼 칸이 땡겨짐.
		}
		
		get_total_sum();
	}

	private static void check_light_red() {
		// 여기선 점수 획득 아님.
		int cnt = 0;
		
		for(int j=0;j<2;j++) {
			boolean flag = false;
			
			for(int i=0;i<4;i++) {
				if(red[i][j]) {
					flag = true;
					break;
				}
			}
			
			if(flag) cnt++;
		}
		
		if(cnt == 0) return;
		
		// cnt 개수만큼 맨 밑에 열부터 타일 삭제
		int col = 5;
		for(int a=0;a<cnt;a++) {
			for(int i=0;i<4;i++) {
				if(!red[i][col]) continue;
				red[i][col] = false;
			}
			col--;
		}
		
		for(int j=5-cnt;j>=0;j--) {
			for(int i=0;i<4;i++) {
				if(red[i][j]) {
					red[i][j+cnt] = true;
					red[i][j] = false;
				}
			}
		}
	}

	private static void check_light_yellow() {
		// 여기선 점수 획득 아님.
		int cnt = 0;
		
		// 타일들이 차지하고 있는 행의 개수만큼 맨 밑 행부터 타일들이 사라진다.
		for(int i=0;i<2;i++) {
			boolean flag = false;
			for(int j=0;j<4;j++) {
				if(yellow[i][j]) {
					flag = true;
					break;
				}
			}
			
			if(flag) cnt++;
		}
		
		if(cnt == 0) return;
		
		// cnt 개수만큼 맨 밑에 행부터 타일 삭제
		int row = 5;
		for(int a=0;a<cnt;a++) {
			for(int j=0;j<4;j++) {
				if(!yellow[row][j]) continue;
				yellow[row][j] = false;
			}
			row--;
		}
		
		for(int i=5-cnt;i>=0;i--) {
			for(int j=0;j<4;j++) {
				if(yellow[i][j]) {
					yellow[i+cnt][j] = true;
					yellow[i][j] = false;
				}
			}
		}
	}

	private static void get_total_sum() {
		for(int i=0;i<6;i++) {
			for(int j=0;j<4;j++) {
				if(yellow[i][j]) totalSum++;
			}
		}
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<6;j++) {
				if(red[i][j]) totalSum++;
			}
		}
	}

	private static void check_red() {
		
		for(int j=2;j<6;j++) {
			boolean flag = true;
			
			for(int i=0;i<4;i++) {
				if(!red[i][j]) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				score++;
				down_col_tile(j);
			}
		}
	}

	private static void down_col_tile(int col) {
		
		// 모두 채워진 열 타일 삭제
		for(int i=0;i<4;i++) {
			red[i][col] = false;
		}
		
		for(int j=col-1;j>=0;j--) {
			for(int i=0;i<4;i++) {
				if(red[i][j]) {
					red[i][j+1] = true;
					red[i][j] = false;
				}
			}
		}
	}

	private static void check_yellow() {
		// 2행부터 확인함.
		for(int i=2;i<6;i++) {
			boolean flag = true;
			
			for(int j=0;j<4;j++) {
				if(!yellow[i][j]) {
					flag = false;
					break;
				}
			}
			
			// 한 행이 모두 채워진 상태라면 해당 행 삭제 후, 위에 타일들 한 칸씩 내려옴.
			if(flag) {
				score++;
				down_row_tile(i);
			}
		}
	}

	private static void down_row_tile(int row) {
		
		// 모두 채워진 행 타일 삭제
		for(int j=0;j<4;j++) {
			yellow[row][j] = false;
		}
		
//		if(row == 0) return;
		
		for(int i=row-1;i>=0;i--) {
			for(int j=0;j<4;j++) {
				if(yellow[i][j]) {
					yellow[i+1][j] = true;
					yellow[i][j] = false;
				}
			}
		}
	}

	// 타일을 yellow, red board에 놓을 때, 0번째 행과 열부터 확인해야 함.
	private static void mark_tile(int t, int x, int y) {
		
		if(t == 1) {
			// yellow board
			for(int i=0;i<4;i++) {
				// 다음 칸에 타일을 채울 수 있으면 기존 칸에 채워진 타일 없애고 다음 칸에 표시하기
				if(!yellow[i+1][y]) {
					yellow[i][y] = false;
					yellow[i+1][y] = true;
					continue;
				}
				
				// 다음 칸에는 넣을 수 없고 현재 칸에 넣을 수 있다면
				if(!yellow[i][y] && yellow[i+1][y]) {
					yellow[i][y] = true;
					break;
				}
				
				// 현재 칸에 넣을 수 없으면 stop
				if(yellow[i][y]) break;
			}
			
			// red board
			for(int j=0;j<4;j++) {
				// 다음 칸에 타일을 채울 수 있으면 기존 칸에 채워진 타일 없애고 다음 칸에 표시하기
				if(!red[x][j+1]) {
					red[x][j] = false;
					red[x][j+1] = true;
					continue;
				}
				// 다음 칸에는 넣을 수 없고 현재 칸에 넣을 수 있다면
				if(!red[x][j] && red[x][j+1]) {
					red[x][j] = true;
					break;
				}
				// 현재 칸에 넣을 수 없으면 stop
				if(red[x][j]) break;
			}
		}
		else if(t == 2) { // 가로 두칸
			// yellow board
			for(int i=0;i<4;i++) {
				// 다음 칸에 타일을 채울 수 있으면 기존 칸에 채워진 타일 없애고 다음 칸에 표시하기
				if(!yellow[i+1][y] && !yellow[i+1][y+1]) {
					yellow[i][y] = false;
					yellow[i][y+1] = false;
					yellow[i+1][y] = true;
					yellow[i+1][y+1] = true;
					continue;
				}
				
				// 다음 칸에는 넣을 수 없고 현재 칸에 넣을 수 있다면
				if( (!yellow[i][y] && !yellow[i][y+1]) && (yellow[i+1][y] && yellow[i+1][y+1])) {
					yellow[i][y] = true;
					yellow[i][y+1] = true;
					break;
				}
				
				// 현재 칸에 넣을 수 없으면 stop
				if(yellow[i][y] && yellow[i][y+1]) break;
			}
			
			// red board
			for(int j=0;j<3;j++) { // 가로 두칸
				// 다음 칸에 타일을 채울 수 있으면 기존 칸에 채워진 타일 없애고 다음 칸에 표시하기
				if(!red[x][j] && !red[x][j+1] && !red[x][j+2]) {
					red[x][j+1] = true;
					red[x][j+2] = true;
					continue;
				}
				// 다음 칸에는 넣을 수 없고 현재 칸에 넣을 수 있다면
				if(!red[x][j] && !red[x][j+1] && red[x][j+2]) {
					red[x][j] = true;
					red[x][j+1] = true;
					break;
				}
				// 현재 칸에 넣을 수 없으면 stop
				if(red[x][j]) break;
			}
		}
		else if(t == 3) { // 세로 두칸
			// yellow board => row
			for(int i=0;i<3;i++) {
				// 1) 현재 위치에 타일을 놓을 수 있고 다음 위치에도 타일을 놓을 수 있는 경우
				if((!yellow[i][y] && !yellow[i+1][y]) && !yellow[i+2][y]) {
					yellow[i+1][y] = true;
					yellow[i+2][y] = true;
					continue;
				}
				
				// 2) 현재 위치에 타일을 놓을 수 있지만 다음 위치에는 타일을 놓을 수 없는 경우
				if((!yellow[i][y] && !yellow[i+1][y]) && yellow[i+2][y]) {
					yellow[i][y] = true;
					yellow[i][y+1] = true;
					break;
				}
			}
			
			// red board => row
			for(int j=0;j<4;j++) {
				
				// 1) 
				if((!red[x][j] && !red[x+1][j]) && (!red[x+1][j] && !red[x+1][j+1])) {
					
				}
			}
		}
	}

}