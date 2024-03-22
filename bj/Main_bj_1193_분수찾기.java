package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1193_분수찾기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int X = Integer.parseInt(br.readLine());
		int a = 1;
		int b = 1;
		
		if(X > 1) {
			int r = 0;
			int cnt = 0;

			while(cnt < X) {
				r++;
				cnt = r * (r+1) / 2;
			}
			
			int c = X -(r-1) * r / 2;
			if(r % 2 == 0) {
				// 짝수 번째 그룹인 경우, 분자가 증가하고 분모가 감소한다.
				a = c;
				b = r-c+1;
			}
			else {
				// 
				a = r-c+1;
				b = c;
			}
		}
		
		System.out.println(a+"/"+b);
	}

}
