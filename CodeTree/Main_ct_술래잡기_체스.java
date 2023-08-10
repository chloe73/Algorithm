package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_ct_술래잡기_체스 {
	
	static int result;
	// 방향 d는 1부터 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗ 
	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
	static class Thief {
		int p; // 순서
		int d; // 방향
		int x,y;
		public Thief(int p, int d, int x, int y) {
			this.p = p;
			this.d = d;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int[][] board = new int[4][4];
		TreeMap<Integer, Thief> thiefMap = new TreeMap<>();
		
		for(int i=0;i<4;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<4;j++) {
				int p = Integer.parseInt(st.nextToken()); // 도둑 번호
				int d = Integer.parseInt(st.nextToken())-1;
				board[i][j] = p;
				thiefMap.put(p, new Thief(p, d, i, j));
			}
		} // input end
		
		result = 0;

		solve(0,0,0,thiefMap, board);
		
		System.out.println(result);
	}

	private static void solve(int tx, int ty, int sum, TreeMap<Integer, Thief> thiefMap, int[][] board) {
		
		TreeMap<Integer, Thief> tempThief = new TreeMap<>();
		int[][] tempBoard = new int[4][4];
		
		for(int i : thiefMap.keySet()) {
			tempThief.put(i, thiefMap.get(i));
		}
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				tempBoard[i][j] = board[i][j];
			}
		}
		
		sum += tempBoard[tx][ty];
		int td = tempThief.get(tempBoard[tx][ty]).d;
		tempThief.remove(tempBoard[tx][ty]);
		tempBoard[tx][ty] = 0;
		
		if(sum > result) result = sum;
		
		// 도둑말은 번호가 작은 순서대로 본인이 가지고 있는 이동 방향대로 이동합니다.
		for(int i : tempThief.keySet()) {
			// 한 번의 이동에 한 칸을 이동하며, 
			System.out.println("keySet : "+i);
			int x = tempThief.get(i).x;
			int y = tempThief.get(i).y;
			int d = tempThief.get(i).d;
			
			// 도둑 말은 이동할 수 있을 때까지 45도 반시계 회전하며 갈 수 있는 칸을 탐색합니다.
			int nx = x;
			int ny = y;
			boolean flag = false;
			for(int k=0;k<8;k++) {
				nx = x + dx[d];
				ny = y + dy[d];
				
				// 술래말이 있는 칸이나 격자를 벗어나는 곳으로는 이동할 수 없습니다.
				if(!is_valid(nx,ny) || (nx == tx && ny == ty)) {
					d = change_dir(d);
					continue;
				}
				
				// 빈 칸이나 다른 도둑말이 있는 칸은 이동할 수 있는 칸이고 
				if(tempBoard[nx][ny] == 0 || (tempBoard[nx][ny] > 0 && tempBoard[nx][ny] <= 16)) {
					flag = true;
					break;
				}
				
			}
			
			// 만약 이동할 수 있는 칸이 없다면 이동하지 않습니다.
			// 그 이외의 경우에는 칸을 이동합니다.
			if(flag) {
				tempThief.get(i).x = nx;
				tempThief.get(i).y = ny;
				tempThief.get(i).d = d;
				
				// 만약 해당 칸에 다른 도둑말이 있다면 해당 말과 위치를 바꿉니다.
				if(tempBoard[nx][ny] > 0 && tempBoard[nx][ny] <= 16) {
					int temp = tempBoard[nx][ny];
					tempThief.get(temp).x = x;
					tempThief.get(temp).y = y;
					tempBoard[nx][ny] = i;
					tempBoard[x][y] = temp;
				}
				
				else if(tempBoard[nx][ny] == 0) {
					tempBoard[nx][ny] = i;
					tempBoard[x][y] = 0;
				}
			}
		}
		
		// 술래말 이동
		for(int i=1;i<=3;i++) {
			int x = tx + dx[td] * i;
			int y = ty + dy[td] * i;
			if(!is_valid(x, y)) break;
			if(tempBoard[x][y] > 0) solve(x, y, sum, tempThief, tempBoard);
		}
		
	}

	private static int change_dir(int d) {
		if(d == 7) return 0;
		return d+1;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=4 || c>=4) return false;
		return true;
	}
}