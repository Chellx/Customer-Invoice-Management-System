package data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test{
private SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/m/dd");
	
	public Test() {
		
	}
	public String allAlpha(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
			if(character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character == ' ') {
				count++;
			}
		}//end of for loop
		if(count == word.length()) {
			return word;
		}
		return "?";
	}//end od allAlpha method
	
	public String alphaAndNums(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
				if(character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == ' ') {
					count++;
				}
		}
			if(count == word.length()) {
				return word;
			}
		return "?";
	}//end of alpha and nums method
	public String justNumbers(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
				if(character >= '0' && character <= '9') {
					count++;
				}
		}
		if(count == word.length()) {
			return word;
		}
		return "?";
	}//end of justNumbers
	public String atSymbolAndDot(String word) {
		int count = 0;
		for(int i = 0; i < word.length();i++) {
			char character = word.charAt(i);
			if(character == '@') {
				if(count < 2) {
					count++;
				}
			}
		}
		if(count == 1) {
			return word;
		}
		return "?";
	}
public String checkDate(String stringDate)	{
	try {
		Date date = simpleDate.parse(stringDate);
		String newDate = simpleDate.format(date);
		return newDate;
	}
	catch(Exception e){
		
	}
	return "?";
}//end of checkDate method
}