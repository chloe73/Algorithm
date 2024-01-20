package algo.SW_DX.day4_Heap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_힙 {
	
	static int N;
	static Heap heap;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			N = Integer.parseInt(br.readLine());
			heap = new Heap();
			for(int i=0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				int cmd = Integer.parseInt(st.nextToken());
				
				if(cmd == 1) {
					// 연산 1 수행
					int number = Integer.parseInt(st.nextToken());
					heap.add(number);
				}
				else {
					// 연산 2 수행
					if(heap.size == 0) {
						bw.write(-1+" ");
					}
					else bw.write(heap.poll()+" ");
				}
			}
			bw.write("\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}

class Heap {
	int[] arr;
	int size = 0;
	
	Heap(){
		arr = new int[100001];
	}
	
	void add(int data) {
		arr[++size] = data;
		int now = size;
		
		while(now > 1) {
			int parent = now / 2;
			if(arr[now] > arr[parent]) {
				// swap 진행
				int temp = arr[parent];
				arr[parent] = arr[now];
				arr[now] = temp;
				now = parent;
			}
			else break;
		}
	}
	
	int poll() {
		int max = arr[1];
		
		arr[1] = arr[size];
		arr[size] = 0;
		size--;
		heapify();
		
		return max;
	}
	
	void heapify() {
		int now = 1;
		
		while(getRight(now) <= size) {
			int larger = now;
			int left = getLeft(now);
			int right = getRight(now);
			
			if(arr[left] > arr[larger]) {
				larger = left;
			}
			
			if(arr[right] > arr[larger]) {
				larger = right;
			}
			
			if(larger != now) {
				int temp = arr[now];
				arr[now] = arr[larger];
				arr[larger] = temp;
				now = larger;
			}
			else break;
		}
	}
	
	int getLeft(int parent) {
		return parent * 2;
	}
	
	int getRight(int parent) {
		return parent * 2 + 1;
	}
}