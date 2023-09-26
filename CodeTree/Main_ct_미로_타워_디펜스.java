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
	static int[][] numBoard;
	static int[][] monsterBoard;
	//                → ↓ ← ↑
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static ArrayList<Monster> monsterList;
	static class Monster {
		int num;
		public Monster(int num) {
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		numBoard = new int[N][N];
		monsterList = new ArrayList<>(); // Monster List
		monsterBoard = new int[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int num = Integer.parseInt(st.nextToken());
				
				if(num > 0)
					monsterBoard[i][j] = num;
			}
		}
		
		init_list();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int d = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			
			solve(d,p);
		}
		
		System.out.println(result);
	}

	private static void init_list() {
		
		int tx = N / 2;
		int ty = N / 2;
		numBoard[tx][ty] = -1;
		int d = 0;
		int cnt = 1;
		int round = 0;
		
		//          왼 - 아래 - 오 - 위
		int[] ddx = {0,1,0,-1};
		int[] ddy = {-1,0,1,0};
		
		int idx = 0;
		
		outer:while(is_valid(tx, ty)) {
			
			for(int i=0;i<cnt;i++) {
				tx += ddx[d];
				ty += ddy[d];
				
				if(!is_valid(tx,ty)) break outer;
				
				if(is_valid(tx,ty) && monsterBoard[tx][ty] == 0) {
					numBoard[tx][ty] = idx++;
					continue;
				}
				
				numBoard[tx][ty] = idx++;
				monsterList.add(new Monster(monsterBoard[tx][ty]));
			}
			
			d = change_dir(d);
			
			round++;
			
			if(round % 2 == 0) cnt++;
		}
	}

	private static void solve(int d, int p) {
		// 1. 플레이어는 상하좌우 방향 중 주어진 공격 칸 수만큼 몬스터를 공격하여 없앨 수 있습니다.
		// 2. 비어있는 공간만큼 몬스터는 앞으로 이동하여 빈 공간을 채웁니다.
		attack_monster(d,p);
		
		// 3. 이때 몬스터의 종류가 4번 이상 반복하여 나오면 해당 몬스터 또한 삭제됩니다. 해당 몬스터들은 동시에 사라집니다.
		// 삭제된 이후에는 몬스터들을 앞으로 당겨주고, 4번 이상 나오는 몬스터가 있을 경우 또 삭제를 해줍니다. 4번 이상 나오는 몬스터가 없을 때까지 반복해줍니다.
		while(is_possible())
			remove_monster();
		
		// 삭제가 끝난 다음에는 몬스터를 차례대로 나열했을 때 같은 숫자끼리 짝을 지어줍니다.
		// 이후 각각의 짝을 (총 개수, 숫자의 크기)로 바꾸어서 다시 미로 속에 집어넣습니다.
		make_set();
		
		update_monsterBoard();
	}
	
	private static void update_monsterBoard() {
		monsterBoard = new int[N][N];
		
		int tx = N / 2;
		int ty = N / 2;

		int d = 0;
		int cnt = 1;
		int round = 0;
		
		//          왼 - 아래 - 오 - 위
		int[] ddx = {0,1,0,-1};
		int[] ddy = {-1,0,1,0};
		
		outer:while(is_valid(tx, ty)) {
			
			for(int i=0;i<cnt;i++) {
				tx += ddx[d];
				ty += ddy[d];
				
				if(!is_valid(tx,ty) || numBoard[tx][ty] >= monsterList.size()) break outer;
				
				monsterBoard[tx][ty] = monsterList.get(numBoard[tx][ty]).num;
			}
			
			d = change_dir(d);
			
			round++;
			
			if(round % 2 == 0) cnt++;
		}
	}

	private static void make_set() {
		ArrayList<Integer> renewalList = new ArrayList<>();
		
		int number = monsterList.get(0).num;
		int cnt = 1;
		
		for(int i=1;i<monsterList.size();i++) {
			if(number == monsterList.get(i).num) {
				cnt++;
				continue;
			}
			
			if(number != monsterList.get(i).num) {
				renewalList.add(cnt);
				renewalList.add(number);
				cnt = 1;
				number = monsterList.get(i).num;
			}
		}
		
		renewalList.add(cnt);
		renewalList.add(number);
		
		monsterList.clear();
		for(int i=0;i<renewalList.size();i++) {
			int m = renewalList.get(i);
			
			// 만약 새로 생긴 배열이 원래 격자의 범위를 넘는다면 나머지 배열은 무시합니다.
			if(i > numBoard[0][0]) break;
			
			monsterList.add(new Monster(m));
		}
	}

	private static boolean is_possible() {
		boolean flag = false;
		int cnt = 1;
		int temp = monsterList.get(0).num;
		
		for(int i=1;i<monsterList.size();i++) {
			if(cnt >= 4) return true;
			
			if(temp == monsterList.get(i).num) {
				cnt++;
				continue;
			}
			
			if(temp != monsterList.get(i).num) {
				cnt = 1;
				temp = monsterList.get(i).num;
				continue;
			}
		}
		
		if(cnt >= 4) return true;
		
		return flag;
	}
	
	private static void remove_monster() {
		
		// 3. 이때 몬스터의 종류가 4번 이상 반복하여 나오면 해당 몬스터 또한 삭제됩니다. 해당 몬스터들은 동시에 사라집니다.
		// 삭제된 이후에는 몬스터들을 앞으로 당겨주고, 4번 이상 나오는 몬스터가 있을 경우 또 삭제를 해줍니다. 4번 이상 나오는 몬스터가 없을 때까지 반복해줍니다.
		Queue<int[]> q = new LinkedList<int[]>(); // {index, cnt}

		int temp = monsterList.get(0).num;
		int cnt = 1;
		
		for(int i=1;i<monsterList.size();i++) {
			if(temp == monsterList.get(i).num) {
				cnt++;
				continue;
			}
			
			if(temp != monsterList.get(i).num) {
				if(cnt >= 4) {
					q.add(new int[] {i-1,cnt});
				}
				cnt = 1;
				temp = monsterList.get(i).num;
				continue;
			}
		}
		
		if(cnt >= 4) {
			q.add(new int[] {monsterList.size()-1,cnt});
		}
		
		ArrayList<Monster> removeList = new ArrayList<>();
		// 한 번에 삭제할 monster 정보
		while(!q.isEmpty()) {
			int[] tmp = q.poll();
			int idx = tmp[0];
			int count = tmp[1];
			
			for(int i=0;i<count;i++) {
				int num = monsterList.get(idx).num;
				result += num;
				removeList.add(monsterList.get(idx--));
			}
		}
		
		monsterList.removeAll(removeList);
	}

	private static void attack_monster(int d, int p) {
		int tx = N / 2;
		int ty = N / 2;
		
		ArrayList<Monster> removeList = new ArrayList<>();
		
		for(int i=0;i<p;i++) {
			tx += dx[d];
			ty += dy[d];
			
			if(monsterBoard[tx][ty] == 0) continue;
			
			if(monsterBoard[tx][ty] > 0) {
				monsterBoard[tx][ty] = 0;
				result += monsterList.get(numBoard[tx][ty]).num;
				removeList.add(monsterList.get(numBoard[tx][ty]));
			}
		}
		
		monsterList.removeAll(removeList);
		
		// 비어있는 공간만큼 몬스터는 앞으로 이동하여 빈 공간을 채웁니다.
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