package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_10431_줄세우기 {
	
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int P = Integer.parseInt(br.readLine());
		for(int t=1;t<=P;t++) {
			st = new StringTokenizer(br.readLine());
			
			int tc = Integer.parseInt(st.nextToken());
			arr = new int[20];
			for(int i=0;i<20;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			int result = solve();
			
			sb.append(tc+" ").append(result+"\n");
		}

		System.out.println(sb.toString());
	}

	private static int solve() {
		int cnt = 0;
		
		int temp = arr[0];
		
		for(int i=1;i<20;i++) {
			if(temp < arr[i]) {
				temp = arr[i];
				continue;
			}
			// 자기 앞에 자기보다 키가 큰 학생이 한 명 이상 있다면 그중 가장 앞에 있는 학생(A)의 바로 앞에 선다. 
			// 이때, A부터 그 뒤의 모든 학생들은 공간을 만들기 위해 한 발씩 뒤로 물러서게 된다.
			// 919 918 917 916 915 914 913 912 911 910 909 908 907 906 905 904 903 902 901 900
			// 901 902 903 904 905 906 907 908 909 910 911 912 913 914 915 916 917 918 919 900
			
			// 지금까진 오름차순이었지만 현재 값이 튀는 값임.
			// 현재 이 값의 올바른 위치를 찾아내야 함.
			// 현재 인덱스 번호 = i
			// 0 ~ i-1까지 탐색
			int target = arr[i];
			for(int idx=0;idx<i;idx++) {
				if(arr[idx] < target) {
					continue;
				}
				
				// idx가 바로 target이 있어야 할 위치
				else {
					for(int k=i;k>idx;k--) {
						arr[k] = arr[k-1];
					}
					arr[idx] = target;
					cnt += (i-idx);
					break;
				}				
			}
						
		}
		
		return cnt;
	}

}
