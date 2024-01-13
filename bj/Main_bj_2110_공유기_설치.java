package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_2110_공유기_설치 {
	
	static int N,C;
	static int[] home;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		home = new int[N];
		
		for(int i=0;i<N;i++) {
			home[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(home);
		
		int left = 1; // 집 간 최소거리 최솟값
		int right = home[N-1] - home[0] + 1; // 집 간 최소거리 최댓값
		
		while(left < right) {
			int mid = (left+right) / 2;
			
			if(is_possible(mid) < C) {
				// mid거리로 설치 가능한 공유기 개수가 C보다 작으면 계속 진행
				// 이 경우 최소거리를 줄여야 한다.
				right = mid;
			}
			else {
				// mid거리로 설치 가능한 공유기 개수가 C보다 크면
				// 최소거리를 더 늘리기 위해 계속 진행
				left = mid+1;
			}
		}
		
		System.out.println(left-1);
	}

	private static int is_possible(int mid) {
		int cnt = 1;
		int beforeHome = home[0];
		
		for(int i=1;i<N;i++) {
			int temp = home[i];
			
			if(temp - beforeHome >= mid) {
				cnt++;
				beforeHome = temp;
			}
		}
		
		return cnt;
	}

}
