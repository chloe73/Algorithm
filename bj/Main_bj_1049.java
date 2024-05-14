package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_1049 {
	
	static int N,M,result;
	static ArrayList<int[]> list;
	static int minP, minB;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList<>();
		// M개의 줄에는 각 브랜드의 패키지 가격과 낱개의 가격이 공백으로 구분하여 주어진다.
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // 6개 묶음 가격
			int b = Integer.parseInt(st.nextToken());// 낱개 가격
			int c = 6 * b; // 낱개 가격 기준으로 6개 가격
			list.add(new int[] {a,b,c});
		} // input end
		
		result = 0;
		
		Collections.sort(list, (o1,o2) -> {
			return o1[1] - o2[1]; // 낱개 가격 오름차순 정렬
		});
		minB = list.get(0)[1]; // 낱개 최소 가격
		
		// N이 6보다 작은 경우
		if(N < 6) {
			result = minB * N;
		}
		else {
			// 6보다 클 때에는 경우의 수가 나뉜다.
			ArrayList<Integer> resultList = new ArrayList<>();
			
			// 1. 낱걔의 가격으로만 최소 가격을 이루는 경우
			int price = list.get(0)[1] * N;
			resultList.add(price);
			
			Collections.sort(list, (o1,o2) -> {
				return o1[0] - o2[0]; // 6개 묶음 가격으로 오름차순 정렬
			});
			minP = list.get(0)[0];
			
			// 2. 6개의 묶음이랑 낱개 구성으로 최소를 이루는 경우
			// 여기서도 경우의 수가 나뉜다.
			int cnt = N / 6;
			if(N % 6 > 0) {
				cnt++;
				resultList.add(minP * cnt);
				cnt--;
			}
			
			while(cnt > 0) {
				int rest = N - (6*cnt); // 패키지 묶음을 제외한 나머지 낱개의 개수
				resultList.add(cnt*minP + rest*minB);
				cnt--;
			}
			
			Collections.sort(resultList);
			result = resultList.get(0);
		}
		
		// 첫째 줄에 기타줄을 적어도 N개 사기 위해 필요한 돈의 최솟값을 출력한다.
		System.out.println(result);
	}

}
