package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main_bj_1181_단어정렬 {
	
	static int N;
	static class Word implements Comparable<Word>{
		int len, index;
		String s;
		public Word(int len, int index, String s) {
			this.len = len;
			this.index = index;
			this.s = s;
		}
		
		public int compareTo(Word o) {
			if(this.len == o.len) return this.index - o.index;
			return this.len - o.len;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		ArrayList<String> wordList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			String temp = br.readLine();
			
			// 문자열 중복 제거
			if(wordList.contains(temp)) continue;
			wordList.add(temp);
		} // input end
		
		// 문자열 정렬을 위한 sort함수 사용
		Collections.sort(wordList);
		System.out.println(wordList);
		
		PriorityQueue<Word> pq = new PriorityQueue<>();
		for(int i=0;i<wordList.size();i++) {
			pq.add(new Word(wordList.get(i).length(), i, wordList.get(i)));
		}
		
		while(!pq.isEmpty()) {
			Word tmp = pq.poll();
			String result = tmp.s;
			sb.append(result+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
