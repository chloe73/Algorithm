package algo.SW_DX;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_마니또 {
	
	static class Node implements Comparable<Node> {
        int to;
        int cost;

        public Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
    	System.setIn(new FileInputStream("input/input_마니또.txt"));
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
        	st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            ArrayList<Node>[] graph = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
            	st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                graph[start].add(new Node(end, cost));
            }

            int minCost = Integer.MAX_VALUE;

            for (int start = 1; start <= N; start++) {
                int[] dist = new int[N + 1];
                Arrays.fill(dist, Integer.MAX_VALUE);
//                dist[start] = 0;

                PriorityQueue<Node> pq = new PriorityQueue<>();
                pq.offer(new Node(start, 0));

                while (!pq.isEmpty()) {
                    Node current = pq.poll();
                    int from = current.to;
                    int cost = current.cost;

                    if (cost > dist[from]) {
                        continue;
                    }

                    for (Node next : graph[from]) {
                        int to = next.to;
                        int newCost = cost + next.cost;

                        if (newCost < dist[to]) {
                            dist[to] = newCost;
                            pq.offer(new Node(to, newCost));
                        }
                    }
                }
                
                minCost = Math.min(minCost, dist[start]);
            }

            if (minCost == Integer.MAX_VALUE) {
                System.out.println("#" + tc + " -1");
            } else {
                System.out.println("#" + tc + " " + minCost);
            }
        }
        
        br.close();
    }
}