package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_ct_이상한_윷놀이 {
	
	static int N,K,result;
	static int[][] board;
	static Queue<Integer>[][] qBoard;
	static ArrayList<Horse> horseList;
	static int[] dx = {0,0,0,-1,1};
	static int[] dy = {0,1,-1,0,0};
	static class Horse {
		int num;
		int x,y,d;
		public Horse(int num, int x, int y, int d) {
			this.num = num;
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
		qBoard = new Queue[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				qBoard[i][j] = new LinkedList<>();
			}
		}
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		horseList = new ArrayList<>();
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			
			horseList.add(new Horse(i,x,y,d));
			qBoard[x][y].add(i);
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		int turn = 0;
		
		while(!is_done()) {
			turn++;
			if(turn > 1000) {
				turn = -1;
				break;
			}
			// 1번 말부터 k번 말까지 규칙대로 순서대로 움직인다.
			move_horse();
		}
		
		result = turn;
	}
	
	private static void move_horse() {
		
		for(int i=0;i<K;i++) {
			Horse temp = horseList.get(i);
			
			Queue<Integer> beforeQ = new LinkedList<>(); // 다시 board에 넣어야 하는 말
			Queue<Integer> afterQ = new LinkedList<>(); // 현재 말과 함께 데리고 가야 할 말
			
			// 현재 칸에서 현재 이동하려는 말 poll
			while(!qBoard[temp.x][temp.y].isEmpty()) {
				int horseNum = qBoard[temp.x][temp.y].poll();
				
				if(temp.num == horseNum) break;
				
				else beforeQ.add(horseNum);
			}
			
			// 현재 이동하려는 말을 뽑아내고 난 뒤 board에 남아있는 말들은 데리고 가야 할 말들이니 afterQ에 추가
			if(qBoard[temp.x][temp.y].size() > 0) {
				while(!qBoard[temp.x][temp.y].isEmpty()) {
					int h = qBoard[temp.x][temp.y].poll();
					
					afterQ.add(h);
				}
			}
			
			// 이미 뽑아진 말들은 이동할 말이 아니니 다시 board에 넣어준다.
			if(beforeQ.size() > 0) {
				while(!beforeQ.isEmpty()) {
					int h = beforeQ.poll();
					qBoard[temp.x][temp.y].add(h);
				}
			}
			
			// 이제 말 이동 시작
			int nx = temp.x + dx[temp.d];
			int ny = temp.y + dy[temp.d];
			
			// 격자판의 범위를 벗어나는 이동일 경우 파란색으로 이동하려는 것과 똑같이 생각하여 처리해줍니다.
			if(!is_valid(nx,ny) || board[nx][ny] == 2) {
				// 현재 가지고 있는 방향을 반대로 바꿔준다.
				temp.d = change_dir(temp.d);
				
				nx = temp.x + dx[temp.d];
				ny = temp.y + dy[temp.d];
				
				if(board[nx][ny] == 2) {
					qBoard[temp.x][temp.y].add(temp.num);
					
					if(afterQ.size() > 0) {
						while(!afterQ.isEmpty()) qBoard[temp.x][temp.y].add(afterQ.poll());
					}
					continue;
				}
			}
			
			// 말이 이동하려는 칸이 흰색인 경우에는 해당 칸으로 이동합니다. 
			if(board[nx][ny] == 0) {
				// 이동하려는 칸에 말이 이미 있는 경우에는 해당 말 위에 이동하려던 말을 올려둡니다. 
				// 이미 말이 올려져 있는 상태에도 말을 올릴 수 있습니다.
				qBoard[nx][ny].add(temp.num);
				temp.x = nx;
				temp.y = ny;
				
				if(afterQ.size()>0) {
					while(!afterQ.isEmpty()) {
						int h = afterQ.poll();
						horseList.get(h).x = nx;
						horseList.get(h).y = ny;
						
						qBoard[nx][ny].add(h);
					}
				}
				continue;
			}
			
			// 이동하려는 칸이 빨간색인 경우에는 해당 칸으로 이동하기 전 순서를 뒤집습니다. 
			// 이후 해당 칸에 말이 있는 경우에는 흰색 칸과 같이 그 위에 쌓아둡니다.
			if(board[nx][ny] == 1) {
				Stack<Integer> tempStack = new Stack<>(); // 순서를 바꾸기 위한 임시 stack
				
				// 기존 순서 뒤집는다.
				if(afterQ.size() > 0) {
					while(!afterQ.isEmpty()) {
						tempStack.add(afterQ.poll());
					}
					
					while(!tempStack.isEmpty()) {
						qBoard[nx][ny].add(tempStack.pop());
					}
				}
				
				// 마지막에 이동하는 말을 넣는다.
				temp.x = nx;
				temp.y = ny;
				qBoard[nx][ny].add(temp.num);
				
				continue;
			}
			
			// 이동하려는 칸이 파란색일 경우에는 이동하지 않고 방향을 반대로 전환한 뒤 이동합니다. 
			// 이동하려는 말에 다른 말들이 쌓여있을 경우에 이동하려는 말만 방향을 반대로 바꿉니다.
			if(board[nx][ny] == 2) {
				// 방향 반대로 전환
				temp.d = change_dir(temp.d);
				nx = temp.x + dx[temp.d];
				ny = temp.y + dy[temp.d];
				
				if(!is_valid(nx,ny)) {
					temp.d = change_dir(temp.d);
					
					qBoard[temp.x][temp.y].add(temp.num);
					
					if(afterQ.size() > 0) {
						while(!afterQ.isEmpty()) qBoard[temp.x][temp.y].add(afterQ.poll());
					}
					continue;
				}
				
				// 만일 반대 방향으로 전환한 뒤 이동하려는 칸도 파란색이라면 이동하지 않고 가만히 있습니다.
				if(board[nx][ny] == 2) {
					qBoard[temp.x][temp.y].add(temp.num);
					
					if(afterQ.size() > 0) {
						while(!afterQ.isEmpty()) qBoard[temp.x][temp.y].add(afterQ.poll());
					}
					continue;
				}
				// 만일 반대 방향으로 전환한 뒤 이동하려는 칸이 빨간색이라면
				else if(board[nx][ny] == 1) {
					Stack<Integer> tempStack = new Stack<>(); // 순서를 바꾸기 위한 임시 stack
					
					// 기존 순서 뒤집는다.
					if(afterQ.size() > 0) {
						while(!afterQ.isEmpty()) {
							tempStack.add(afterQ.poll());
						}
						
						while(!tempStack.isEmpty()) {
							qBoard[nx][ny].add(tempStack.pop());
						}
					}
					
					// 마지막에 이동하는 말을 넣는다.
					temp.x = nx;
					temp.y = ny;
					qBoard[nx][ny].add(temp.num);
				}
				// 만일 반대 방향으로 전환한 뒤 이동하려는 칸이 흰색이라면
				else if(board[nx][ny] == 0) {
					qBoard[nx][ny].add(temp.num);
					temp.x = nx;
					temp.y = ny;
					
					if(afterQ.size()>0) {
						while(!afterQ.isEmpty()) {
							int h = afterQ.poll();
							horseList.get(h).x = nx;
							horseList.get(h).y = ny;
							
							qBoard[nx][ny].add(h);
						}
					}
				}
				
			}
			
		}
		
	}
	
	private static int change_dir(int d) {
		if(d == 1) return 2;
		else if(d == 2) return 1;
		else if(d == 3) return 4;
		else return 3;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>= N) return false;
		return true;
	}

	// 말이 4개 이상 겹쳐지는 경우가 생긴다면 그 즉시 게임을 종료
	private static boolean is_done() {
		
		for(int i=0;i<K;i++) {
			Horse h = horseList.get(i);
			if(qBoard[h.x][h.y].size() >= 4) return true;
		}
		
		return false;
	}

}
