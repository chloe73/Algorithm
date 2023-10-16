package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_3190_뱀 {
	
	static int N,K,L,result;
	static ArrayList<int[]> snake;
	static boolean[][] apple;
	static HashMap<Integer, String> cmdList;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		
		// K개의 줄에는 사과의 위치가 주어짐
		apple = new boolean[N][N];
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			
			apple[r][c] = true;
		}
		
		L = Integer.parseInt(br.readLine());
		cmdList = new HashMap<>();
		snake = new ArrayList<>();
		
		// L개의 줄에는 뱀의 방향 변환 정보가 주어짐
		for(int i=0;i<L;i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			String c = st.nextToken();
			
			cmdList.put(x, c);
		}
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		// snake는 초기에 (0,0)에서 시작, 방향은 오른쪽
		int sx = 0;
		int sy = 0;
		int sd = 1;
		
		snake.add(new int[] {sx,sy});
		
		while(true) {
			result++;
			
			// 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
			// 1. 현재 위치에서 현재 바라보는 방향으로 한 칸 이동한다.
			int nx = sx + dx[sd];
			int ny = sy + dy[sd];
			
			// 2. 만약 범위를 벗어났거나 본인 몸에 닿았으면 게임 종료 !
			if(!is_valid(nx, ny) || is_touched(nx, ny)) break;
			
			// 3. 만약 이동한 칸에 사과가 있다면,
			if(apple[nx][ny]) {
				// 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
				apple[nx][ny] = false;
				snake.add(new int[] {nx,ny});
			}
			else {
				snake.add(new int[] {nx,ny});
				snake.remove(0);
			}
			
			// 현재 시간이 방향을 바꿔야 하는 타이밍이라면
			if(cmdList.containsKey(result)) {
				if(cmdList.get(result).equals("L")) {
					// 왼쪽으로 90도 회전
					sd -= 1;
					if(sd == -1)
						sd = 3;
				}
				else {
					// 오른쪽으로 90도 회전
					sd += 1;
					if(sd == 4)
						sd = 0;
				}
			}
			
			// 현재 뱀의 머리 위치 업데이트
			sx = nx;
			sy = ny;
		}
	}
	
	// snake가 범위 밖으로 벗어나면 그 순간 게임 종료 !
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>= N) return false;
		return true;
	}
	
	// snake가 이동 중 본인의 몸에 닿았으면 게임 종료 !
	private static boolean is_touched(int r, int c) {
		for(int[] temp : snake) {
			if(temp[0] == r && temp[1] == c) return true;
		}
		
		return false;
	}

}