package algo.SW_DX.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

// 17152. [Pro] 검색엔진2
public class Solution {
	private static final int MAX_LENGTH				= 8;
	private static final int CMD_INIT				= 100;
	private static final int CMD_SEARCH				= 200;
	private static final int CMD_RECOMMEND			= 300;
	private static final int CMD_RELATE				= 400;
	private static final int CMD_RANK				= 500;

	public static final class Result {
		int mOrder;
		int mRank;

		Result() {
			mOrder = 0;
			mRank = 0;
		}
	}

	private static void String2Char(String s, char[] b, int maxlen) {
		int n = s.length();
		for (int i = 0; i < n; ++i)
			b[i] = s.charAt(i);
		for (int i = n; i < maxlen; ++i)
			b[i] = '\0';
	}
	
	private static int strcmp(char a[], char b[], int n) {
		int i = 0;
		while(i < n - 1 && a[i] != 0 && a[i] == b[i]) i++;
		return a[i] - b[i];
	}

	private static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) throws Exception {
		int Q;
		
		Q = sc.nextInt();
		
		boolean okay = false;
		
		for (int q = 0; q < Q; ++q) {
			int cmd = sc.nextInt();
			int ret, ans, ans2, mCount, mRank;
			char mStr[] = new char[MAX_LENGTH], mStr2[] = new char[MAX_LENGTH], mReturnStr[] = new char[MAX_LENGTH];
			Result res;
			
			switch(cmd) {
			case CMD_INIT:
				usersolution.init();
				okay = true;
				break;
			case CMD_SEARCH:
				String2Char(sc.next(), mStr, MAX_LENGTH);
				mCount = sc.nextInt();
				usersolution.search(mStr, mCount);
				break;
			case CMD_RECOMMEND:
				String2Char(sc.next(), mStr, MAX_LENGTH);
				res = usersolution.recommend(mStr);
				ans = sc.nextInt();
				ans2 = sc.nextInt();
				if (res.mOrder != ans || res.mRank != ans2)
					okay = false;
				break;
			case CMD_RELATE:
				String2Char(sc.next(), mStr, MAX_LENGTH);
				String2Char(sc.next(), mStr2, MAX_LENGTH);
				ret = usersolution.relate(mStr, mStr2);
				ans = sc.nextInt();
				if (ans != ret)
					okay = false;
				break;
			case CMD_RANK:
				String2Char(sc.next(), mStr, MAX_LENGTH);
				mRank = sc.nextInt();
				usersolution.rank(mStr, mRank, mReturnStr);
				String2Char(sc.next(), mStr2, MAX_LENGTH);
				if (strcmp(mStr2, mReturnStr, MAX_LENGTH) != 0)
					okay = false;
				break;		
			default:
				okay = false;
				break;
			}
			
		}
 
		return okay;
	}
	
	public static void main(String[] args) throws Exception {
		// System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);
	
		int TC = sc.nextInt();
		int MARK = sc.nextInt();
		
		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		
		sc.close();
	}
}

class UserSolution {
	
	static TreeSet<Word> wordSet; // 정렬된 모든 검색어 집합 데이터
	static HashMap<String, Word> wordMap; // 모든 검색어 집합 데이터
	static HashMap<String, HashSet<Word>> relatedWord; // 연관검색어 데이터
	static class Word implements Comparable<Word>{
		String w;
		int cnt;
		
		public Word(String w, int cnt) {
			this.w = w;
			this.cnt = cnt;
		}
		
		public int compareTo(Word o) {
			if(this.cnt == o.cnt) return this.w.compareTo(o.w);
			return o.cnt-this.cnt;
		}
	}
	
	void init() {
		// 각 테스트 케이스에서 처음 1회 호출된다.
		// 초기에는 아무 검색 기록도 없다.
		wordSet = new TreeSet<>();
		wordMap = new HashMap<>();
		relatedWord = new HashMap<>();
		
		return;
	}

	void search(char mStr[], int mCount) {
		// 연관검색어 관계를 고려해 검색어 mStr의 조회수를 mCount 만큼 증가시켜 반영한다.
		// 동일한 mStr로 여러 번 조회수가 누적될 수 있다.
		// 조회수를 테스트 케이스 동안 누적해도 1,000,000을 초과하지 않는 것이 보장된다.
		String input = "";
		
		for(char c : mStr) {
			if(c == '\0') break;
			input += c+"";
		}
		
		if(!wordMap.containsKey(input)) {
			Word word = new Word(input, mCount);
			wordMap.put(input, word);
			wordSet.add(word);
		}
		else {
			wordMap.get(input).cnt+= mCount;
		}
		
		if(relatedWord.containsKey(input)) {
			for(Word word : relatedWord.get(input)) {
				word.cnt += mCount;
			}
		}
		
		return;
	}

	Solution.Result recommend(char mStr[]) {
		// 사용자가 mStr을 추천 검색어 기능을 사용하여 검색하고자 한다.
		// 본문의 설명과 같은 규칙으로 추천 검색어에 처음으로 mStr이 등장하는 위치를 반환하라.
		// [Fig. 1]과 같은 경우 ‘ab’를 입력하면 2번째로 추천되므로, Result 구조체의 mRank와 mOrder가 각각 2가 된다.
		// mStr은 기존 검색 기록에 존재하며, 검색어를 마지막까지 입력해도 추천 검색어에 등장하지 않는 경우는 주어지지 않는다.
		Solution.Result res = new Solution.Result();

		String input = "";
		
		for(char c : mStr) {
			if(c == '\0') break;
			input += c+"";
		}
		
		int order = 0;
		int rank = 0;
		
		String keyword = "";
		outer:for(int i=0;i<=input.length();i++) {
			order = i;
			if(i == 0) {
				for(Word w : wordSet) {
					rank++;
					
					if(rank > 5) break;
					
					if(w.w.equals(input)) {
						break outer;
					}
				}				
			}
			else {
				keyword += input.charAt(i-1);
				rank = 0;
				
				for(Word w : wordSet) {
					if(w.w.startsWith(keyword)) {
						rank++;
						if(rank > 5) break;
						
						if(w.w.equals(input)) {
							break outer;
						}
					}
				}
			}
			
		}
		
		res.mOrder = order;
		res.mRank = rank;
		
		// 이 함수로 검색한 후 해당 검색어의 조회수가 ‘1’ 증가한다.
		wordMap.get(input).cnt++;
		return res;
	}

	int relate(char mStr1[], char mStr2[]) {
		// 검색어 mStr1과 검색어 mStr2를 연관 검색어로 설정한다.
		// 두 검색어는 기존 검색 기록에 존재하며, 서로 동일하거나 기존에 연관 검색어 관계인 경우는 주어지지 않는다.
		// 연관 검색어로 설정한 검색어 모두의 조회수를 합하여도 테스트 케이스 동안 1,000,000을 초과하지 않는 것이 보장된다.
		// 연관 검색어들이 동시에 추천 검색어로 등장하더라도 추천검색어에 해당 검색어들을 모두 표시한다.
		
		String input1 = "";
		
		for(char c : mStr1) {
			if(c == '\0') break;
			input1 += c+"";
		}
		
		String input2 = "";
		
		for(char c : mStr2) {
			if(c == '\0') break;
			input2 += c+"";
		}
		
		// 검색어 A와 검색어 B를 연관 검색어로 설정할 경우 
		// 검색어 A의 조회수와 검색어 B의 조회수가 서로 합해진다.
		
		if(!relatedWord.containsKey(input1)) {
			relatedWord.put(input1, new HashSet<>());
		}
		relatedWord.get(input1).add(wordMap.get(input2));
		
		if(!relatedWord.containsKey(input2)) {
			relatedWord.put(input2, new HashSet<>());
		}
		relatedWord.get(input2).add(wordMap.get(input1));
		
		relatedWord.get(input1).addAll(relatedWord.get(input2));
		relatedWord.get(input2).addAll(relatedWord.get(input1));
		
		int count = wordMap.get(input1).cnt + wordMap.get(input2).cnt;
		int ret = 0;
		
		for(Word w : relatedWord.get(input1)) {
			ret += w.cnt;
			w.cnt = count;
		}
		
		// 연관 검색어 관계로 묶인 모든 검색어들의 조회수의 합을 반환한다.
		return ret;
	}

	void rank(char mPrefix[], int mRank, char mReturnStr[]) {
		// mPrefix로 시작하는 검색어 중 mRank 번째 많이 조회된 검색어를 찾아 mReturnStr에 반환한다.
		// 같은 조회수이면 사전 순서로 앞서는 검색어가 우선한다.
		// 해당 순위의 검색어가 없는 경우는 주어지지 않는다.
		// mPrefix는 알파벳 소문자 1~3자와 마지막 ‘＼0’으로 구성된 문자열이다.
		// 검색 기록에서 찾을 때는 ‘＼0’을 제외하고 찾아야한다.
		// mReturnStr으로 반환된 검색어 문자열은 ‘＼0’으로 끝나야 한다.
		String input = "";
		
		for(char c : mPrefix) {
			if(c == '\0') break;
			input += c+"";
		}
		
		
		
		return;
	}
}