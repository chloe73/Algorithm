package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_13305_주유소 {
	
	static int N;
	static int[] roadDistance,oilPrice;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		roadDistance = new int[N-1]; // 거리
		oilPrice = new int[N]; // 1리터 당 기름 가격
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N-1;i++) {
			roadDistance[i] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			oilPrice[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		// 그리디 알고리즘
		
		// 4
		// 2 3 1
		// 5 2 4 1
		// : (2거리 * 5원) + (3거리 * 2원) + (1거리 * 2원) = 18원
		
		// 4
		// 3 3 4
		// 1 1 1 1
		
		long result = 0;
		long minCost = oilPrice[0];
		
		// 현재까지 탐색한 주유소 중에서 가장 싼 곳에서
		// 지금까지 이동한 거리를 
		for(int i=0;i<N-1;i++) {
			// 현재 주유소가 이전 주유소보다 더 싼 경우
			if(oilPrice[i] < minCost) {
				minCost = oilPrice[i];
			}
			
			result += (minCost * roadDistance[i]);
		}
		
		// 제일 왼쪽 도시에서 제일 오른쪽 도시로 가는 최소 비용을 출력한다.
		System.out.println(result);
	}

}
