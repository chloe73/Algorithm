package algo;

public class Main_k {

	public static void main(String[] args) {
		
		f(210,0,20);
	}
	
	public static void f(int offset, int left, int right) {
		
		String number = offset + "";
		String answer = "";
		
		while(answer.length() < right) {
			
			System.out.println("number : "+number);
			
			int idx = 0;
			
			while(idx < number.length()) {
				int temp = number.charAt(idx) - '0';
				
				if(temp == 0) {
					answer += "zero";
					
				}
				else if(temp == 1) {
					if(idx+1 < number.length()) {
						int next = number.charAt(idx+1) - '0';
						String num = temp + "" + next;
						
						if(num.equals("10")) answer += "ten";
						else if(num == "11") answer += "eleven";
						else if(num == "12") answer += "twelve";
						else if(num == "13") answer += "thirteen";
						else if(num == "14") answer += "fourteen";
						else if(num == "15") answer += "fifteen";
						else if(num == "16") answer += "sixteen";
						else if(num == "17") answer += "seventeen";
						else if(num == "18") answer += "eighteen";
						else if(num == "19") answer += "nineteen";
						
						idx += 2;
						continue;
					}
					else answer += "one";
				}
				else {
					if(temp == 2) answer += "two";
					else if(temp == 3) answer += "three";
					else if(temp == 4) answer += "four";
					else if(temp == 5) answer += "five";
					else if(temp == 6) answer += "six";
					else if(temp == 7) answer += "seven";
					else if(temp == 8) answer += "eight";
					else if(temp == 9) answer += "nine";
				}
				idx++;
			}
			
			offset++;
			number = offset +"";
		}
		
		System.out.println(answer);
	}

}
