package algo.programmers;

import java.util.*;
import java.io.*;

public class Solution_택배상자 {

    public int solution(int[] order) {
        int answer = 0;
        
        ArrayList<Integer> truck = new ArrayList<>();
        Queue<Integer> belt = new LinkedList<>();
        
        // order의 맨 처음 숫자를 기준으로 
        int temp = order[0];
        truck.add(temp);
        
        // order의 첫번째 번호보다 큰 수는 컨테이너 벨트에 추가
        for(int i=order.length;i>temp;i--) {
            belt.add(i);
        }
        
        // 보조 컨테이너 벨트에 order의 첫번째 번호보다 작은 수 추가
        Queue<Integer> q = new LinkedList<>();
        if(temp>1) {
            for(int i=temp-1;i>0;i--) {
                q.add(i);
            }
        }
        
        int idx = 1; // order 인덱스 번호
        while(idx < order.length) {
            
            int next = order[idx];
            
            if(next == q.peek()) {
                truck.add(q.poll());
            }
            
            else if(next == belt.peek()) {
                truck.add(belt.poll());
            }
            
            else break;
            
            idx++;
        }
        
        answer = truck.size();
        return answer;
    }

}
