package algo.SW_DX.day8_Trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Trie_practice {

	static class TrieNode {
		private Map<Character, TrieNode> childNodes = new HashMap<>();
		private boolean isLastChar;
		
		public Map<Character, TrieNode> getChildNodes() {
			return childNodes;
		}
		public void setChildNodes(Map<Character, TrieNode> childNodes) {
			this.childNodes = childNodes;
		}
		public boolean isLastChar() {
			return isLastChar;
		}
		public void setLastChar(boolean isLastChar) {
			this.isLastChar = isLastChar;
		}
	}
	static class Trie {
		private TrieNode rootNode;
		Trie(){
			rootNode = new TrieNode();
		}
		
		void insert(String word) {
			TrieNode thisNode = this.rootNode;
			for(int i=0;i<word.length();i++) {
				thisNode = thisNode.getChildNodes().computeIfAbsent(word.charAt(i), c -> new TrieNode());
			}
			thisNode.setLastChar(true);
		}
		
		boolean contains(String word) {
			TrieNode thisNode = this.rootNode;
			for(int i=0;i<word.length();i++) {
				char character = word.charAt(i);
				TrieNode node = thisNode.getChildNodes().get(character);
				if(node == null) {
					return false;
				}
				thisNode = node;
			}
			return thisNode.isLastChar();
		}
		
		void delete(String word) {
			delete(this.rootNode, word, 0);
		}
		
		private void delete(TrieNode thisNode, String word, int index) {
			char character = word.charAt(index);
			
			if(!thisNode.getChildNodes().containsKey(character)) {
				throw new Error("there is no[" + word + "] in this Trie.");
			}
			
			TrieNode childNode = thisNode.getChildNodes().get(character);
			index++;
			
			if(index == word.length()) {
				if(!childNode.isLastChar) {
					throw new Error("there is no[" + word + "] in this Trie.");
				}
				childNode.setLastChar(false);
				
				if(childNode.getChildNodes().isEmpty()) {
					thisNode.getChildNodes().remove(character);
				}
			}
			else {
				delete(childNode, word, index);
				if(!childNode.isLastChar && childNode.getChildNodes().isEmpty()) {
					thisNode.getChildNodes().remove(character);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			int K = Integer.parseInt(br.readLine());
			String word = br.readLine();
			
			
		}

	}

}
