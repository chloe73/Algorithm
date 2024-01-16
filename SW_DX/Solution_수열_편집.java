package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_수열_편집 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
//	static class Node{
//		int data;
//		Node next;
//		
//		public Node(int data) {
//			this.data = data;
//			this.next = null;
//		}
//	}
//	static class LinkedList{
//		
//	}
	
	public static void main(String[] args) throws IOException{
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.append("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			ArrayList<Integer> list = new ArrayList<>();
			for(int i=0;i<N;i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				
				char cmd = st.nextToken().charAt(0);
				int index,number;
				switch (cmd) {
				case 'I':
					index = Integer.parseInt(st.nextToken());
					number = Integer.parseInt(st.nextToken());
					list.add(index, number);
					break;

				case 'D':
					index = Integer.parseInt(st.nextToken());
					list.remove(index);
					break;
					
				case 'C':
					index = Integer.parseInt(st.nextToken());
					number = Integer.parseInt(st.nextToken());
					list.set(index, number);
					break;
				}
			}
			
			if(L >= list.size())
				bw.append(-1+"\n");
			else bw.append(list.get(L)+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}
