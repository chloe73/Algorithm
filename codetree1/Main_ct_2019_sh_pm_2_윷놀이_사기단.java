package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_ct_2019_sh_pm_2_윷놀이_사기단 {
	
	static int result;
	static int[] cmd;
	// 처음에는 시작 칸에 말 4개가 주어집니다.
	static ArrayList<Horse> hList;
	static int[][] road = {
			{0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,45},
			{10,13,16,19,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45},
			{20,22,24,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45,4},
			{30,28,27,26,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45}
	};
	static class Horse {
		int x,y,num;
		Horse(int x, int y, int num){
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		cmd = new int[10];
		for(int i=0;i<10;i++) {
			cmd[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		result = 0;
		
		int[] arr = new int[10];
		Arrays.fill(arr, -1);
		solve(0,arr);

		System.out.println(result);
	}

	private static void solve(int idx, int[] arr) {
		// 1. 10번의 말 이동순서를 정한다.
		if(idx == 10) {
			// 말의 이동순서가 다 정해지면, 말의 초기위치 초기화
			hList = new ArrayList<>();
			for(int i=0;i<4;i++) {
				hList.add(new Horse(0, 0, 0));
			}
			int num = move_horse(arr);
			if(num == -1) return;
			result = Math.max(result, num);
			return;
		}
		
		for(int i=0;i<4;i++) {
			arr[idx] = i;
			solve(idx+1, arr);
		}
	}

	private static int move_horse(int[] arr) {
		int score = 0;
		// 게임은 10개의 턴으로 이뤄지고 도착칸에 도착하지 않은 말을 골라 
		// 원하는 이동횟수만큼 이동할 수 있습니다.
		// 시작칸과 도착칸을 제외하고는 칸에 말들을 겹쳐서 올릴 수 없습니다.
		
		for(int i=0;i<10;i++) {
			// horseNum = 현재 이동할 말의 번호
			int horseNum = arr[i];
			int x = hList.get(horseNum).x;
			int y = hList.get(horseNum).y;
			
			// cnt = 현재 이동할 칸의 개수
			int cnt = cmd[i];
			
			// 이미 도착한 말을 선택할 수 없다.
			if(hList.get(horseNum).num == 45) return -1;
			
			int ny = y + cnt;
			// 말이 도착 칸으로 이동하면 남은 이동 횟수와 관계 없이 이동을 마칩니다.
			if(ny>21 || road[x][ny] == 45) {
				hList.get(horseNum).y = 21;
				hList.get(horseNum).num = 45;
				continue;
			}
			
			int nextNum = road[x][ny];
			// 10,20,30일 때만, road 위치 바꿔주기
			if(nextNum == 10) {
				x = 1;
				ny = 0;
			}
			else if(nextNum == 20) {
				x = 2;
				ny = 0;
			}
			// nextNum == 30일 때가 문제였음....
			else if(x == 0 && nextNum == 30) {
				x = 3;
				ny = 0;
			}

			// 특정 말을 움직였을 때 도달하게 되는 위치에 다른 말이 이미 있다면, 이는 불가능한 이동임을 의미합니다. 
			if(!check_horse_location(horseNum,x,ny,nextNum)) return -1;
			
			hList.get(horseNum).x = x;
			hList.get(horseNum).y = ny;
			hList.get(horseNum).num = nextNum;

			// 말이 한 번의 이동을 마칠 때마다 칸에 있는 수가 점수에 추가됩니다.
			score += nextNum;
		}
		
		return score;
	}

	private static boolean check_horse_location(int idx, int x, int y, int nextNum) {

		// 25,30,35,40 => 같은 위치에 있는지 체크
		if(nextNum == 25 || nextNum == 35 || nextNum == 40 || 
				(nextNum == 30 && !(x == 3 && y == 0))) {
			for(int i=0;i<4;i++) {
				if(i == idx) continue;
				Horse h = hList.get(i);
				if(h.num == nextNum) return false;
			}
		}
		else {
			for(int i=0;i<4;i++) {
				if(i == idx) continue;
				Horse h = hList.get(i);
				if(h.x == x && h.y == y) return false;
			}
		}
		return true;
	}

}
