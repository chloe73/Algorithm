package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1205_등수_구하기 {
	
	static int N,score,P,result;
	static ArrayList<Integer> arrList;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		score = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		
		arrList = new ArrayList<>();
		
		if(N == 0) {
			System.out.println(1);
			return;
		}
		else {
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				arrList.add(Integer.parseInt(st.nextToken()));
			}
			
			// arrList크기와 랭킹최대크기가 같은 경우
			if(N == P && score <= arrList.get(arrList.size()-1)) {
				System.out.println(-1);
			}
			else {
				int rank = 1;
				for(int i=0;i<arrList.size();i++) {
					if(score < arrList.get(i)) {
						rank++;
					}
					else
						break;
				}
				System.out.println(rank);
			}
		}
	}

}
