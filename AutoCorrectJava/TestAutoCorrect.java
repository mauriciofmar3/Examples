import java.util.*;
import java.io.*;
class TestAutoCorrect {
  static String VOWELS = "AEIOUaeiou";

  public static void main(String args[]) throws IOException {
    Scanner in = new Scanner(new File("english.0"));    
    while(in.hasNext()) {
      String str = in.next();
      StringBuffer sb = new StringBuffer();
      for(int x = 0; x < str.length()*2; ++x) {
        for(char value : getOptions(str.charAt(x/2))) {
          sb.append(value); 
        }
      }
      System.out.println(sb);
    }
  }

  private static char[] getOptions(char input) throws IOException {
    String ret = "" + input;
    int index = VOWELS.indexOf(input + "");
    if(index >= 0)
      ret += VOWELS.substring(0,index) + VOWELS.substring(index+1);
    else
      ret += input > 90 ? (char)(input - 32) : (char)(input + 32);
    return ret.toCharArray();
  }
}
