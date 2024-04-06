package algo.codetree1.firstHalf2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_예술성 {
	
	static int N,result;
	static int[][] board, group;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		result = 0;
		
		makeGroup();
		
		solve();
		
		// 초기 예술 점수, 1회전 이후 예술 점수, 2회전 이후 예술 점수, 그리고 3회전 이후 예술 점수를 모두 합한 값을 출력합니다.
		System.out.println(result);
	}

	private static void solve() {
		
		for(int i=0;i<3;i++) {
			
			rotateMiddle();
			
			rotate();
			
			makeGroup();
		}
	}

	private static void rotate() {
		// 90도 시계방향 회전
		int[][] arr = new int[N][N];
		for(int i=0;i<N;i++) {
			arr[i] = Arrays.copyOf(board[i], N);
		}
		
		int x = 0;
		int y = 0;
		int size = N/2;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				arr[j][size-i-1] = board[i][j];
				
				x = 0;
				y = N/2+1;
				arr[j][N-1-i] = board[x+i][y+j];

				x = N/2+1;
				y = 0;
				arr[x+j][size-i-1] = board[x+i][y+j];

				x = N/2+1;
				y = N/2+1;
				arr[x+j][N-1-i] = board[x+i][y+j];				
			}
		}

		board = arr;
	}

	private static void rotateMiddle() {
		int[][] arr = new int[N][N];
		for(int i=0;i<N;i++) {
			arr[i] = Arrays.copyOf(board[i], N);
		}
		
		int x = N/2-1;
		int y = N/2;
		int cnt = 1;
		while(isValid(x, y)) {
			int nx = x;
			int ny = y;
			// left
			nx += dx[3]*cnt;
			ny += dy[3]*cnt;
			// down
			nx += dx[2]*cnt;
			ny += dy[2]*cnt;
			
			arr[nx][ny] = board[x][y];
			x -= 1;
			cnt++;
		}
		
		x = N/2;
		y = N/2-1;
		cnt = 1;
		while(isValid(x, y)) {
			int nx = x;
			int ny = y;
			
			nx += dx[2]*cnt;
			ny += dy[2]*cnt;
			
			nx += dx[1]*cnt;
			ny += dy[1]*cnt;
			
			arr[nx][ny] = board[x][y];
			y -= 1;
			cnt++;
		}
		
		x = N/2+1;
		y = N/2;
		cnt = 1;
		while(isValid(x, y)) {
			int nx = x;
			int ny = y;
			
			nx += dx[1]*cnt;
			ny += dy[1]*cnt;
			
			nx += dx[0]*cnt;
			ny += dy[0]*cnt;
			
			arr[nx][ny] = board[x][y];
			x++;
			cnt++;
		}
		
		x = N/2;
		y = N/2+1;
		cnt = 1;
		while(isValid(x, y)) {
			int nx = x;
			int ny = y;
			
			nx += dx[0]*cnt;
			ny += dy[0]*cnt;
			
			nx += dx[3]*cnt;
			ny += dy[3]*cnt;
			
			arr[nx][ny] = board[x][y];
			y++;
			cnt++;
		}
		
		board = arr;
	}

	private static void makeGroup() {
		group = new int[N][N];
		boolean[][] visited = new boolean[N][N];
		HashMap<Integer, int[]> map = new HashMap<>();
		
		int groupNum = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j]) {
					Queue<int[]> q = new LinkedList<>();
					q.add(new int[] {i,j});
					visited[i][j] = true;
					int cnt = 0;
					int num = board[i][j];
					
					while(!q.isEmpty()) {
						int[] temp = q.poll();
						int x = temp[0];
						int y = temp[1];
						group[x][y] = groupNum;
						cnt++;
						
						for(int d=0;d<4;d++) {
							int nx = x + dx[d];
							int ny = y + dy[d];
							
							if(!isValid(nx, ny) || visited[nx][ny]) continue;
							
							if(board[x][y] == board[nx][ny]) {
								visited[nx][ny] = true;
								q.add(new int[] {nx,ny});
							}
						}
					}
					map.put(groupNum, new int[] {cnt,num});
					groupNum++;
				}
			}
		}
		
		// 그룹 쌍 간의 조화로움 값이 0보다 큰 조합인 (G1, G2), (G2, G3), (G2, G4), (G3, G4) 의 조화로움 값을 전부 더하면 
		// 48 + 192 + 152 + 156 = 548이 됩니다. 이를 초기 예술 점수라 부르겠습니다.
		HashSet<int[]> set = new HashSet<>(); // 그룹 쌍 정보
		visited = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j]) {
					Queue<int[]> q = new LinkedList<>();
					q.add(new int[] {i,j});
					visited[i][j] = true;
					boolean[] isChecked = new boolean[map.size()+1];
					int[] byun = new int[map.size()+1];
					
					while(!q.isEmpty()) {
						int[] temp = q.poll();
						int x = temp[0];
						int y = temp[1];
						
						for(int d=0;d<4;d++) {
							int nx = x + dx[d];
							int ny = y + dy[d];
							
							if(!isValid(nx, ny) || visited[nx][ny]) continue;
							
							if(group[x][y] != group[nx][ny] && !isChecked[group[nx][ny]]) {
								isChecked[group[nx][ny]] = true;
								byun[group[nx][ny]]++;
								//set.add(new int[] {group[x][y], group[nx][ny]});
								continue;
							}
							
							if(group[x][y] != group[nx][ny] && isChecked[group[nx][ny]]) {
								byun[group[nx][ny]]++;
							}
							
							if(group[x][y] == group[nx][ny]) {
								visited[nx][ny] = true;
								q.add(new int[] {nx,ny});
							}
						}
					}
					
					for(int k=1;k<map.size()+1;k++) {
						if(isChecked[k]) {
							set.add(new int[] {group[i][j], k, byun[k]});
						}
					}
				}
			}
		}
		
		// 예술 점수는 모든 그룹 쌍의 조화로움의 합으로 정의됩니다. 
		// 그룹 a와 그룹 b의 조화로움은 (그룹 a에 속한 칸의 수 + 그룹 b에 속한 칸의 수 ) 
		// x 그룹 a를 이루고 있는 숫자 값 x 그룹 b를 이루고 있는 숫자 값 
		// x 그룹 a와 그룹 b가 서로 맞닿아 있는 변의 수로 정의됩니다.
		for(int[] arr : set) {
			int[] a = map.get(arr[0]); // cnt, num 순서
			int[] b = map.get(arr[1]);
			
			int art = (a[0]+b[0]) * a[1] * b[1] * arr[2];
			result += art;
		}
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0  || r>=N || c>=N) return false;
		return true;
	}

}
