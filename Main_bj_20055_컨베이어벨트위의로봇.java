package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_20055_컨베이어벨트위의로봇 {
	
	static int N,K,result;
	static int[] a,robot;
	static int robot_num = 1;
	static HashMap<Integer,Robot> robotList;
	static class Robot {
		int x,num;
		public Robot(int x, int num) {
			this.x = x;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		a = new int[2*N]; // 내구도 표시
		robot = new int[N]; // 벨트 위 로봇 표시 -> 있다면 로봇 번호 표시 (key)
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<2*N;i++) {
			a[i] = Integer.parseInt(st.nextToken());
		} // input end
		robotList = new HashMap<>();

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
		
		for(int num : a) {
			if(num == 0) cnt++;
			if(cnt == K) return true;
		}
		return false;
	}

	private static void add_robot() {
		
		if(a[0] > 0) {
			a[0]--;
			robot[0] = robot_num;
			robotList.put(robot_num, new Robot(0, robot_num));
			robot_num++;
		}
		
	}

	private static void move_robot() {
		
		if(robotList.size() > 0) {
			
			ArrayList<Integer> removeList = new ArrayList<>();
			
			for(int i=N-2;i>=0;i--) {
				if(robot[i] > 0) { // 로봇이 있는 칸이라면
					int idx = robot[i];
					
					if(i+1 == N-1) {
						if(a[i+1] > 0 && robot[i+1] == 0) {
							a[i+1]--;
							removeList.add(idx);
							robot[i] = 0;
						}
					}
					
					else if(i+1 < N-1) {
						if(a[i+1] > 0 && robot[i+1] == 0) {
							a[i+1]--;
							robot[i] = 0;
							robot[i+1] = idx;
							robotList.get(idx).x++;
						}
					}
				}
			}
			
			if(robot[N-1] > 0) {
				removeList.add(robot[N-1]);
				robot[N-1] = 0;
			}
			
//			for(int i : robotList.keySet()) {
//				int x = robotList.get(i).x; // 현재 로봇의 위치
//				
//				if(x+1 == N-1) {
//					if(a[x+1] > 0 && robot[x+1] == 0) {
//						a[x+1]--;
//						removeList.add(i);
//						robot[x] = 0;
//					}
//				}
//				
//				else if(x+1 < N-1) {
//					if(a[x+1] > 0 && robot[x+1] == 0) {
//						robot[x] = 0;
//						a[x+1]--;
//						robotList.get(i).x++;
//						robot[x+1] = i;
//					}
//				}
//			}
			
			for(int i : removeList) {
				robotList.remove(i);
			}
		}
		
	}

	private static void move_belt() {
		
		int temp = a[2*N-1];
		
		for(int i=2*N-2;i>=0;i--) {
			a[i+1] = a[i];
		}
		
		a[0] = temp;
		
		// 로봇 한 칸씩 이동
		if(robotList.size() > 0) {
			
			ArrayList<Integer> removeList = new ArrayList<>();
			
			for(int i : robotList.keySet()) {
				int x = robotList.get(i).x; // 현재 로봇의 위
				robot[x] = 0;
				
				if(x == N-2) { // 내리는 위치에 로봇이 도달한다면 로봇 지우기
					removeList.add(i);
				}
				
				else if(x < N-2) {
					robotList.get(i).x++;
					robot[x+1] = i;
				}
			}
			
			if(robot[N-1] > 0) {{
				removeList.add(robot[N-1]);
			}
			
			for(int i : removeList) {
				robotList.remove(i);
			}
		}
		
	}

}
