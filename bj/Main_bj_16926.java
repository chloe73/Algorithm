package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_16926 {
	
	static int N,M,R;
	static int[][] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		
		while(R-- > 0) {
			int[][] temp = new int[N][M];
			int ux = 0;
			int ex = N-1;
			int uy = 0;
			int ey = M-1;
			
			while(ux <= ex && uy <= ey) {
				
				// 윗줄 돌리기
				int x = ux;
				int y = ey;
				while(y > uy) {
					temp[x][y-1] = arr[x][y];
					
					y--;
					
				}
				if(y == uy) {
					temp[x+1][y] = arr[x][y];
					
				}
				
				// 왼쪽 열 돌리기
				x = ux+1;
				y = uy;
				while(x < ex) {
					temp[x+1][y] = arr[x][y];
					x++;
				}
				if(x == ex) {
					temp[x][y+1] = arr[x][y];
//					break;
				}
				
				// 아래 행 돌리기
				x = ex;
				y = uy+1;
				while(y < ey) {
					temp[x][y+1] = arr[x][y];
					y++;
				}
				if(y == ey) {
					temp[x-1][y] = arr[x][y];
//					break;
				}
				
				// 오른쪽 열 돌리기
				x = ex-1;
				y = ey;
				while(x > ux) {
					temp[x-1][y] = arr[x][y];
					x--;
				}
				
				ux++;
				ex--;
				
				uy++;
				ey--;
			}
			
			
			arr = temp;

		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				sb.append(arr[i][j]+" ");
			}
			if(i == N-1) continue;
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

}
