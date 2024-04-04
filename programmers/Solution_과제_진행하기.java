package algo.programmers;

import java.util.*;
import java.io.*;

class Solution_과제_진행하기 {
    static class Task {
        String name, start;
        int playtime;
        int si,bun;
        public Task(String name, String start, int playtime) {
            this.name = name;
            this.start = start;
            this.playtime = playtime;
            String[] arr = start.split(":");
            this.si = Integer.parseInt(arr[0]);
            this.bun = Integer.parseInt(arr[1]);
        }
    }
    static PriorityQueue<Task> pq;
    
    public String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        ArrayList<String> result = new ArrayList<>();
        
        pq = new PriorityQueue<>((o1,o2) -> {
            if(o1.si == o2.si)
                return o1.bun - o2.bun;
            return o1.si - o2.si;
        });
        for(int i=0;i<plans.length;i++){
            pq.add(new Task(plans[i][0], plans[i][1], Integer.parseInt(plans[i][2])));
        }
        
        Stack<Task> waitingStack = new Stack<>();
        while(!pq.isEmpty()) {
            Task temp = pq.poll();
            int s = temp.si;
            int b = temp.bun + temp.playtime;
            if(b >= 60) {
                s += (b / 60);
                b = b % 60;
            }
            
            if(pq.isEmpty()) {
                result.add(temp.name);
                break;
            }
            
            // 현재 과제를 바로 끝내도 되는 경우
            if(pq.size() > 0 && s < pq.peek().si || (pq.size() > 0 && s == pq.peek().si && b <= pq.peek().bun)){
                result.add(temp.name);
                if(waitingStack.size() > 0) {
                    int diff = (pq.peek().si*60 + pq.peek().bun) - (s*60 + b);
                    
                    while(diff > 0 && waitingStack.size() > 0) {
                        if(waitingStack.peek().playtime <= diff) {
                            result.add(waitingStack.peek().name);
                            diff -= waitingStack.peek().playtime;
                            waitingStack.pop();
                            continue;
                        }
                        waitingStack.peek().playtime -= diff;
                        diff = 0;
                    }
                    
                    // if(waitingStack.peek().playtime - diff <= 0) {
                    //     result.add(waitingStack.peek().name);
                    //     waitingStack.pop();
                    // }
                    // else {
                    //     waitingStack.peek().playtime -= diff;
                    // }
                }
                continue;
            }
            // 현재 과제 시작 시간에서 다음 과제 시작 시간까지 차이 -> 해당 시간만큼만 과제를 진행할 수 있다.
            
            int diff = (pq.peek().si*60 + pq.peek().bun) - (temp.si*60 + temp.bun);

            temp.playtime -= diff;
            temp.bun += diff;
            if(temp.bun >= 60) {
                temp.si += (temp.bun / 60);
                temp.bun = temp.bun % 60;
            }

            waitingStack.add(temp);
        }
        
        while(!waitingStack.isEmpty()) {
            Task temp = waitingStack.pop();
            result.add(temp.name);
        }
        
        int idx = 0;
        for(String s : result){
            answer[idx++] = s;
        }
        return answer;
    }
}