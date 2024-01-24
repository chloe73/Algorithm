package algo.SW_DX.day8_Trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Solution_K번째_문자열 {
	
	static int K, index;
	static char[] result;
	static class Trie {
		char alphabet;
		boolean isEndChar;
		int subtreeCnt;
		Map<Character, Trie> children = new HashMap<>();
		
		Trie(char alphabet){
			this.alphabet = alphabet;
		}
		
		Trie() { }
	}
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			Trie root = new Trie();
			K = Integer.parseInt(br.readLine())+1;
			String word = br.readLine();
			
			for(int i=0;i<word.length();i++) {
				insert(word, i, root);
			}
			
			result = new char[word.length()];
			index = 0;
			dfs(root, 0);
			if(K > 0) {
				bw.write("none"+"\n");
			}
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void dfs(Trie trie, int depth) throws IOException {
		if(K == 0) return;
		
		K--;
		if(K == 0) {
			String str = "";
			for(int i=0;i<depth;i++) {
				str += result[i];
			}
			bw.write(str+"\n");
			return;
		}
		
		for(char i='a'; i<='z';i++) {
			if(trie.children.containsKey(i)) {
				Trie child = trie.children.get(i);
				if(child.subtreeCnt < K) {
					K -= child.subtreeCnt;
					continue;
				}
				
				result[depth] = i;
				dfs(trie.children.get(i), depth+1);
				result[depth] = '_';
			}
		}
	}

	private static int insert(String word, int idx, Trie trie) {
		if(idx == word.length()) return 0;
		
		char alphabet = word.charAt(idx);
		
		int subCnt = 0;
		if(!trie.children.containsKey(alphabet)) {
			Trie newTrie = new Trie(alphabet);
			newTrie.subtreeCnt = 1;
			subCnt = 1;
			trie.children.put(alphabet, newTrie);
		}
		
		subCnt += insert(word, idx+1, trie.children.get(alphabet));
		trie.subtreeCnt += subCnt;
		
		return subCnt;
	}
}
