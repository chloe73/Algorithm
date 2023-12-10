package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_왕실의_기사_대결 {
	
	static int L,N,Q,result;
	static int[][] board,kBoard;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static ArrayList<Knight> kList;
	static class Knight {
		int r,c,h,w,k; // k가 체력. 만약, 체력이 0이라면 해당 기사는 사라진 상태임.
		int damage;
		public Knight(int r, int c, int h, int w, int k, int damage) {
			this.r = r;
			this.c = c;
			this.h = h;
			this.w = w;
			this.k = k;
			this.damage = damage;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		board = new int[L][L];
		kBoard = new int[L][L]; // knight 위치 정보 저장
		for(int i=0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<L;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		kList = new ArrayList<>();
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			// knight 기사 정보 입력
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			// 기사 위치 저장
			kList.add(new Knight(r, c, h, w, k, 0));
			for(int x=0;x<h;x++) {
				for(int y=0;y<w;y++) {
					kBoard[r+x][c+y] = i;
				}
			}
		}
		
		for(int i=0;i<Q;i++) {
			st = new StringTokenizer(br.readLine());
			// i번 기사에게 방향 d로 한 칸 이동하라는 명령
			// d는 0, 1, 2, 3 중에 하나이며 각각 위쪽, 오른쪽, 아래쪽, 왼쪽 방향을 의미
			int idx = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			// 이미 사라진 기사 번호가 주어질 수도 있음에 유의
			// 체스판에서 사라진 기사에게 명령을 내리면 아무런 반응이 없게 됩니다.
			if(kList.get(idx).k == 0) continue;
			
			move_knight(idx,d);
		}
		
		// Q 번의 대결이 모두 끝난 후 생존한 기사들이 총 받은 대미지의 합을 출력
		for(Knight temp : kList) {
			if(temp.k > 0) {
				result += temp.damage;
			}
		}
		
		System.out.println(result);
	}

	private static void move_knight(int idx, int d) {
		// 만약 이동하려는 위치에 다른 기사가 있다면 그 기사도 함께 연쇄적으로 한 칸 밀려나게 됩니다.
		// 그 옆에 또 기사가 있다면 연쇄적으로 한 칸씩 밀리게 됩니다.
		// 하지만 만약 기사가 이동하려는 방향의 끝에 벽이 있다면 모든 기사는 이동할 수 없게 됩니다.
		
		Knight temp = kList.get(idx);
		
		if(d==0) {
			// up
			Queue<Integer> q = new LinkedList<>();
			// 위쪽으로 이동하는 것이 범위 밖으로 벗어나는 것은 아닌지 확인
			if(!is_valid(temp.r-1, temp.c)) return;
			
			// 해당 기사의 맨 첫번째 행부터 확인
			boolean isWall = false;
			for(int c=temp.c;c<temp.c+temp.w;c++) {
				if(board[temp.r-1][c] == 2) {
					isWall = true;
					break;
				}
				// 해당 칸에 다른 기사가 위치해 있다면
				if(kBoard[temp.r-1][c] > 0) {
					if(!q.contains(kBoard[temp.r-1][c])) {
						q.add(kBoard[temp.r-1][c]);
					}
				}
			}
			
			if(isWall) return;
			
			// 벽이 없는 상황에서 다른 기사들이 밀려나는 경우
			if(!q.isEmpty()) {
				
			}
			// 벽이 없고 밀려날 기사도 없는 경우 해당 기사만 한칸 이동하면 됨.
			for(int i=temp.r;i<temp.r+temp.h;i++) {
				for(int j=0;j<temp.c+temp.w;j++) {
					kBoard[i-1][j] = kBoard[i][j];
				}
			}
		}
		else if(d==1) {
			// right
			boolean isWall = false;
			Queue<Integer> q = new LinkedList<>();
			
			
		}
		else if(d==2) {
			// down
			// 현재 기사의 영역에서 맨 밑 행부터 탐색 시작
			boolean isWall = false;
			int x = temp.r+temp.h-1;
			Queue<Integer> q = new LinkedList<>();
			
			// 범위 밖으로 벗어나는 경우
			if(x>=L) return;
			
			for(int y=temp.c;y<temp.c+temp.w;y++) {
				if(board[x+1][y] == 2) {
					isWall = true;
					break;
				}
				// 해당 칸에 다른 기사가 위치해 있다면
				if(kBoard[x+1][y] > 0) {
					if(!q.contains(kBoard[x+1][y])) {
						q.add(kBoard[x+1][y]);
					}
				}
			}
			// 한 줄 내겨가려는 행에 벽이 있으면 이동 못함.
			if(isWall) return;
			
			// 벽이 없는 상황에서 다른 기사들이 존재하는 경우
			if(!q.isEmpty()) {
				int size = q.size();
				for(int i=0;i<size;i++) {
					int knightIdx = q.poll();
					
					// 해당 기사들이 밀려날 수 있는지 확인
					
				}
			}
			
			// 벽이 없고 밀려날 기사도 없는 경우 해당 기사만 한칸 이동하면 됨.
			for(int i=x;i>=temp.r;i--) {
				for(int j=temp.c;j<temp.c+temp.w;j++) {
					kBoard[i+1][j] = kBoard[i][j];
				}
			}
			
		}
		else {
			// left
			
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=L || c>=L) return false;
		return true;
	}

}
