package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 이분 탐색 문제
public class Main_bj_10816_숫자_카드_2 {
	
	static int N,M;
	static int[] arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 숫자 카드 N개를 가지고 있다.
		// 정수 M개가 주어졌을 때, 이 수가 적혀있는 숫자 카드를
		// 상근이가 몇 개 가지고 있는지 구하는 프로그램 작성
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr); // N개의 숫자 오름차순 정렬
		
		M = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			int num = Integer.parseInt(st.nextToken());
			int cnt = 0;
			
			cnt = upper(num) - lower(num);
			
			sb.append(cnt+" ");
		}
		
		System.out.println(sb.toString());
	}
	
	private static int upper(int target) {
		int left = 0;
		int right = arr.length;
		
		while(left < right) {
			int mid = (left + right) / 2;
			if(target < arr[mid]) right = mid;
			else left = mid + 1;
		}
		
		return left;
	}
	
	private static int lower(int target) {
		int left = 0;
		int right = arr.length;
		
		while(left < right) {
			int mid = (left + right ) / 2;
			if(target <= arr[mid]) right = mid;
			else left = mid + 1;
		}
		
		return left;
	}

}
