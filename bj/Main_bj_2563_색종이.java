package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_2563_색종이 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int cnt = Integer.parseInt(br.readLine());
		boolean[][] isChecked = new boolean[100][100];
		int result = 0;
		for(int k=0;k<cnt;k++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			for(int i=x;i<x+10;i++) {
				for(int j=y;j<y+10;j++) {
					if(!isChecked[i][j]) {
						isChecked[i][j] = true;
						result++;
					}
				}
			}
		} // input end

		System.out.println(result);
	}

}
