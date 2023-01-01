package algo.programmers;

import java.util.*;
import java.io.*;

public class Solution_택배상자 {

    public int solution(int[] order) {
        int answer = 0;
        
        Stack<Integer> truck = new Stack<>();
        
        int num = 1;
        int idx = 0; // order 인덱스 번호
        while(true) {
            
        	if(!truck.isEmpty() && order[idx] == truck.peek()) {
        		answer++;
        		
        		idx++;
        		truck.pop();
        		
        		continue;
        	}
        	
        	if(num > order.length) break;
        	
        	if(order[idx] == num) {
        		answer++;
        		 
        		idx++;
        		num++;
        		
        		continue;
        	}
            
            truck.push(num);
            num++;
        }

        return answer;
    }

}
