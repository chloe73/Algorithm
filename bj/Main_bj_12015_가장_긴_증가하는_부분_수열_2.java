package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_12015_가장_긴_증가하는_부분_수열_2 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] LIS = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		LIS[0] = arr[0];
		int longest = 1;
		
		for(int i=1;i<N;i++) {
			int temp = arr[i];
			
			// 현재 값이 LIS배열의 마지막 값보다 큰 경우 값을 추가해주면 된다.
			if(LIS[longest-1] < temp) {
				longest++;
				LIS[longest-1] = temp;
			}
			else {
				// 아닌 경우, 이분탐색을 진행해야 한다.
				int left = 0;
				int right = longest;
				
				while(left < right) {
					int mid = (left + right) / 2;
					
					if(LIS[mid] < temp) {
						left = mid + 1;
					}
					else right = mid;
				}
				
				LIS[left] = temp;
			}
		}

		System.out.println(longest);
	}

}
