package nis.util;

public class Util {	
	public static String parseLinks(String inputText) {
		StringBuilder str = new StringBuilder();
		String[] array = inputText.split(" ");
		
		for(String word : array){
			if(word.startsWith("http://"))
				str.append(" <a href=\"" + word + "\" target=\"_blank\"" +  ">" + word + "</a>");
			else
				str.append(" " + word);
		}
		
		return str.toString();
	}
	
	public static String removePunctuation(String input){
		return input.replaceAll("[ \t\r\n.:,#'\":]", "");
	}
	
}
