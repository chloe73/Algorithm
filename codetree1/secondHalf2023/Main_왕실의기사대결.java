package algo.codetree1.secondHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main_왕실의기사대결 {
	
	static int L,N,Q;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static int[][] board,kBoard;
	static TreeMap<Integer, Knight> kMap;
	static class Knight {
		@Override
		public String toString() {
			return "Knight [x=" + x + ", y=" + y + ", h=" + h + ", w=" + w + ", k=" + k + ", damage=" + damage
					+ ", isDead=" + isDead + "]";
		}

		int x,y,h,w,k;
		int damage;
		boolean isDead;
		
		public Knight(int x, int y, int h, int w, int k, int damage, boolean isDead) {
			this.x = x;
			this.y = y;
			this.h = h;
			this.w = w;
			this.k = k;
			this.damage = damage;
			this.isDead = isDead;
		}
		
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		board = new int[L][L];
		kBoard = new int[L][L];
		for(int i=0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<L;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
//		0이라면 빈칸을 의미합니다.
//		1이라면 함정을 의미합니다.
//		2라면 벽을 의미합니다.
		
		kMap = new TreeMap<>();
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			kMap.put(i, new Knight(x, y, h, w, k, 0, false));
			for(int r=x;r<x+h;r++) {
				for(int c=y;c<y+w;c++) {
					kBoard[r][c] = i;
				}
			}
		}

		for(int a=0;a<Q;a++) {
			st = new StringTokenizer(br.readLine());
			
			int i = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			move(i,d);
			
//			System.out.println();
//			for(int r=0;r<L;r++) {
//				for(int c=0;c<L;c++) {
//					System.out.print(kBoard[r][c]+"\t");
//				}
//				System.out.println();
//			}
//			
//			for(int idx: kMap.keySet()) {
//				Knight temp = kMap.get(idx);
//				System.out.println(temp.toString());
////				System.out.println(idx+"번 기사 정보 : "+temp.x+", "+temp.y+", "+temp.h+", "+temp.w+", "+temp.k);
//			}
//			System.out.println(a+" ****** ============================              ");
		} // input end
		
		int result = 0;
		for(int i : kMap.keySet()) {
			Knight temp = kMap.get(i);
			
			if(!temp.isDead) result += temp.damage;
		}
		// Q 개의 명령이 진행된 이후, 생존한 기사들이 총 받은 대미지의 합을 출력합니다.
		System.out.println(result);
	}

	private static void move(int i, int d) {
		// (1) 기사 이동
		// 또, 체스판에서 사라진 기사에게 명령을 내리면 아무런 반응이 없게 됩니다.
		if(kMap.get(i).isDead) return;

		// 왕에게 명령을 받은 기사는 상하좌우 중 하나로 한 칸 이동할 수 있습니다. 
		
		int idx = i;
		Queue<Integer> q = new LinkedList<>();
		boolean isMovable = true;
		int[][] renewalKBoard = new int[L][L];
		
		Knight temp = kMap.get(idx);
		boolean[] isChecked = new boolean[kMap.size()+1];
		isChecked[idx] = true;
		outer:for(int x=temp.x;x<temp.x+temp.h;x++) {
			for(int y=temp.y;y<temp.y+temp.w;y++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				// 하지만 만약 기사가 이동하려는 방향의 끝에 벽이 있다면 모든 기사는 이동할 수 없게 됩니다. 
				if(!isValid(nx, ny) || board[nx][ny] == 2) {
					isMovable = false;
					break outer;
				}
				
				// 이때 만약 이동하려는 위치에 다른 기사가 있다면 그 기사도 함께 연쇄적으로 한 칸 밀려나게 됩니다. 
				// 그 옆에 또 기사가 있다면 연쇄적으로 한 칸씩 밀리게 됩니다.
				if(kBoard[nx][ny] > 0 && !isChecked[kBoard[nx][ny]]) {
					isChecked[kBoard[nx][ny]] = true;
					q.add(kBoard[nx][ny]);
				}
				
				renewalKBoard[nx][ny] = idx;
			}
		}
		
		if(!isMovable) return;
		
		
		// 2) 대결 대미지
		int[] damageNum = new int[kMap.size()+1];
		// 명령을 받은 기사가 다른 기사를 밀치게 되면, 밀려난 기사들은 피해를 입게 됩니다.
		// 이때 각 기사들은 해당 기사가 이동한 곳에서 w×h 직사각형 내에 놓여 있는 함정의 수만큼만 피해를 입게 됩니다.
		// 각 기사마다 피해를 받은 만큼 체력이 깎이게 되며, 현재 체력 이상의 대미지를 받을 경우 기사는 체스판에서 사라지게 됩니다.
		// 단, 명령을 받은 기사는 피해를 입지 않으며, 기사들은 모두 밀린 이후에 대미지를 입게 됩니다.
		// 밀렸더라도 밀쳐진 위치에 함정이 전혀 없다면 그 기사는 피해를 전혀 입지 않게 됨에 유의합니다.

		Stack<ArrayList<Integer>> stack = new Stack<>();
		if(q.size() > 0) {
			
			outer:while(!q.isEmpty()) {
				
				ArrayList<Integer> list = new ArrayList<>();
				int size = q.size();
				//isChecked = new boolean[kMap.size()+1];
				
				// 현재 q 사이즈 만큼 연쇄되는 기사들 확인
				for(int a=0;a<size;a++) {
					int num = q.poll();
					list.add(num);
					Knight next = kMap.get(num);
					
					for(int x=next.x;x<next.x+next.h;x++) {
						for(int y=next.y;y<next.y+next.w;y++) {
							int nx = x + dx[d];
							int ny = y + dy[d];
							
							// 하지만 만약 기사가 이동하려는 방향의 끝에 벽이 있다면 모든 기사는 이동할 수 없게 됩니다. 
							if(!isValid(nx, ny) || board[nx][ny] == 2) {
								isMovable = false;
								break outer;
							}
							
							// 이때 만약 이동하려는 위치에 다른 기사가 있다면 그 기사도 함께 연쇄적으로 한 칸 밀려나게 됩니다. 
							// 그 옆에 또 기사가 있다면 연쇄적으로 한 칸씩 밀리게 됩니다.
							if(kBoard[nx][ny] > 0 && kBoard[nx][ny] != num && !isChecked[kBoard[nx][ny]]) {
								isChecked[kBoard[nx][ny]] = true;
								q.add(kBoard[nx][ny]);
							}
							
							// 함정이 있으면 damage +1 증가
							if(board[nx][ny] == 1) {
								damageNum[num]++;
							}
							
						}
					}
					
				}
				
				stack.add(list);
			}
			
		}
		
		if(!isMovable) return;
		
		kMap.get(i).x += dx[d];
		kMap.get(i).y += dy[d];

		for(int index=1;index<=kMap.size();index++) {
			if(index == i) continue; 
			
			if(damageNum[index] > 0) {
				if(kMap.get(index).k - damageNum[index] <= 0) {
					kMap.get(index).isDead = true;
					kMap.get(index).x += dx[d];
					kMap.get(index).y += dy[d];
					isChecked[index] = false;
					continue;
//					for(int x=kMap.get(index).x;x<kMap.get(index).x+kMap.get(index).h;x++) {
//						for(int y=kMap.get(index).y;y<kMap.get(index).y+kMap.get(index).w;y++) {
//							renewalKBoard[x][y] = 0;
//						}
//					}
				}
				
			}
			
			if(isChecked[index] && kMap.get(index).k - damageNum[index] > 0) {
				kMap.get(index).x += dx[d];
				kMap.get(index).y += dy[d];
				kMap.get(index).damage += damageNum[index];
				kMap.get(index).k -= damageNum[index];				
			}

			if(!isChecked[index] && !kMap.get(index).isDead) {
				Knight tmp = kMap.get(index);
				for(int x=tmp.x;x<tmp.x+tmp.h;x++) {
					for(int y=tmp.y;y<tmp.y+tmp.w;y++) {
						renewalKBoard[x][y] = index;
					}
				}
			}
		}
		
		while(!stack.isEmpty()) {
			ArrayList<Integer> list = stack.pop();
			
			for(int index : list) {
				Knight tmp = kMap.get(index);
				for(int x=tmp.x;x<tmp.x+tmp.h;x++) {
					for(int y=tmp.y;y<tmp.y+tmp.w;y++) {
						renewalKBoard[x][y] = index;
					}
				}
			}
		}
		kBoard = renewalKBoard;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=L || c>=L) return false;
		return true;
	}

}
