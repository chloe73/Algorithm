package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_14003_가장_긴_증가하는_부분_수열_5 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		
		int[] arr = new int[N];
		int[] indexArr = new int[N];
		ArrayList<Integer> list = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		list.add(Integer.MIN_VALUE);
		
		for(int i=0;i<N;i++) {
			int temp = arr[i];
			
			if(list.get(list.size()-1) < temp) {
				list.add(temp);
				indexArr[i] = list.size()-1;
			}
			else {
				int left = 0;
				int right = list.size()-1;
				
				while(left < right) {
					int mid = (left+right) / 2;
					
					if(list.get(mid) < temp) {
						left = mid + 1;
					}
					else {
						right = mid;
					}
				}
				list.set(left, temp);
				indexArr[i] = left;
			}
		}
		
		sb.append(list.size()-1+"\n");
		Stack<Integer> stack = new Stack<>();
		
		int index = list.size()-1;
		
		for(int i=N-1;i>=0;i--) {
			if(indexArr[i] == index) {
				index--;
				stack.push(arr[i]);
			}
		}
		
		while(!stack.isEmpty()) {
			sb.append(stack.pop()+" ");
		}
		
		System.out.println(sb.toString());
	}

}
