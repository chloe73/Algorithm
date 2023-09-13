package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_미로_타워_디펜스 {
	
	static int N,M,result;
	static int tx,ty;
	static int[][] numBoard;
	static Queue<Monster>[][] board;
	// 0번부터 3번까지 각각 → ↓ ← ↑
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static ArrayList<int[]> cmdList;
	static ArrayList<Integer> mList; 
	static ArrayList<Integer> removeList;
	static class Monster {
		int num;
		int x,y;
		public Monster(int num, int x, int y){
			this.num = num;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new LinkedList[N][N];
		numBoard = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = new LinkedList<>();
				int num = Integer.parseInt(st.nextToken());
				if(num == 0) continue;
				board[i][j].add(new Monster(num, i, j));
			}
		}
		cmdList = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int d = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			
			cmdList.add(new int[] {d,p});
		} // input end
		
		tx = N / 2;
		ty = N / 2;
		
		mList = new ArrayList<>();
		
		init_mList();
		
		solve();
		
		System.out.println(result);
	}

	private static void init_mList() {
		int x = tx;
		int y = ty;
		int d = 0;
		
		// left - down - right - up
		int[] ddx = {0,1,0,-1};
		int[] ddy = {-1,0,1,0};
		
		int cnt = 1;
		int round = 0;
		int number = 0;
		outer:while(true) {
			
			for(int i=0;i<cnt;i++) {
				x += ddx[d];
				y += ddy[d];
				
				numBoard[x][y] = number++;
				
				if(x == 0 && y == 0) {
					if(board[x][y].size() > 0) {
						Monster temp = board[x][y].peek();
						
						mList.add(temp.num);
					}
					break outer;
				}
				
				if(board[x][y].size() == 0) continue;
				
				Monster temp = board[x][y].peek();
				
				mList.add(temp.num);
			}
			
			d = change_dir(d);
			
			round++;
			
			if(round % 2 == 0) cnt++;
		}
	}
	
	private static void print() {
		for(int i=0;i<mList.size();i++) {
			System.out.print(mList.get(i)+", ");
		}
		System.out.println();
	}

	private static void solve() {
		
		for(int[] temp : cmdList) {
			// 1. 플레이어는 상하좌우 방향 중 주어진 공격 칸 수만큼 몬스터를 공격하여 없앨 수 있습니다.
			remove_monster(temp[0], temp[1]);

			// 2. 비어있는 공간만큼 몬스터는 앞으로 이동하여 빈 공간을 채웁니다.
			
			// 3. 이때 몬스터의 종류가 4번 이상 반복하여 나오면 해당 몬스터 또한 삭제됩니다.
			// 해당 몬스터들은 동시에 사라집니다.
			// 삭제된 이후에는 몬스터들을 앞으로 당겨주고, 4번 이상 나오는 몬스터가 있을 경우 또 삭제를 해줍니다.
			// 4번 이상 나오는 몬스터가 없을 때까지 반복해줍니다.
			while(is_possible()) {
				remove_and_fill();
			}

			// 4. 삭제가 끝난 다음에는 몬스터를 차례대로 나열했을 때 같은 숫자끼리 짝을 지어줍니다.
			// 이후 각각의 짝을 (총 개수, 숫자의 크기)로 바꾸어서 다시 미로 속에 집어넣습니다.
			make_set();
			
//			print();
		}
	}
	
	private static void make_set() {
		
		ArrayList<Integer> renewalList = new ArrayList<>();
		
		int temp = mList.get(0);
		int cnt = 1;
		
		for(int i=1;i<mList.size();i++) {
			int num = mList.get(i);
			
			if(temp == num) {
				cnt++;
				continue;
			}
			
			if(temp != num) {
				renewalList.add(cnt);
				renewalList.add(temp);
				
				temp = num;
				cnt = 1;
			}
		}
		
		renewalList.add(cnt);
		renewalList.add(temp);
		
		mList.clear();
		mList = renewalList;
	}

	private static void remove_and_fill() {
		
		// 4번 이상 나오는 몬스터
		// 인덱스
		ArrayList<Integer> removeList = new ArrayList<>();
		
		int temp = mList.get(0);
		int cnt = 1;
		for(int i=1;i<mList.size();i++) {
			int num = mList.get(i);
			
			if(temp == num) {
				cnt++;
				continue;
			}
			
			if(temp != num) {
				
				if(cnt >= 4) {
					int index = i-1;
					while(cnt-- > 0) {
						result += mList.get(index);
						removeList.add(index--);
					}
				}
				
				temp = num;
				cnt = 1;
				continue;
			}
		}
		
		if(cnt >= 4) {
			int index = mList.size()-1;
			while(cnt-- > 0) {
				result += mList.get(index);
				removeList.add(index--);
			}
		}
		
		ArrayList<Integer> renewalList = new ArrayList<>();
		
		for(int i=0;i<mList.size();i++) {
			if(removeList.contains(i)) continue;
			
			renewalList.add(mList.get(i));
		}
		
		mList.clear();
		mList = renewalList;
	}

	private static boolean is_possible() {
		
		int temp = mList.get(0);
		int cnt = 1;
		for(int i=1;i<mList.size();i++) {
			int num = mList.get(i);
			
			if(cnt >= 4) return true;
			
			if(temp == num) {
				cnt++;
				continue;
			}
			
			if(temp != num) {
				temp = num;
				cnt = 1;
				continue;
			}
		}
		
		if(cnt >= 4) return true;
		
		return false;
	}

	private static void remove_monster(int d, int p) {
		
		int x = tx;
		int y = ty;
		
		removeList = new ArrayList<>();
		
		for(int i=0;i<p;i++) {
			x += dx[d];
			y += dy[d];
			
//			if(board[x][y].size() == 0) continue;
			
			removeList.add(numBoard[x][y]);
			
//			Monster temp = board[x][y].poll();
			
			if(numBoard[x][y] < mList.size())
				result += mList.get(numBoard[x][y]);
//			board[x][y].clear();
		}
		
		ArrayList<Integer> renewalList = new ArrayList<>();
		
		for(int i=0;i<mList.size();i++) {
			if(removeList.contains(i)) continue;
			
			renewalList.add(mList.get(i));
		}
		
		mList.clear();
		mList = renewalList;
		
	}
	
	private static int change_dir(int d) {
		if(d == 3) return 0;
		return d+1;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
		
	}

}
