package algo.programmers;

import java.util.*;

public class Solution_두원사이의정수쌍 {

	public long solution(int r1, int r2) {
        long answer = 0;
        
        long a1 = (long) Math.pow(r1,2);
        long a2 = (long) Math.pow(r2,2);
        
        // y^2 = r^2 - x^2
        int cnt = 0;
        for(long x=0;x<=r2;x++) {
            long y2 = (long) Math.sqrt(a2 - x*x);
            long y1 = (long) Math.sqrt(a1 - x*x);
            
            if(Math.sqrt(a1 - x*x) % 1 == 0) cnt++;
            
            answer += (y2-y1) * 4;
        }
        
        answer += cnt * 4;
        answer -= 4;
        
        return answer;
    }

}
