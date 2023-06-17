package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_17825_주사위_윷놀이 {
	
	static int[] arr1 = {0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40};
	static int[] arr2 = {10,13,16,19,25,30,35,40}; // 10 ~
	static int[] arr3 = {20,22,24,25,30,35,40}; // 20 ~
	static int[] arr4 = {30,28,27,26,25,30,35,40}; // 30 ~
	static int[] cmd, order;
	static int result;
	static ArrayList<Horse> horseList;
	static class Horse {
		int arrNum, idx, num;
		public Horse(int arrNum, int idx, int num) {
			this.arrNum = arrNum;
			this.idx = idx;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		cmd = new int[10];
		order = new int[10]; // 말 이동 순서

		for(int i=0;i<10;i++) {
			cmd[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		perm(0); // 중복 순열
		
		System.out.println(result);
	}

	private static void perm(int cnt) {
		
		if(cnt == 10) {
			horseList = new ArrayList<>();
			for(int i=0;i<4;i++) horseList.add(new Horse(1, 0, 0));
			move();
			return;
		}
		
		for(int i=0;i<4;i++) {
			order[cnt] = i;
			perm(cnt+1);
		}
	}

	private static void move() {
		
		int score = 0;
		
		for(int i=0;i<10;i++) {
			int horseNum = order[i]; // 0 ~ 3 사이 말 번호
			int diceNum = cmd[i]; // 주사위 숫자 : 이동할 칸의 개수
			// 현재 이동하려는 말의 번호
			int temp_num = horseList.get(horseNum).num;
			int temp_arrNum = horseList.get(horseNum).arrNum;
			int temp_idx = horseList.get(horseNum).idx;
			
			if(temp_num == 100) return; // 이미 도착한 말은 선택 불가
			
			// 이동 후, 현재 배열의 크기를 넘어가면 도착지에 도착한거니 체크
			if(temp_idx + diceNum >= get_arr_length(temp_arrNum)) {
				horseList.get(horseNum).num = 100; // 도착 표시
				horseList.get(horseNum).arrNum = 1;
				horseList.get(horseNum).idx = 0;
				continue;
			}
			else {
				temp_idx += diceNum;
				temp_num = get_num(temp_arrNum, temp_idx);
				horseList.get(horseNum).num = temp_num;
				horseList.get(horseNum).idx = temp_idx;
				
				if(temp_num == 10) {
					horseList.get(horseNum).arrNum = 2;
					horseList.get(horseNum).idx = 0;
				}
				else if(temp_num == 20) {
					horseList.get(horseNum).arrNum = 3;
					horseList.get(horseNum).idx = 0;
				}
				else if(temp_num == 30 && temp_arrNum == 1) {
					horseList.get(horseNum).arrNum = 4;
					horseList.get(horseNum).idx = 0;
				}
			}
			
			// 이동 후,  해당 칸에 다른 말이 있으면 불가능한 케이스
			if(!check_destination(temp_num, horseNum, temp_arrNum, temp_idx)) return;
			
			score += temp_num;
		}
		
		result = Math.max(result, score);
		
	}
	
	private static int get_num(int arrNum, int idx) {
		if(arrNum == 1) return arr1[idx];
		else if(arrNum == 2) return arr2[idx];
		else if(arrNum == 3) return arr3[idx];
		else return arr4[idx];
	}
	
	private static int get_arr_length(int arrNum) {
		if(arrNum == 1) return arr1.length;
		else if(arrNum == 2) return arr2.length;
		else if(arrNum == 3) return arr3.length;
		else return arr4.length;
	}
	
	private static boolean check_destination(int num, int horseNum, int arrNum, int idx) {
		for(int i=0;i<4;i++) {
			if(i == horseNum) continue; // 본인 말은 확인 안 함
			int k = horseList.get(i).num;
			int a = horseList.get(i).arrNum;
			int index = horseList.get(i).idx;
			// 번호는 똑같아도 위치가 다를 수 있음
			if(num == 16 || num == 22 || num == 24 || num == 26 || num == 28 || num == 30) {
				if(k == num && arrNum == a && idx == index) return false;
			}
			else {				
				if(k == num) return false;
			}
		}
		return true;
	}

}
