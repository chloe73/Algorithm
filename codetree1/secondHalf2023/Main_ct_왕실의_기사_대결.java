package algo.codetree1.secondHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_ct_왕실의_기사_대결 {
	
	static int L,N,Q,result;
	static int[][] board,kBoard;
//	d는 0, 1, 2, 3 중에 하나이며 각각 위쪽, 오른쪽, 아래쪽, 왼쪽 방향을 의미합니다.
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static HashMap<Integer, Knight> kMap;
	static class Point {
		int num,x,y,h,w;
		public Point(int num, int x, int y, int h, int w) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.h = h;
			this.w = w;
		}
	}
	static class Knight {
		int x,y,h,w,k;
		int damage;
		boolean isAlive;
		public Knight(int x, int y, int h, int w, int k) {
			this.x = x;
			this.y = y;
			this.h = h;
			this.w = w;
			this.k = k;
			isAlive = true;
			damage = 0;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
//		0이라면 빈칸을 의미합니다.
//		1이라면 함정을 의미합니다.
//		2라면 벽을 의미합니다.
		board = new int[L][L];
		for(int i=0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<L;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		kMap = new HashMap<>();
		kBoard = new int[L][L];
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			kMap.put(i, new Knight(x, y, h, w, k));
			for(int a=0;a<h;a++) {
				for(int b=0;b<w;b++) {
					kBoard[x+a][y+b] = i;
				}
			}
		}
		
		for(int turn=0;turn<Q;turn++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			solve(i,d);
		} // input end
		
//		Q 개의 명령이 진행된 이후, 생존한 기사들이 총 받은 대미지의 합을 출력합니다.
		for(int i=1;i<=N;i++) {
			if(!kMap.get(i).isAlive) continue;
			result += kMap.get(i).damage;
		}
		
		System.out.println(result);
	}

	private static void solve(int idx, int d) {
		Knight temp = kMap.get(idx);
		
		if(!temp.isAlive) return;
		
		boolean flag = true;
		Stack<Integer> s = new Stack<>();
		boolean[] visited = new boolean[N+1];
		
		if(d == 0) {
			// up
			int tx = temp.x-1;
			int ty = temp.y;
			
			if(!isValid(tx, ty)) return;
			
			s.add(idx);
			
			Stack<Point> stack = new Stack<>();
			for(int i=0;i<temp.w;i++) {
				// 벽인 경우
				if(board[tx][ty+i] == 2) return;
				// 다른 기사가 있는 경우
				if(kBoard[tx][ty+i] > 0) {
					int num = kBoard[tx][ty+i];
					if(!visited[num]) {
						visited[num] = true;
						Knight next = kMap.get(num);
						stack.add(new Point(num, next.x, next.y, next.h, next.w));
						s.add(num);
					}
				}
			}
			
			if(stack.size() > 0) {
				while(!stack.isEmpty()) {
					Point tmp = stack.pop();
					
					// 범위를 벗어나는 경우 이동 불가
					if(!isValid(tmp.x-1, tmp.y)) return;
					
					for(int i=0;i<tmp.w;i++) {
						// 벽을 마주친 경우, 이동 불가
						if(board[tmp.x-1][tmp.y+i] == 2) return;
						// 다른 기사 마주한 경우
						if(kBoard[tmp.x-1][tmp.y+i] > 0) {
							int num = kBoard[tmp.x-1][tmp.y+i];
							if(!visited[num]) {
								visited[num] = true;
								Knight next = kMap.get(num);
								stack.add(new Point(num, next.x, next.y, next.h, next.w));
								s.add(num);
							}
						}
					}
				}
			}
		}
		else if(d == 1) {
			// 오른쪽으로 이동
			// 밀 때, 기준점 tx,ty
			int tx = temp.x;
			int ty = temp.y + temp.w;

			// 범위 밖으로 나가는 경우 아무 일도 안 일어남.
			if(!isValid(tx, ty)) return;
			
			s.add(idx);
			
			Stack<Point> stack = new Stack<>();
			for(int i=0;i<temp.h;i++) {
				
				// 벽을 마주치면 불가능한 경우임.
				if(board[tx+i][ty] == 2) {
					return;
				}
				
				// 다른 기사를 마주한 경우,
				if(kBoard[tx+i][ty] > 0) {
					int num = kBoard[tx+i][ty];
					if(!visited[num]) {
						visited[num] = true;
						Knight next = kMap.get(num);
						stack.add(new Point(num, next.x, next.y, next.h, next.w));
						s.add(num);
					}
				}
			}
			
			if(stack.size() > 0) {
				while(!stack.isEmpty()) {
					Point tmp = stack.pop();
					
					// 범위를 벗어나는 경우 이동 불가
					if(!isValid(tmp.x, tmp.y+tmp.w)) return;
					
					for(int i=0;i<tmp.h;i++) {
						// 벽을 마주친 경우, 이동 불가
						if(board[tmp.x+i][tmp.y+tmp.w] == 2) return;
						// 다른 기사 마주한 경우
						if(kBoard[tmp.x+i][tmp.y+tmp.w] > 0) {
							int num = kBoard[tmp.x+i][tmp.y+tmp.w];
							if(!visited[num]) {
								visited[num] = true;
								Knight next = kMap.get(num);
								stack.add(new Point(num, next.x, next.y, next.h, next.w));
								s.add(num);
							}
						}
					}
				}
			}
		}
		else if(d == 2) {
			// down
			int tx = temp.x+temp.h;
			int ty = temp.y;
			
			// 범위 밖으로 나가는 경우 아무 일도 안 일어남.
			if(!isValid(tx, ty)) return;
			
			s.add(idx);
			
			Stack<Point> stack = new Stack<>();
			for(int i=0;i<temp.w;i++) {
				// 벽인 경우
				if(board[tx][ty+i] == 2) return;
				// 다른 기사가 있는 경우
				if(kBoard[tx][ty+i] > 0) {
					int num = kBoard[tx][ty+i];
					if(!visited[num]) {
						visited[num] = true;
						Knight next = kMap.get(num);
						stack.add(new Point(num, next.x, next.y, next.h, next.w));
						s.add(num);
					}
				}
			}
			
			if(stack.size() > 0) {
				while(!stack.isEmpty()) {
					Point tmp = stack.pop();
					
					// 범위를 벗어나는 경우 이동 불가
					if(!isValid(tmp.x+tmp.h, tmp.y)) return;
					
					for(int i=0;i<tmp.w;i++) {
						// 벽을 마주친 경우, 이동 불가
						if(board[tmp.x+tmp.h][tmp.y+i] == 2) return;
						// 다른 기사 마주한 경우
						if(kBoard[tmp.x+tmp.h][tmp.y+i] > 0) {
							int num = kBoard[tmp.x+tmp.h][tmp.y+i];
							if(!visited[num]) {
								visited[num] = true;
								Knight next = kMap.get(num);
								stack.add(new Point(num, next.x, next.y, next.h, next.w));
								s.add(num);
							}
						}
					}
				}
			}
		}
		else {
			// left
			int tx = temp.x;
			int ty = temp.y-1;
			
			// 범위 밖으로 나가는 경우 아무 일도 안 일어남.
			if(!isValid(tx, ty)) return;
			
			s.add(idx);
			
			Stack<Point> stack = new Stack<>();
			for(int i=0;i<temp.h;i++) {
				
				// 벽을 마주치면 불가능한 경우임.
				if(board[tx+i][ty] == 2) {
					return;
				}
				
				// 다른 기사를 마주한 경우,
				if(kBoard[tx+i][ty] > 0) {
					int num = kBoard[tx+i][ty];
					if(!visited[num]) {
						visited[num] = true;
						Knight next = kMap.get(num);
						stack.add(new Point(num, next.x, next.y, next.h, next.w));
						s.add(num);						
					}
				}
			}
			
			if(stack.size() > 0) {
				while(!stack.isEmpty()) {
					Point tmp = stack.pop();
					
					// 범위를 벗어나는 경우 이동 불가
					if(!isValid(tmp.x, tmp.y-1)) return;
					
					for(int i=0;i<tmp.h;i++) {
						// 벽을 마주친 경우, 이동 불가
						if(board[tmp.x+i][tmp.y-1] == 2) return;
						// 다른 기사 마주한 경우
						if(kBoard[tmp.x+i][tmp.y-1] > 0) {
							int num = kBoard[tmp.x+i][tmp.y-1];
							if(!visited[num]) {
								visited[num] = true;
								Knight next = kMap.get(num);
								stack.add(new Point(num, next.x, next.y, next.h, next.w));
								s.add(num);
							}
						}
					}
				}
			}
		}
		
		// 이동 전, 기사들의 현재 위치 복사
//		int[][] arr = new int[L][L];
//		for(int i=0;i<L;i++) {
//			arr[i] = Arrays.copyOf(kBoard[i], L);
//		}
		
		// 기사들 이동 시작
		while(!s.isEmpty()) {
			int num = s.pop();
			
			moveKnight(num, d, idx);
		}
	}
	
	private static void moveKnight(int num, int d, int target) {
		Knight tmp = kMap.get(num);
		int cnt = 0;
		
		if(d == 0) {
			// up
			int nx = tmp.x;
			int ny = tmp.y;
			for(int i=0;i<tmp.h;i++) {
//				nx--;
				for(int j=0;j<tmp.w;j++) {
					// 이동하려는 칸이 함정인 경우
					if(board[nx+i-1][ny+j] == 1) cnt++;
					kBoard[nx+i-1][ny+j] = num;
					kBoard[nx+i][ny+j] = 0;
				}
			}
			
			tmp.x -= 1;
		}
		else if(d == 1) {
			// right
			int nx = tmp.x;
			int ny = tmp.y + tmp.w-1;
			for(int j=0;j<tmp.w;j++) {
				for(int i=0;i<tmp.h;i++) {
					if(board[nx+i][ny-j+1] == 1) cnt++;
					kBoard[nx+i][ny-j+1] = num;
					kBoard[nx+i][ny-j] = 0;
				}
			}
			tmp.y += 1;
		}
		else if(d == 2) {
			// down
			int nx = tmp.x + tmp.h -1;
			int ny = tmp.y;
			for(int i=0;i<tmp.h;i++) {
//				nx++;
				for(int j=0;j<tmp.w;j++) {
					if(board[nx-i+1][ny+j] == 1) cnt++;
					kBoard[nx-i+1][ny+j] = num;
					kBoard[nx-i][ny+j] = 0;
				}
			}
			tmp.x += 1;
		}
		else {
			// left
			int nx = tmp.x;
			int ny = tmp.y;
			for(int j=0;j<tmp.w;j++) {
//				ny--;
				for(int i=0;i<tmp.h;i++) {
					if(board[nx+i][ny+j-1] == 1) cnt++;
					kBoard[nx+i][ny+j-1] = num;
					kBoard[nx+i][ny+j] = 0;
				}
			}
			tmp.y -= 1;
		}

		if(num != target) {
			if(tmp.k - cnt > 0) {
				tmp.k -= cnt;
				tmp.damage += cnt;
			}
			else {
				tmp.isAlive = true;
				tmp.k = 0;
				for(int i=0;i<tmp.h;i++) {
					for(int j=0;j<tmp.w;j++) {
						kBoard[tmp.x+i][tmp.y+j] = 0;
					}
				}
			}
		}
		
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=L || c>=L) return false;
		return true;
	}
}
