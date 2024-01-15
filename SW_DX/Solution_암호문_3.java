package algo.SW_DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_암호문_3 {
	
	static int N,M,result;
	static ArrayList<Integer> list;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		for(int tc=1;tc<=10;tc++) {
			sb.append("#"+tc+" ");
			
			N = Integer.parseInt(br.readLine());
			list = new ArrayList<>();
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				int num = Integer.parseInt(st.nextToken());
				
				list.add(num);
			}
			
			M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<M;i++) {
				String cmd = st.nextToken();
				
				if(cmd.equals("I")) {
					// I(삽입) x, y, s : 앞에서부터 x번째 암호문 바로 다음에 y개의 암호문을 삽입한다.
					// s는 덧붙일 암호문들이다.[ ex) I 3 2 123152 487651 ]
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					
					int idx = x;
					for(int j=0;j<y;j++) {
						int s = Integer.parseInt(st.nextToken());
						list.add(idx++, s);
					}
					
					
				}
				else if(cmd.equals("D")) {
					// D(삭제) x, y : 앞에서부터 x번째 암호문 바로 다음부터 y개의 암호문을 삭제한다.[ ex) D 4 4 ]
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					
					for(int k=x+1;k<=x+y;k++) {
						list.remove(x);
					}
				}
				else {
					// A(추가) y, s : 암호문 뭉치 맨 뒤에 y개의 암호문을 덧붙인다. 
					// s는 덧붙일 암호문들이다. [ ex) A 2 421257 796813 ]
					int y = Integer.parseInt(st.nextToken());
					for(int j=0;j<y;j++) {
						int num = Integer.parseInt(st.nextToken());
						
						list.add(num);
					}
				}
			}
			
			for(int i=0;i<10;i++) {
				sb.append(list.get(i)+" ");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

}
