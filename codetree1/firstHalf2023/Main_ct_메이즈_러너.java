package algo.codetree1.firstHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_메이즈_러너 {
	
	static int N,M,K,ex,ey,totalDist;
	static int[][] board;
	static HashSet<Integer>[][] visited; // 참가자 위치, 출구 위치 표시
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static HashMap<Integer, Person> pMap;
	static class Person {
		int x,y,dist;
		boolean isEscaped;
		public Person(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.isEscaped = false;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		visited = new HashSet[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				visited[i][j] = new HashSet<>();
			}
		}
		
		pMap = new HashMap<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			pMap.put(i+1, new Person(x, y, 0));
			visited[x][y].add(i+1);
		}
		
		st = new StringTokenizer(br.readLine());
		ex = Integer.parseInt(st.nextToken())-1;
		ey = Integer.parseInt(st.nextToken())-1;
		totalDist = 0;
		
		solve();
		
		StringBuilder sb = new StringBuilder();
		ex++;
		ey++;
		sb.append(totalDist+"\n");
		sb.append(ex+" "+ey);
		
		// 게임 시작 후 K초가 지났거나, 모든 참가자가 미로를 탈출했을 때, 모든 참가자들의 이동 거리 합과 출구 좌표를 출력합니다.
		System.out.println(sb.toString());
	}

	private static void solve() {
		// K초 동안 위의 과정을 계속 반복됩니다. 
		while(K-- > 0) {
			// 만약 K초 전에 모든 참가자가 탈출에 성공한다면, 게임이 끝납니다. 
			if(isAllEscaped()) break;
			
//			1초마다 모든 참가자는 한 칸씩 움직입니다. 움직이는 조건은 다음과 같습니다.
			movePerson();
			
//			모든 참가자가 이동을 끝냈으면, 다음 조건에 의해 미로가 회전합니다.
//			한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 잡습니다.
//			가장 작은 크기를 갖는 정사각형이 2개 이상이라면, 좌상단 r 좌표가 작은 것이 우선되고, 그래도 같으면 c 좌표가 작은 것이 우선됩니다.
			int x = ex;
			int y = ey;
			int s = 1;
			outer1:for(int size=1;size<=N;size++) {
				for(int i=0;i<N-size+1;i++) {
					for(int j=0;j<N-size+1;j++) {
						boolean flagE = false;
						boolean flagP = false;
						outer2:for(int r=i;r<i+size;r++) {
							for(int c=j;c<j+size;c++) {
								if(r == ex && c == ey) {
									flagE = true;
									if(flagE && flagP) {
										break outer2;
									}
									continue;
								}
								if(visited[r][c].size() > 0) {
									flagP = true;
									if(flagE && flagP) {
										break outer2;
									}
									continue;
								}
							}
						}
						
						if(flagE && flagP) {
							x = i;
							y = j;
							s = size;
							break outer1;
						}
					}
				}
			}
			rotate(x,y,s);
			
//			System.out.println("=== : "+totalDist +", "+ ex+", "+ey);
		}
	}
	
	private static void rotate(int x, int y, int size) {
//		선택된 정사각형은 시계방향으로 90도 회전하며, 회전된 벽은 내구도가 1씩 깎입니다.
		int[][] arr = new int[N][N]; // 내구도 회전된 후 배열

		ArrayList<Integer> moveList = new ArrayList<>();
		int eex = -1;
		int eey = -1;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(x+i == ex && y+j == ey) {
					eex = x+j;
					eey = y+size-i-1;
					continue;
				}
				if(board[x+i][y+j] > 0) {
					arr[x+j][y+size-i-1] = board[x+i][y+j];
					board[x+i][y+j] = 0;
				}
				if(visited[x+i][y+j].size() > 0) {
					Queue<Integer> q = new LinkedList<>();
					for(int key : visited[x+i][y+j]) {
						q.add(key);
					}
					visited[x+i][y+j].clear();
					while(!q.isEmpty()) {
						int num = q.poll();
						Person tmp = pMap.get(num);
						tmp.x = x+j;
						tmp.y = y+size-i-1;
//						visited[tmp.x][tmp.y].add(num);
						moveList.add(num);
					}
				}
			}
		}
		
		ex = eex;
		ey = eey;
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(arr[x+i][y+j] > 0) arr[x+i][y+j]--;
				board[x+i][y+j] = arr[x+i][y+j];
			}
		}
		
		for(int i : moveList) {
			Person tmp = pMap.get(i);
			visited[tmp.x][tmp.y].add(i);
		}
	}

	private static void movePerson() {
//		모든 참가자는 동시에 움직입니다.
//		한 칸에 2명 이상의 참가자가 있을 수 있습니다.
		
		for(int i : pMap.keySet()) {
			Person temp = pMap.get(i);
			
			if(temp.isEscaped) continue;
			
//			두 위치 (x1,y1), (x2,y2)의 최단거리는 ∣x1−x2∣+∣y1−y2∣로 정의됩니다.
			int tempDist = Math.abs(temp.x-ex)+Math.abs(temp.y-ey);
			int nx = temp.x;
			int ny = temp.y;
			boolean flag = false; // 움직일 수 있는지 확인하기 위한 변수
			
//			움직일 수 있는 칸이 2개 이상이라면, 상하로 움직이는 것을 우선시합니다.
			for(int d=0;d<4;d++) {
				nx = temp.x + dx[d];
				ny = temp.y + dy[d];
				
//				상하좌우로 움직일 수 있으며, 벽이 없는 곳으로 이동할 수 있습니다.
				if(!isValid(nx, ny) || board[nx][ny] > 0) continue;
				
				int distance = Math.abs(nx-ex)+Math.abs(ny-ey);
				
//				움직인 칸은 현재 머물러 있던 칸보다 출구까지의 최단 거리가 가까워야 합니다.
				if(distance < tempDist) {
					flag = true;
					break;
				}
			}
			
//			참가가가 움직일 수 없는 상황이라면, 움직이지 않습니다.
			if(flag) {
				totalDist++;
				visited[temp.x][temp.y].remove(i);
				temp.x = nx;
				temp.y = ny;
				temp.dist += 1;
				// 출구에 도착한 경우, 바로 탈출함.
				if(temp.x == ex && temp.y == ey) {
					temp.isEscaped = true;
					continue;
				}
				visited[temp.x][temp.y].add(i);
			}
		}
	}
	

	private static boolean isAllEscaped() {
		
		for(int i : pMap.keySet()) {
			if(!pMap.get(i).isEscaped) return false;
		}
		
		return true;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
