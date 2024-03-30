package algo.programmers;

import java.util.*;

class Solution_호텔_대실 {
    static class Hotel2 implements Comparable<Hotel2>{
        int ex,ey;
        public Hotel2(int ex,int ey) {
            this.ex = ex;
            this.ey = ey;
        }
        public int compareTo(Hotel2 o) {
            if(this.ex == o.ex)
                return this.ey - o.ey;
            return this.ex - o.ex;
        }
    }
    static class Hotel implements Comparable<Hotel>{
        int sx,sy,ex,ey;
        public Hotel(int sx,int sy,int ex,int ey){
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }
        public int compareTo(Hotel o) {
            if(this.sx == o.sx) {
                if(this.sy == o.sy) {
                    if(this.ex == o.ex) {
                        return this.ey - o.ey;
                    }
                    return this.ex - o.ey;
                }
                return this.sy - o.sy;
            }
            return this.sx - o.sx;
        }
    }
    public int solution(String[][] book_time) {
        int answer = 0;
        
        PriorityQueue<Hotel> pq = new PriorityQueue<>();
        for(int i=0;i<book_time.length;i++) {
            
            StringTokenizer st = new StringTokenizer(book_time[i][0],":");
            int sx = Integer.parseInt(st.nextToken());
            int sy = Integer.parseInt(st.nextToken());
            //System.out.println(st.nextToken()+":"+st.nextToken());
            st = new StringTokenizer(book_time[i][1],":");
            int ex = Integer.parseInt(st.nextToken());
            int ey = Integer.parseInt(st.nextToken());
            ey += 10;
            if(ey >= 60) {
                ey -= 60;
                ex += 1;
            }
            pq.add(new Hotel(sx,sy,ex,ey));
        } // input end
        PriorityQueue<Hotel2> pq2 = new PriorityQueue<>();
        // PriorityQueue<Hotel2> pq2 = new PriorityQueue<>(Comparator.comparingInt(o -> o.ex));
        Hotel hotel = pq.poll();
        pq2.add(new Hotel2(hotel.ex,hotel.ey));
        
        while(!pq.isEmpty()) {
            Hotel h1 = pq.poll(); // 현재 배치하려는 예약
            Hotel2 h2 = pq2.peek(); // 앞선 예약 건
            
            if(h2.ex < h1.sx) {
                //System.out.println("0기존 방에 추가");
                pq2.poll();
            }
            else if(h2.ex == h1.sx) {
                if(h2.ey <= h1.sy) {
                    //System.out.println("1기존 방에 추가");
                    pq2.poll();
                }
            }

            pq2.add(new Hotel2(h1.ex,h1.ey));
            //System.out.println("2새로운 방에 추가");
        }
        
        answer = pq2.size();
        return answer;
    }
}