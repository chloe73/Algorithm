package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1987_알파벳 {
	
	static int R,C,result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		for(int i=0;i<R;i++) {
			String s= br.readLine();
			for(int j=0;j<C;j++) {
				board[i][j]	= s.charAt(j);
			}
		} // input end
		
		result = Integer.MIN_VALUE;
		boolean[][] visited = new boolean[R][C];
		boolean[] alphabet = new boolean[26];
		// 좌측 상단에서 시작해서, 말이 최대한 몇 칸을 지날 수 있는지를 구하는 프로그램을 작성하시오.
		solve(0,0,1,visited,alphabet);

		// 첫째 줄에 말이 지날 수 있는 최대의 칸 수를 출력한다.
		System.out.println(result);
	}

	private static void solve(int x, int y, int cnt, boolean[][] visited, boolean[] alphabet) {
		
		visited[x][y] = true;
		alphabet[board[x][y] - 'A'] = true;

		if(cnt > result) {
			result = cnt;
		}
		
		for(int d=0;d<4;d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(!is_valid(nx, ny) || visited[nx][ny]) continue;
			
			int next = board[nx][ny] - 'A';
			if(!alphabet[next]) {
				visited[nx][ny] = true;
				alphabet[next] = true;
				solve(nx, ny, cnt+1, visited, alphabet);
				visited[nx][ny] = false;
				alphabet[next] = false;
			}
		}
		
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}

}