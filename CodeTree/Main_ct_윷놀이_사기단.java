package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_윷놀이_사기단 {
	
	static int result;
	static int[] cmd, order;
	static ArrayList<Horse> horseList;
	static class Horse {
		int x,y,num;
		public Horse(int x, int y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
	};
	// board 크기 row = 7, col = 9
	static int[][] board = {
			{0, 2, 4, 6, 8, 10,-1,-1,-1},
			{10,13,16,19,25,30,35,40,45},
			{10,12,14,16,18,20,-1,-1,-1},
			{20,22,24,25,30,35,40,45,-1},
			{20,22,24,26,28,30,-1,-1,-1},
			{30,28,27,26,25,30,35,40,45},
			{30,32,34,36,38,40,45,-1,-1}
	};
	static boolean[][] visited;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		cmd = new int[10];
		order = new int[10];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<10;i++) {
			cmd[i] = Integer.parseInt(st.nextToken());
		}
		
		perm(0,0);

		System.out.println(result);
	}

	private static void perm(int idx, int cnt) {
		// 각 10번의 이동에 대해 말 1~4번까지 조합 구성하기
		if(cnt == 10) {
			horseList = new ArrayList<>();
			for(int i=0;i<4;i++) {
				horseList.add(new Horse(0, 0, 0)); // 초기 말 시작 점에 있다.
			}
			visited = new boolean[7][9];
			int score = start_game();
			if(score == -1) return; // 불가능한 경우
			result = Math.max(result, score);
			return;
		}
		
		for(int i=0;i<4;i++) {
			order[idx] = i;
			perm(idx+1,cnt+1);
		}
	}

	private static int start_game() {
		int score = 0;
		
		for(int i=0;i<10;i++) {
			// 순서가 정해진 말 배열 탐색
			int horseNum = order[i]; // 이동할 말의 숫자 (0 ~ 3)
			int horseCnt = cmd[i]; // 이동할 칸의 수
			
			// 이미 도착한 칸에 있는 말을 선택한 경우 -> 불가능한 이동이다.
			if(horseList.get(horseNum).num == 45) return -1;
			
			// 주어진 숫자의 말 주어진 칸의 수만큼 이동
			// 주어진 말이 이동을 마쳤을 때, 해당 칸에 이미 다른 말이 있는 경우 -> 불가능한 이동이다.
			if(!move_horse(horseNum, horseCnt)) return -1;
			
			// 도착한 칸의 숫자만큼 점수 획득
			score += get_score(horseNum);
		}
		
		return score;
	}

	private static int get_score(int horseNum) {
		int score = horseList.get(horseNum).num;
		if(score == 45) return 0; // 이미 도착점에 있는 경우 0
		return score;
	}

	private static boolean move_horse(int num, int cnt) {
		boolean flag = true;
		// 특정 말을 움직였을 때 도달하게 되는 위치에 다른 말이 이미 있다면, 이는 불가능한 이동임을 의미합니다.
		int x = horseList.get(num).x;
		int y = horseList.get(num).y;
		
		// 이동하기 전 기존 위치 visited false로 변경
		visited[x][y] = false;
		
		// cnt 만큼 이동
		for(int i=0;i<cnt;i++) {
			// 이동하다가 이미 도착 점에 도달한 경우
			if(board[x][y+1] == 45) {
				y++;
				break;
			}
			
			if(board[x][y+1] > 0) {
				y++;
				continue;
			}
			
			if(board[x][y+1] == -1) {
				
				if(x == 0) {
					x = 2;
					y = 1;
					continue;
				}
				else if(x == 2) {
					x = 4;
					y = 1;
					continue;
				}
				else if(x == 4) {
					x = 6;
					y = 1;
				}
				
			}
		}
		
		// 도착점이 10,20,30인 경우
		if(board[x][y] == 10) {
			x = 1;
			y = 0;
		}
		else if(board[x][y] == 20) {
			x = 3;
			y = 0;
		}
		else if(board[x][y] == 30 && x == 4) {
			x = 5;
			y = 0;
		}
		
		// cnt 만큼 이동을 마친 다음, 해당 칸에 다른 말이 있다면 불가능한 경우
		if(!check_destination(x,y)) return false;
		
		// 이동을 마친 뒤, 해당 칸에 visited = true 로 표시
		visited[x][y] = true;
		horseList.get(num).x = x;
		horseList.get(num).y = y;
		horseList.get(num).num = board[x][y];
		
		return flag;
	}

	private static boolean check_destination(int x, int y) {
		if(board[x][y] == 45) return true;
		if(board[x][y] == 25 && (x == 1 || x == 3 || x == 5)) {
			if(visited[1][4] || visited[3][3] || visited[5][4])
				return false;
		}
		if(board[x][y] == 30 && (x == 1 || x == 3 || (x == 5 && y == 5))) {
			if(visited[1][5] || visited[3][4] || visited[5][5])
				return false;
		}
		if(board[x][y] == 35 && (x == 1 || x == 3 || x == 5)) {
			if(visited[1][6] || visited[3][5] || visited[5][6])
				return false;
		}
		if(board[x][y] == 40 && (x == 1 || x == 3 || x == 5 || x == 6)) {
			if(visited[1][7] || visited[3][6] || visited[5][7] || visited[6][5])
				return false;
		}
		if(visited[x][y]) return false;
		return true;
	}
	
}
