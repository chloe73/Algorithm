package algo.softeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Softeer_5th_성적평가 {
	
    static int N;
    static int[] arr, result, total;
    static class Point implements Comparable<Point> {
        int idx, score;
        public Point(int idx, int score) {
            this.idx = idx;
            this.score = score;
        }
        @Override
        public int compareTo(Point o) {
            return o.score - this.score;
        }
    }
    static PriorityQueue<Point> pq;
    static HashMap<Integer, Integer> hmap = new HashMap<>();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
        arr = new int[N];
        result = new int[N];
        total = new int[N];
        pq = new PriorityQueue<Point>();

        for(int i=0;i<3;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                arr[j] = Integer.parseInt(st.nextToken());
                total[j] += arr[j];
                pq.add(new Point(j,arr[j]));
            }
            Point p = pq.poll();
            int num = p.score;
            int index = p.idx;
            int order = 1;
            int prev = 1;
            result[index] = order;
            while(!pq.isEmpty()) {
                Point temp = pq.poll();
                if(num == temp.score) {
                    result[temp.idx] = order;
                }
                else if(num > temp.score) {
                    order = prev+1;
                    result[temp.idx] = order;
                }
                num = temp.score;
                prev++;
            }
            for(int k : result) {
                System.out.print(k+" ");
            }
            System.out.println();
        }
        
        pq = new PriorityQueue<Point>();
        for(int i=0;i<N;i++) {
          pq.add(new Point(i, total[i]));  
        }
        // 맨 처음 하나 poll
        Point p = pq.poll();
        int num = p.score;
        int index = p.idx;
        int order = 1;
        int prev = 1;
        result[index] = order;
        while(!pq.isEmpty()) {
            Point temp = pq.poll();
            if(num == temp.score) {
                result[temp.idx] = order;
            }
            else if(num > temp.score) {
                order = prev+1;
                result[temp.idx] = order;
            }
            prev++;
        }
        for(int k : result) {
            System.out.print(k+" ");
        }
	}

}
