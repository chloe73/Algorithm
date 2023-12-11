package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1436_영화감독_숌 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		// 종말의 수란 어떤 수에 6이 적어도 3개 이상 연속으로 들어가는 수를 말한다.
		// 제일 작은 종말의 수는 666이고,
		// 그 다음으로 큰 수는 1666, 2666, 3666, .... 이다.
		
		int end = 666;
		int cnt = 1;
		
		while(cnt != N) {
			end++;
			if(String.valueOf(end).contains("666"))
				cnt++;
		}
		
		System.out.println(end);
	}

}
