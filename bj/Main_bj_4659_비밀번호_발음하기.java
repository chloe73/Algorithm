package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_4659_비밀번호_발음하기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			String s = br.readLine();
			
			if(s.equals("end")) break;
			
			sb.append("<"+s+"> is ");
		
			if(check(s)) {
				sb.append("acceptable.");
			}
			else {
				sb.append("not acceptable.");
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static boolean check(String input) {
		boolean flag = true;
		
		
		int aeiouCnt = 0;
		boolean mo = false;
		int moCnt = 0;
		boolean ja = false;
		int jaCnt = 0;
		boolean twice = false;
		outer:for(int i=0;i<input.length();i++) {
			char temp = input.charAt(i);
			
			if(i>0) {
				String s = input.charAt(i-1)+""+temp;
				
				if(input.charAt(i-1) == temp) {
					// 같은 글자가 연속적으로 두번 오면 안되나, ee 와 oo는 허용한다.
					if(s.equals("ee") || s.equals("oo"))
						continue;
					
					twice = true;
					break outer;
				}
			}
			
			if(temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u') {
				aeiouCnt++;
				if(!mo) {
					mo = true;
					moCnt++;
					if(ja) {
						ja = false;
						jaCnt = 0;
					}
				}
				else {
					moCnt++;
					if(moCnt >= 3) {
						break outer;
					}
				}
			}
			else {
				if(!ja) {
					ja = true;
					jaCnt++;
					if(mo) {
						mo = false;
						moCnt = 0;
					}
				}
				else {
					jaCnt++;
					if(jaCnt >= 3) {
						break outer;
					}
				}
			}
		}
		
		// 모음(a,e,i,o,u) 하나를 반드시 포함하여야 한다.
		if(aeiouCnt  == 0) return false;
		
		// 모음이 3개 혹은 자음이 3개 연속으로 오면 안 된다.
		if(mo && moCnt >= 3) return false;		
		if(ja && jaCnt >= 3) return false;
		
		if(twice) return false;
		
		return flag;
	}
}
