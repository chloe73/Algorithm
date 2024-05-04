package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_bj_11651 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		ArrayList<int[]> list = new ArrayList<>();
		// 좌표를 y좌표가 증가하는 순으로, y좌표가 같으면 x좌표가 증가하는 순서로 정렬한 다음 출력하는 프로그램을 작성하시오.
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			list.add(new int[] {x,y});
		}
		Collections.sort(list, (o1,o2) -> {
			if(o1[1] == o2[1])
				return o1[0]-o2[0];
			return o1[1] - o2[1];
		});
		
		StringBuilder sb = new StringBuilder();
		for(int[] temp : list) {
			sb.append(temp[0]+" "+temp[1]+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
