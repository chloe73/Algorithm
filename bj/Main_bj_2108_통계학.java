package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main_bj_2108_통계학 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		
		double sum = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
			sum += arr[i];
			if(map.containsKey(arr[i])) {
				map.put(arr[i], map.get(arr[i])+1);
			}
			else {
				map.put(arr[i], 1);
			}
		} // input end
		
		StringBuilder sb = new StringBuilder();
		// 산술평균 : N개의 수들의 합을 N으로 나눈 값
		sb.append(Math.round(sum / N)+"\n");
		
		// 중앙값 : N개의 수들을 증가하는 순서로 나열했을 경우 그 중앙에 위치하는 값
		Arrays.sort(arr);
		int idx = N / 2;
		sb.append(arr[idx]+"\n");
		
		// 최빈값 : N개의 수들 중 가장 많이 나타나는 값
//		int freq = arr[0];
//		int count = 0;
//		int max = -1;
//		boolean check = false;
//		
//		for(int i=0;i<N-1;i++) {
//			if(arr[i] == arr[i+1]) {
//				count++;
//			}
//			else {
//				count = 0;
//			}
//			
//			if(max < count) {
//				max = count;
//				freq = arr[i];
//				check = true;
//			}
//			else if(max == count && check) {
//				freq = arr[i];
//				check = false;
//			}
//		}
//		sb.append(freq+"\n");

		int cnt = 0;
		int num = Integer.MIN_VALUE;
		ArrayList<Integer> list = new ArrayList<>();
		for(int i : map.keySet()) {
			if(cnt < map.get(i)) {
				num = i;
				cnt = map.get(i);
				list = new ArrayList<>();
				list.add(i);
				continue;
			}
			else if(cnt == map.get(i)) {
				list.add(i);
			}
		}
		Collections.sort(list);
		if(list.size() > 1) {
			num = list.get(1);
		}
		else 
			num = list.get(0);
		sb.append(num+"\n");
		
		// 범위 : N개의 수들 중 최댓값과 최솟값의 차이
		int diff = arr[N-1] - arr[0];
		sb.append(diff+"\n");
		
		System.out.println(sb.toString());
	}

}
