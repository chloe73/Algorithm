package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_ct_2019_sh_pm_2_윷놀이_사기단 {
	
	static int result;
	static int[] cmd;
	// 처음에는 시작 칸에 말 4개가 주어집니다.
	static int[] hx,hy;
	static int[][] road = {
			{0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,45},
			{10,13,16,19,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45},
			{20,22,24,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45,4},
			{30,28,27,26,25,30,35,40,45,45,45,45,45,45,45,45,45,45,45,45,45,45}
	};

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
			hx = new int[4];
			hy = new int[4];
			int num = move_horse(arr);
			if(num == -1) return;
//			System.out.println("num : "+num);
			result = Math.max(result, num);
			return;
		}
		
		for(int i=0;i<4;i++) {
			arr[idx] = i;
			solve(idx+1, arr);
//			arr[idx] = -1;
		}
	}

	private static int move_horse(int[] arr) {
		int score = 0;
		// 게임은 10개의 턴으로 이뤄지고 도착칸에 도착하지 않은 말을 골라 
		// 원하는 이동횟수만큼 이동할 수 있습니다.
		// 시작칸과 도착칸을 제외하고는 칸에 말들을 겹쳐서 올릴 수 없습니다.
		
		for(int i=0;i<10;i++) {
			// temp = 현재 이동할 말의 번호
			int temp = arr[i];
			int x = hx[temp];
			int y = hy[temp];

			// cnt = 현재 이동할 칸의 개수
			int cnt = cmd[i];
			
			// 이미 도착한 말을 선택할 수 없다.
			if(road[x][y] == 45) return -1;
			
			if(y+cnt >= road[x].length || road[x][y+cnt] == 45) {
				// 말이 도착 칸으로 이동하면 남은 이동 횟수와 관계 없이 이동을 마칩니다.
				// 도착점에 도착하는 경우
				x = 0;
				y = 21;
				continue;
			}
			
			int nextNum = road[x][y+cnt];
			
			if(nextNum == 10) {
				// 특정 말을 움직였을 때 도달하게 되는 위치에 다른 말이 이미 있다면, 이는 불가능한 이동임을 의미합니다. 
				if(!check_horse_location(temp, 1, 0, nextNum)) return -1;
				// 다음 칸으로 이동 가능함.
				hx[temp] = 1;
				hy[temp] = 0;
			}
			else if(nextNum == 20) {
				if(!check_horse_location(temp, 2, 0, nextNum)) return -1;
				hx[temp] = 2;
				hy[temp] = 0;
			}
			else if(nextNum == 30) {
				if(!check_horse_location(temp, 3, 0, nextNum)) return -1;
				hx[temp] = 3;
				hy[temp] = 0;
			}
			// 25,30,35,40 => 같은 위치에 있는지 체크
			else if(nextNum == 25 || nextNum == 30 || nextNum == 35 || nextNum == 40) {
				if(!check_horse_location(temp, x, y+cnt, nextNum)) return -1;
				hy[temp] = y+cnt;
			}
			else {
				if(!check_horse_location(temp, x, y+cnt, nextNum)) return -1;
				// 다음 칸으로 이동 가능함.
				hy[temp] = y+cnt;
			}
			// 말이 한 번의 이동을 마칠 때마다 칸에 있는 수가 점수에 추가됩니다.
			score += nextNum;
		}
		
		return score;
	}
	
	private static boolean check_horse_location(int idx, int x, int y, int num) {
		
		for(int i=0;i<4;i++) {
			if(i == idx) continue;
			if(num == 25 || num == 30 || num == 35 || num == 40) {
				if(num == road[hx[i]][hy[i]]) return false;
			}
			if(hx[i] == x && hy[i] == y) return false;
		}
		
		return true;
	}

}
