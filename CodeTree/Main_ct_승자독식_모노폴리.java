package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_ct_승자독식_모노폴리 {
	
	static int N,M,K,result;
	static int[][] board;
	static int[][][] turnBoard;
	// up down left right
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int[][][] dirList;
	static TreeMap<Integer, Player> playerMap;
	static class Player {
		int x,y;
		int d;
		public Player(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new int[N][N];
		turnBoard = new int[N][N][2];
		playerMap = new TreeMap<>();
		dirList = new int[M+1][4][4];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] > 0)	{
					turnBoard[i][j][0] = board[i][j];
					turnBoard[i][j][1] = K;
					playerMap.put(board[i][j], new Player(i, j, 0));
				}
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=M;i++) {
			int dir = Integer.parseInt(st.nextToken())-1;
			playerMap.get(i).d = dir;
		}
		
		for(int i=1;i<=M;i++) {
			for(int j=0;j<4;j++) {
				st = new StringTokenizer(br.readLine());
				for(int l=0;l<4;l++) {
					int dir = Integer.parseInt(st.nextToken())-1;
					dirList[i][j][l] = dir;
				}
			}
		}
		
		result = 0;
		
		solve();
		
		System.out.println(result > 1000 ? -1 : result);
	}

	private static void solve() {
		
		while(result++ < 1000) {
			
			// 1. 각 플레이어들 한 칸씩 이동
			move_player();
			
			// 2. 각 칸 독점계약 1 감소
			decrease_downBoard();
			
			for(int i : playerMap.keySet()) {
				Player temp = playerMap.get(i);
				
				turnBoard[temp.x][temp.y][0] = i;
				turnBoard[temp.x][temp.y][1] = K;
			}
			
			if(playerMap.size() == 1) {
				break;
			}
			
//			System.out.println("==================");
//			print();
		}
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static void decrease_downBoard() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(turnBoard[i][j][1] > 0) turnBoard[i][j][1]--;
				if(turnBoard[i][j][1] == 0) turnBoard[i][j][0] = 0;
			}
		}
	}

	private static void move_player() {
		
		// 우선 플레이어는 본인에게 인접한 상하좌우 4 칸 중 아무도 독점계약을 맺지 않은 칸으로 이동하고
		// 이때 이동할 수 있는 칸이 여러개일 수 있음으로 이동 우선순위에 따라 움직일 칸을 결정하게 됩니다.
		for(int i : playerMap.keySet()) {
			Player temp = playerMap.get(i);
			
			boolean flag = false;
			for(int dir : dirList[i][temp.d]) {
				int nx = temp.x + dx[dir];
				int ny = temp.y + dy[dir];
				
				if(!is_valid(nx,ny)) continue;
				
				if(turnBoard[nx][ny][0] == 0) {
					flag = true;
					temp.x = nx;
					temp.y = ny;
					temp.d = dir;
					break;
				}
			}
			
			// 만약 그러한 칸이 없을 경우에는 인접한 4방향 중 본인이 독점계약한 땅으로 이동합니다.
			if(!flag) {
				for(int dir : dirList[i][temp.d]) {
					int nx = temp.x + dx[dir];
					int ny = temp.y + dy[dir];
					
					if(!is_valid(nx, ny)) continue;
					
					if(turnBoard[nx][ny][0] == i) {
						temp.x = nx;
						temp.y = ny;
						temp.d = dir;
						break;
					}
				}
			}
			
		}
		// 모든 플레이어가 이동한 후 한 칸에 여러 플레이어가 있을 경우에는 
		// 가장 작은 번호를 가진 플레이어만 살아남고 나머지 플레이어는 게임에서 사라지게 됩니다.
		board = new int[N][N];
		ArrayList<Integer> removeList = new ArrayList<>();
		for(int i : playerMap.keySet()) {
			Player temp = playerMap.get(i);
			
			if(board[temp.x][temp.y] == 0) {
				board[temp.x][temp.y]= i;
//				turnBoard[temp.x][temp.y][0] = i;
//				turnBoard[temp.x][temp.y][1] = K;
				continue;
			}
			
			else if(board[temp.x][temp.y] > 0) {
				removeList.add(i);
			}
		}
		
		if(removeList.size() > 0) {
			for(int i : removeList) {
				playerMap.remove(i);
			}
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>= N) return false;
		return true;
	}

}
