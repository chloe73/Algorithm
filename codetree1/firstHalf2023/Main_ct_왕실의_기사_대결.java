package algo.codetree1.firstHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
	static class Result {
		Stack<Integer> stack;
		
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
		
		// 이동 전, 기사들의 현재 위치 복사
		int[][] arr = new int[L][L];
		for(int i=0;i<L;i++) {
			arr[i] = Arrays.copyOf(kBoard[i], L);
		}
		
		boolean flag = true;
		
		if(d == 0) {
			
		}
		else if(d == 1) {
			// 오른쪽으로 이동
			// 밀 때, 기준점 tx,ty
			int tx = temp.x;
			int ty = temp.y + temp.w -1;

			// 범위 밖으로 나가는 경우 아무 일도 안 일어남.
			if(!isValid(tx, ty+1)) return;
			
			for(int i=0;i<temp.h;i++) {
				while(isValid(tx, ty)) {
					// 오른쪽으로 탐색
					ty++;
					
					// 벽을 마주치면 불가능한 경우임.
					if(board[tx+i][ty] == 1) {
						return;
					}
					
					// 다른 기사를 마주한 경우,
					if(kBoard[tx+i][ty] > 0) {
						
					}
					else {
						
					}
				}
			}
			
			
		}
		else if(d == 2) {
			
		}
		else {
			
		}
		
		
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=L || c>=L) return false;
		return true;
	}
}
