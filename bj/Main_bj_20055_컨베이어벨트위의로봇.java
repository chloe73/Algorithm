package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_20055_컨베이어벨트위의로봇 {
	
	static int N,K,result;
	static int[][][] belt;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		belt = new int[2][N][2]; // 내구도,로봇 표시
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			belt[0][i][0] = Integer.parseInt(st.nextToken());	
		}
		for(int i=N-1;i>=0;i--) {
			belt[1][i][0] = Integer.parseInt(st.nextToken());
		} // input end

		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(true) {
			result++;
			
			// *** 언제든지 로봇이 내리는 위치에 도달하면 그 즉시 내린다. *** 
			
			// 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
			move_belt();
			
			// 2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
			// 로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
			move_robot();
			
			// 3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
			add_robot();
			
			// 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
			if(check_a()) break;
		}
	}

	private static boolean check_a() {
		
		int cnt = 0;
		
		for(int i=0;i<2;i++) {
			for(int j=0;j<N;j++) {
				if(belt[i][j][0] == 0) cnt++;
				if(cnt == K) return true;
			}
			if(cnt >= K) return true;
		}
		return false;
	}

	private static void add_robot() {
		
		if(belt[0][0][0] > 0) {
			belt[0][0][0]--;
			belt[0][0][1] = 1;
		}
		
	}

	private static void move_robot() {
		
		if(belt[0][N-1][1] == 1) {
			belt[0][N-1][1] = 0;
		}
		
		for(int i=N-2;i>=0;i--) {
			if(belt[0][i][1] == 1) {
				if(belt[0][i+1][0] > 0 && belt[0][i+1][1] == 0) {
					belt[0][i+1][0]--;
					belt[0][i+1][1] = 1;
					belt[0][i][1] = 0;
				}				
			}
		}
		
		if(belt[0][N-1][1] == 1) {
			belt[0][N-1][1] = 0;
		}
	}

	private static void move_belt() {
		
		// 벨트(내구도) 이동
		int temp = belt[0][0][0];
		int tmp = belt[0][0][1];

		// 아랫줄 이동
		for(int j=0;j<N;j++) {
			if(j==0) {
				belt[0][0][0] = belt[1][j][0];
				belt[0][0][1] = belt[1][j][1];
			}
			else {
				belt[1][j-1][0] = belt[1][j][0];
				belt[1][j-1][1] = belt[1][j][1];
			}
		}

		// 윗줄 이동
		for(int j=N-1;j>0;j--) {
			if(j==N-1) { // 로봇 내리는 위치
				belt[1][j][0] = belt[0][j][0];
				belt[1][j][1] = 0; // 로봇 내리니까 0으로 표시
			}
			else {
				belt[0][j+1][0] = belt[0][j][0];
				belt[0][j+1][1] = belt[0][j][1];
			}
		}
		belt[0][1][0] = temp;
		belt[0][1][1] = tmp;
		
		if(belt[0][N-1][1] == 1) {
			belt[0][N-1][1] = 0;
		}
	}

}
