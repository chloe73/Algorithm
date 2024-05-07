package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Main_bj_1339 {
	
	static int N;
	static ArrayList<String> list;
	static HashSet<Character> set;
	static HashMap<Character, Alpha> map;
	static class Alpha implements Comparable<Alpha>{
		@Override
		public String toString() {
			return "Alpha [order=" + order + ", word=" + word + ", cnt=" + cnt + "]";
		}
		int order;
		char word;
		int cnt;
		public Alpha(char word, int order, int cnt) {
			this.word = word;
			this.order = order;
			this.cnt = cnt;
		}
		public int compareTo(Alpha o) {
			if(this.order == o.order)
				return o.cnt - this.cnt; // 등장 횟수가 더 많은 문자가 우선순위가 더 높음.
			return o.order-this.order; // 자리수가 큰 문자가 우선순위가 더 높음.
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		set = new HashSet<>();
		map = new HashMap<>();
		
		for(int i=0;i<N;i++) {
			String temp = br.readLine();
			
			for(int k=0;k<temp.length();k++) {
				char c = temp.charAt(k);
				int order = (int) Math.pow(10, (temp.length()-k-1));
				
				if(!set.contains(c)) {
					set.add(c);
					if(order == 0) order = 1;
					map.put(c, new Alpha(c, order, 1));
//					System.out.println(c+" : "+order);
				}
				else {
					Alpha a = map.get(c);
					int count = a.cnt;
					if(order > a.order) {
						map.put(c, new Alpha(c, order, count+1));
					}
					else {
						map.put(c, new Alpha(c, a.order, count+1));
					}
//					System.out.println(c+" : "+order+", "+a.cnt+1);
				}
			}
			list.add(temp);
		} // input end
		
		PriorityQueue<Alpha> pq = new PriorityQueue<>();
		for(char c : map.keySet()) {
			pq.add(map.get(c));
		}
		
		HashMap<Character, Integer> numberMap = new HashMap<>();
		int number = 9;
		while(!pq.isEmpty()) {
			Alpha temp = pq.poll();
			numberMap.put(temp.word, number--);
			System.out.println(temp);
		}
		
		int result = 0;
		for(String s : list) {
			String num = "";
			for(int i=0;i<s.length();i++) {
				num += numberMap.get(s.charAt(i)) + "";
			}
			
//			System.out.println(num);
			result += Integer.parseInt(num);
		}
		
//		for(char c : numberMap.keySet()) {
//			System.out.println(c+", "+numberMap.get(c));
//		}
		
		System.out.println(result);
		// 단어 수학 문제는 N개의 단어로 이루어져 있으며, 각 단어는 알파벳 대문자로만 이루어져 있다. 
		// 이때, 각 알파벳 대문자를 0부터 9까지의 숫자 중 하나로 바꿔서 N개의 수를 합하는 문제이다. 
		// 같은 알파벳은 같은 숫자로 바꿔야 하며, 두 개 이상의 알파벳이 같은 숫자로 바뀌어지면 안 된다.
		// 예를 들어, GCF + ACDEB를 계산한다고 할 때, A = 9, B = 4, C = 8, D = 6, E = 5, F = 3, G = 7로 결정한다면, 두 수의 합은 99437이 되어서 최대가 될 것이다.
		// N개의 단어가 주어졌을 때, 그 수의 합을 최대로 만드는 프로그램을 작성하시오.
	}

}
