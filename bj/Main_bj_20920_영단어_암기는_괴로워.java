package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_20920_영단어_암기는_괴로워 {
	
	static class Word implements Comparable<Word> {
		String word;
		int cnt;
		
		public Word(String word, int cnt) {
			this.word = word;
			this.cnt = cnt;
		}
		
		public int compareTo(Word o) {
			if(this.cnt == o.cnt) {
				if(o.word.length() == this.word.length()) {
					// 사전 순으로 앞서는 단어 return
					return this.word.compareTo(o.word);
				}
				// 해당 단어의 길이가 길수록 앞에 배치한다.
				return o.word.length() - this.word.length();
			}
			// 자주 나오는 단어일수록 앞에 배치한다.
			return o.cnt - this.cnt;
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		HashMap<String, Integer> wordMap = new HashMap<>();
		
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			
			// 길이가 M이상인 단어들만 외운다고 한다.
			if(s.length() < M) continue;
			
			if(wordMap.containsKey(s)) {
				wordMap.put(s, wordMap.get(s)+1);
			}
			else {
				wordMap.put(s, 1);
			}
		} // input end
		
		PriorityQueue<Word> pq = new PriorityQueue<>();
		for(String s :wordMap.keySet()) {
			pq.add(new Word(s, wordMap.get(s)));
		}
		
		while(!pq.isEmpty()) {
			sb.append(pq.poll().word+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
