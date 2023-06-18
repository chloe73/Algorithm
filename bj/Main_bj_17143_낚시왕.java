package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_17143_낚시왕 {
	
	static int R,C,M,result;
	static int tempLocation; // 낚시왕 현재 위치 (열)
	static int[][] board;
	static int[] dx = {-1,1,0,0}; // 위, 아래
	static int[] dy = {0,0,1,-1}; // 오른쪽, 왼쪽
	static ArrayList<Shark> sharkList; // 상어 리스트
	static class Shark {
		int x,y,s,d,z;
		
		public Shark(int x, int y, int s, int d, int z) {
			this.x = x;
			this.y = y;
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[R][C];
		sharkList = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken())-1;
			int z = Integer.parseInt(st.nextToken());
			
			sharkList.add(new Shark(x, y, s, d, z));
			board[x][y] = i + 1; // int[][]의 기본값이 0이므로 인덱스 번호에 1 더해서 관리함.
		} // input end
		
		tempLocation = -1;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(tempLocation < C) {
			// 1. 낚시왕 오른쪽으로 한 칸 이동
			tempLocation++;
			if(tempLocation == C) continue;
			
			// 2. 낚시왕 땅에서 가장 가까운 상어 잡는다.
			result += get_shark();
			
			// 3. 상어 이동
			move_shark();
		}
	}

	private static void move_shark() {
		
		board = new int[R][C];
		// 각 상어 이동
		for(int i=0;i<sharkList.size();i++) {
			Shark temp = sharkList.get(i); // 현재 이동하려는 상어 정보
			int x = temp.x;
			int y = temp.y;
			int d = temp.d;
			
			int cnt = 0; // 이동한 칸의 수
			while(cnt++ < temp.s) {
				
				// 경계를 넘어가면 방향 반대로 바꾼다.
				if(!isValid(x+dx[d],y+dy[d])) d = change_dir(d);
				
				// 한 칸 이동
				x += dx[d];
				y += dy[d];
			}
			
			sharkList.get(i).x = x;
			sharkList.get(i).y = y;
			sharkList.get(i).d = d;
		}
		
		// 각 상어 이동 완료 후, 각 칸에 상어가 2마리 있는 칸 체크, board 다시 세팅
		ArrayList<Integer> removeList = new ArrayList<>();
		
		for(int i=0;i<sharkList.size();i++) {
			Shark temp = sharkList.get(i); // 현재 확인 중인 상어 정보
			
			if(board[temp.x][temp.y] == 0) board[temp.x][temp.y] = i+1;
			else { // 해당 칸에 2마리가 모인 경우
				int original = board[temp.x][temp.y]-1; // 기존 칸에 있던 상어의 인덱스 번호
				if(sharkList.get(original).z < temp.z) {
					// 기존 상어보다 현재 상어의 크기가 더 큰 경우
					removeList.add(original);
					board[temp.x][temp.y] = i+1;
				}
				else removeList.add(i);
			}
		}
		
		board = new int[R][C]; // 새로 메모리 할당
		ArrayList<Shark> newSharkList = new ArrayList<>();
		for(int i=0;i<sharkList.size();i++) {
			if(removeList.contains(i)) continue;
			
			Shark temp = sharkList.get(i);
			newSharkList.add(temp);
		}
//		sharkList.removeAll(removeList); -> 이 부분이 문제였음.
		sharkList = newSharkList;
		
		for(int i=0;i<sharkList.size();i++) {
			Shark temp = sharkList.get(i);
			
			board[temp.x][temp.y] = i+1;
		}
	}

	private static int get_shark() {
		int sharkSize = 0;
		
		// 현재 낚시왕이 위치한 열의 각 행을 탐색한다.
		for(int i=0;i<R;i++) {
			if(board[i][tempLocation] > 0) {
				int sharkNum = board[i][tempLocation] - 1; // 가장 가까운 상어의 인덱스 번호
				sharkSize = sharkList.get(sharkNum).z; // 가장 가까운 상어의 크기
				sharkList.remove(sharkNum); // sharkList에서 잡은 상어 삭제
				
				board = new int[R][C]; // board 다시 세팅
				for(int idx=0;idx<sharkList.size();idx++) {
					Shark temp = sharkList.get(idx);
					board[temp.x][temp.y] = idx + 1; // 기존 인덱스 번호에 1 더해서 관리
				}
				break;
			}
		}
		
		return sharkSize;
	}
	
	private static int change_dir(int d) {
		if(d == 0) return 1;
		else if(d == 1) return 0;
		else if(d == 2) return 3;
		else return 2;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=R ||c>=C) return false;
		return true;
	}

}
