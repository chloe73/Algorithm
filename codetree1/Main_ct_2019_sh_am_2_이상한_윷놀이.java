package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_ct_2019_sh_am_2_이상한_윷놀이 {
	
	static int N,K,result;
	static int[][] board;
	static ArrayList<Horse> hList;
	static Queue<Integer>[][] hBoard;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {1,-1,0,0};
	static class Horse {
		int idx;
		int x,y,d;
		public Horse(int idx, int x, int y, int d) {
			this.idx = idx;
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
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		hBoard = new LinkedList[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				hBoard[i][j] = new LinkedList<>();
			}
		}
		
		hList = new ArrayList<>();
		for(int i=1;i<=K;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			
			hBoard[x][y].add(i-1);
			hList.add(new Horse(i, x, y, d));
		} // input end
		
		solve();
		
		System.out.println(result);
	}
	
	private static void solve() {
		int turn = 0;
		
		outer:while(++turn <= 1000) {
			// 1~k번 말 순서대로 규칙대로 이동한다.
			for(Horse temp : hList) {
				// 현재 위치
				int x = temp.x;
				int y = temp.y;
				// 이동하려는 위치
				int nx = temp.x + dx[temp.d];
				int ny = temp.y + dy[temp.d];
				// 현재 이동하려는 말의 방향
				int d = temp.d;
				
				// 격자판의 범위를 벗어나는 이동일 경우 파란색으로 이동하려는 것과 똑같이 생각하여 처리해줍니다.
				if(!is_valid(nx, ny) || board[nx][ny] == 2) {
					// 이동하려는 칸이 파란색일 경우에는 이동하지 않고 방향을 반대로 전환한 뒤 이동합니다.
					d = change_dir(d);
					nx = x + dx[d];
					ny = y + dy[d];
					// 만일 반대 방향으로 전환한 뒤 이동하려는 칸도 파란색이라면 방향만 반대로 전환한 뒤 이동하지 않고 가만히 있습니다.
					if(!is_valid(nx, ny) || board[nx][ny] == 2) {
						temp.d = d;
						continue;
					}
					else if(board[nx][ny] == 0) {
						// 말이 이동하려는 칸이 흰색인 경우에는 해당 칸으로 이동합니다.
						// 이동하려는 칸에 말이 이미 있는 경우에는 해당 말 위에 이동하려던 말을 올려둡니다.
						// 이미 말이 올려져 있는 상태에도 말을 올릴 수 있습니다.
						boolean flag = false;
						int size = hBoard[x][y].size();
						for(int i=0;i<size;i++) {
							int index = hBoard[x][y].poll();
							Horse h = hList.get(index);
							if(h.idx == temp.idx) {
								flag = true;
								hList.get(h.idx-1).x = nx;
								hList.get(h.idx-1).y = ny;
								hList.get(h.idx-1).d = d;
								hBoard[nx][ny].add(index);
								continue;
							}
							if(flag) {
								hList.get(h.idx-1).x = nx;
								hList.get(h.idx-1).y = ny;
								hBoard[nx][ny].add(index);
								continue;
							}
							// 현재 이동하려는 말 위에 쌓여있지 않은 말들은 다시 현재 board에 넣어줘야 한다.
							hBoard[x][y].add(index);
						}
						
						// 이동 후, 이동한 칸에 말이 4개 이상 겹쳐있는지 확인
						if(hBoard[nx][ny].size()>=4) break outer;
					}
					else if(board[nx][ny] == 1) {
						// 이동하려는 칸이 빨간색인 경우에는 해당 칸으로 이동하기 전 순서를 뒤집습니다.
						// 이후 해당 칸에 말이 있는 경우에는 흰색 칸과 같이 그 위에 쌓아둡니다.
						boolean flag = false;
						Stack<Integer> stack = new Stack<>();
						int size = hBoard[x][y].size();
						for(int i=0;i<size;i++) {
							int index = hBoard[x][y].poll();
							Horse h = hList.get(index);
							if(h.idx == temp.idx) {
								flag = true;
								hList.get(h.idx-1).x = nx;
								hList.get(h.idx-1).y = ny;
								hList.get(h.idx-1).d = d;
								stack.push(index);
								continue;
							}
							if(flag) {
								hList.get(h.idx-1).x = nx;
								hList.get(h.idx-1).y = ny;
								stack.push(index);
								continue;
							}
							hBoard[x][y].add(index);
						}
						
						while(!stack.isEmpty()) {
							hBoard[nx][ny].add(stack.pop());
						}
						
						// 이동 후, 이동한 칸에 말이 4개 이상 겹쳐있는지 확인
						if(hBoard[nx][ny].size()>=4) break outer;
					}
				}
				else if(board[nx][ny] == 0) {
					// 말이 이동하려는 칸이 흰색인 경우에는 해당 칸으로 이동합니다.
					// 이동하려는 칸에 말이 이미 있는 경우에는 해당 말 위에 이동하려던 말을 올려둡니다.
					// 이미 말이 올려져 있는 상태에도 말을 올릴 수 있습니다.
					boolean flag = false;
					int size = hBoard[x][y].size();
					for(int i=0;i<size;i++) {
						int index = hBoard[x][y].poll();
						Horse h = hList.get(index);
						if(h.idx == temp.idx) {
							flag = true;
							hList.get(h.idx-1).x = nx;
							hList.get(h.idx-1).y = ny;
							hBoard[nx][ny].add(index);
							continue;
						}
						if(flag) {
							hList.get(h.idx-1).x = nx;
							hList.get(h.idx-1).y = ny;
							hBoard[nx][ny].add(index);
							continue;
						}
						// 현재 이동하려는 말 위에 쌓여있지 않은 말들은 다시 현재 board에 넣어줘야 한다.
						hBoard[x][y].add(index);
					}
					
					// 이동 후, 이동한 칸에 말이 4개 이상 겹쳐있는지 확인
					if(hBoard[nx][ny].size()>=4) break outer;
				}
				else if(board[nx][ny] == 1) {
					// 이동하려는 칸이 빨간색인 경우에는 해당 칸으로 이동하기 전 순서를 뒤집습니다.
					// 이후 해당 칸에 말이 있는 경우에는 흰색 칸과 같이 그 위에 쌓아둡니다.
					boolean flag = false;
					Stack<Integer> stack = new Stack<>();
					int size = hBoard[x][y].size();
					for(int i=0;i<size;i++) {
						int index = hBoard[x][y].poll();
						Horse h = hList.get(index);
						if(h.idx == temp.idx) {
							flag = true;
							hList.get(h.idx-1).x = nx;
							hList.get(h.idx-1).y = ny;
							stack.push(index);
							continue;
						}
						if(flag) {
							hList.get(h.idx-1).x = nx;
							hList.get(h.idx-1).y = ny;
							stack.push(index);
							continue;
						}
						hBoard[x][y].add(index);
					}
					
					while(!stack.isEmpty()) {
						hBoard[nx][ny].add(stack.pop());
					}
					
					// 이동 후, 이동한 칸에 말이 4개 이상 겹쳐있는지 확인
					if(hBoard[nx][ny].size()>=4) break outer;
				}
				
			}
		}
		
		if(turn > 1000)
			turn = -1;
		
		result = turn;
		
		return;
	}
	
	private static int change_dir(int d) {
		if(d == 0) return 1;
		if(d == 1) return 0;
		if(d == 2) return 3;
		return 2;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
