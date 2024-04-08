package algo.codetree1.firstHalf2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_미로타워디펜스 {
	
	static int N,M,result;
	static int[][] board,arr;
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
//	static ArrayList<int[]> locationList;
	static ArrayList<Integer> mList;
	static class Monster {
		int x,y,num;
		public Monster(int x, int y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		double before = System.currentTimeMillis();
		
		arr = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		board = new int[N][N];
		mList = new ArrayList<>();
//		locationList = new ArrayList<>();
		make_snail();

		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int d = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			
			attack(d,p);
//			print();
//			System.out.println(" =================== ");
		} // input end
		
		double after = System.currentTimeMillis();
		System.out.println((after-before)/ 1000);
		System.out.println(result);
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] < mList.size()) {
					System.out.print(mList.get(board[i][j])+"\t");
				}
				else
					System.out.print(0+"\t");
			}
			System.out.println();
		}
	}

	private static void attack(int d, int p) {
		int x = N/2;
		int y = N/2;
		
		ArrayList<Integer> removeList = new ArrayList<>();
		for(int i=0;i<p;i++) {
			x += dx[d];
			y += dy[d];
			
			if(!isValid(x, y)) break;
			
			if(board[x][y] < mList.size() && mList.get(board[x][y]) > 0) {
				result += mList.get(board[x][y]);
				removeList.add(board[x][y]);
			}
			else break;
			
			// 플레이어는 상하좌우 방향 중 주어진 공격 칸 수만큼 몬스터를 공격하여 없앨 수 있습니다.
//			if(arr[x][y] > 0) {
//				// 몬스터 번호만큼 점수 획득
//				result += arr[x][y];
//				arr[x][y] = 0;
//				removeList.add(board[x][y]);
//			}
		}
		
		// 비어있는 공간만큼 몬스터는 앞으로 이동하여 빈 공간을 채웁니다.
		stick_monster(removeList);
		
		// 이때 몬스터의 종류가 4번 이상 반복하여 나오면 해당 몬스터 또한 삭제됩니다. 해당 몬스터들은 동시에 사라집니다.
		while(true) {
			ArrayList<int[]> rList = new ArrayList<>();
			int cnt = 1;
			int num = mList.get(1);
			int start = 1;

			for(int i=2;i<mList.size();i++) {
				if(num != mList.get(i)) {
					if(cnt >= 4 && mList.get(i-1) == num) {
						result += (cnt * num);
						rList.add(new int[] {start,i-1});
						cnt = 1;
						num = mList.get(i);
						start = i;
					}
					else {
						cnt = 1;
						num = mList.get(i);
						start = i;
					}
					continue;
				}
				if(num == mList.get(i)) {
					cnt++;
					continue;
				}
			}
			
			if(cnt >= 4) {
				rList.add(new int[] {start,mList.size()-1});
				result += (cnt * num);
			}
			
			if(rList.size() == 0) break;
			
			if(rList.size() > 0) {
				int count = 0;
				for(int[] target : rList) {
					int s = target[0];
					int e = target[1];
					for(int k=s;k<=e;k++) {
						mList.remove(k-count);
						count++;
					}
				}
			}
			
			// 삭제된 이후에는 몬스터들을 앞으로 당겨주고, 4번 이상 나오는 몬스터가 있을 경우 또 삭제를 해줍니다. 
			// 4번 이상 나오는 몬스터가 없을 때까지 반복해줍니다.
			
		}
		
		// 삭제가 끝난 다음에는 몬스터를 차례대로 나열했을 때 같은 숫자끼리 짝을 지어줍니다. 
		// 이후 각각의 짝을 (총 개수, 숫자의 크기)로 바꾸어서 다시 미로 속에 집어넣습니다.
		ArrayList<Integer> renewal = new ArrayList<>();
		int cnt = 1;
		int num = mList.get(1);
		renewal.add(0);
		for(int i=2;i<mList.size();i++) {
			if(num == mList.get(i)) {
				cnt++;
				continue;
			}
			if(num != mList.get(i)) {
				renewal.add(cnt);
				renewal.add(num);
				cnt = 1;
				num = mList.get(i);
			}
		}
		renewal.add(cnt);
		renewal.add(num);
		
		mList.clear();
		mList = renewal;
	}
	
	private static boolean check_monster() {
		
		int cnt = 1;
		int num = mList.get(1);
		for(int i=2;i<mList.size();i++) {
			if(cnt == 4) return true;
			if(num == mList.get(i)) {
				cnt++;
				continue;
			}
			if(num != mList.get(i)) {
				cnt = 1;
				num = mList.get(i);
			}
		}
		
		if(cnt >= 4) return true;
		
		return false;
	}
	
	private static void stick_monster(ArrayList<Integer> list) {
		
		int cnt = 0;
		for(int i : list) {
			mList.remove(i-cnt);
			cnt++;
		}
		
	}

	private static void make_snail() {
		int x = N/2;
		int y = N/2;
		int turn = 0;
		int cnt = 1;
		int num = 1;
		int d = 0;
		int[] ddx = {0,1,0,-1};
		int[] ddy = {-1,0,1,0};
		mList.add(0);
//		locationList.add(new int[] {x,y});
		
		outer:while(true) {
			if(x == 0 && y == 0) break;
			
			for(int i=0;i<cnt;i++) {
				x += ddx[d];
				y += ddy[d];
				if(arr[x][y] > 0)
					mList.add(arr[x][y]);
//				locationList.add(new int[] {x,y});

				if(x == 0 && y == 0) {
					board[x][y] = num;
					break outer;
				}
				
				board[x][y] = num++;
			}
			
			turn++;
			if(turn % 2 == 0) {
				cnt++;
			}
			
			if(d == 3) d = 0;
			else d++;
		}

	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
