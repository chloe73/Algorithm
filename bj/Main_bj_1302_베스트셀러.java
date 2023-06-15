package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main_bj_1302_베스트셀러 {
	
	static int N;
	static class Book implements Comparable<Book>{
		String name;
		int cnt;
		public Book(String name, int cnt) {
			this.name = name;
			this.cnt = cnt;
		}
		
		@Override
		public int compareTo(Book o) {
			return o.cnt - this.cnt; // 가장 많이 팔리 책
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		
		HashMap<String, Integer> map = new HashMap<>();
		N = Integer.parseInt(br.readLine());
		for(int i=0;i<N;i++) {
			String temp = br.readLine();
			if(map.containsKey(temp)) {
				int num = map.get(temp);
				num++;
				map.put(temp, num);
			}
			else {
				map.put(temp, 1);
			}
		} // input end
		
		PriorityQueue<Book> bookList = new PriorityQueue<>();
		for(String s : map.keySet()) {
			bookList.add(new Book(s, map.get(s)));
		}
		
//		for(String s : book) {
//			System.out.println(s + " : " + map.get(s));
//		}
//		System.out.println("==========");
		
		ArrayList<String> book = new ArrayList<>();
		Book temp = bookList.poll();
		int maxNum = temp.cnt;
		book.add(temp.name);
		
		while(!bookList.isEmpty()) {
			Book b = bookList.poll();
			
			if(maxNum == b.cnt) {
				book.add(b.name);
			}
			else if(maxNum > b.cnt) break;
		}
		
		Collections.sort(book); // 책 제목 사전 순 정렬
		
		// 가장 많이 팔린 책 제목 출력 (여러 개인 경우, 사전 순으로 가장 앞서는 제목 출력)
		System.out.println(book.get(0));
	}

}
