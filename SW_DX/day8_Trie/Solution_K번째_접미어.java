package algo.SW_DX.day8_Trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Solution_K번째_접미어 {
	
	static int K;
	static char[] result;
	static class TrieNode {
		char alphabet;
		boolean isLastChar;
		int subtreeCnt;
		Map<Character, TrieNode> children = new HashMap<>();

		TrieNode(char alphabet){
			this.alphabet = alphabet;
			this.subtreeCnt = 0;
		}
		
		TrieNode(){
			
		}
	}
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			K = Integer.parseInt(br.readLine());
			String word = br.readLine();
			int len = word.length();
			TrieNode root = new TrieNode();
			
			if(K > len) {
				bw.write("none"+"\n");
				continue;
			}
			
			for(int i=0;i<len;i++) {
				TrieNode indexTrie = root;
				
				for(int j=i;j<len;j++) {
					char alphabet = word.charAt(j);
					
					if(!indexTrie.children.containsKey(alphabet)) {
						// 새로운 문자 추가
						TrieNode newTrie = new TrieNode(alphabet);
						indexTrie.children.put(alphabet, newTrie);
					}
					
					indexTrie = indexTrie.children.get(alphabet);
					indexTrie.subtreeCnt++;
				}
				
				indexTrie.isLastChar = true;
			}
			
			result = new char[len];
			
			dfs(root, 0);
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void dfs(TrieNode trie, int depth) throws IOException {
		if(K == 0) return;
		
		if(trie.isLastChar) {
			K--;
			
			if(K == 0) {
				String str = "";
				for(int i=0;i<depth;i++) {
					str += result[i];
				}
				bw.write(str+"\n");
				return;
			}
		}
		
		for(char i='a';i<='z';i++) {
			if(trie.children.containsKey(i)) {
				TrieNode child = trie.children.get(i);
				if(child.subtreeCnt < K) {
					K -= child.subtreeCnt;
					continue;
				}
				
				result[depth] = i;
				dfs(child, depth+1);
				result[depth] = '_';
			}
		}
	}

}
