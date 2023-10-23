package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_2531_회전_초밥 {
	
	static int N,d,k,c,result;
	static int[] arr;
	static boolean[] visited; // 숫자 같은 거 있는지 확인하기 위한 변수

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 회전 초밥 벨트에 놓인 접시의 수
		d = Integer.parseInt(st.nextToken()); // 초밥 번호
		k = Integer.parseInt(st.nextToken()); // 연속해서 먹는 접시의 수
		c = Integer.parseInt(st.nextToken()); // 쿠폰 번호

		arr = new int[N];
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int i=0;i<N;i++) {
			ArrayList<Integer> tempList = new ArrayList<>();
			visited = new boolean[d+1];
			int cnt = 0;
			
			for(int j=0;j<k;j++) {
				if(0 <= i+j && i+j < N) {
					int num = arr[i+j];
					tempList.add(num);
					if(!visited[num]) {
						visited[num] = true;
						cnt++;
					}
					continue;
				}
				else {
					// 현재 인덱스 번호가 범위를 벗어난 경우
					// 숫자 확인 필요
					int idx = (i+j) % N;
					int num = arr[idx];
					tempList.add(num);
					if(!visited[num]) {
						visited[num] = true;
						cnt++;
					}
					continue;
				}
			}
			
			if(!visited[c]) {
				visited[c] = true;
				cnt++;
			}
			
			if(result < cnt) {
				result = cnt;
			}
			
		}
		
	}

}
