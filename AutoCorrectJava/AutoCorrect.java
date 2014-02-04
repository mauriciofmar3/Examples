import java.io.*;
import java.util.*;
class AutoCorrect {
  public static void main(String args[]) throws IOException {
    Scanner in = new Scanner(System.in);
    PrefixNode head = new PrefixNode();
    head.loadWords("english.0"); 
    System.out.print(">");
    while(in.hasNext()) {
      String s = in.next();
      if(s.equals(":quit"))
        break;
      System.out.println(head.spellCheck(s));
      System.out.print(">");
    }
  }
}

class PrefixNode {
  HashMap<Character, PrefixNode> map;
  char value;
  boolean isAWord;
  public static String VOWELS = "AEIOUaeiou";

  public PrefixNode() {
    map = new HashMap<Character, PrefixNode>();
    this.value = '0';
  }
  
  private PrefixNode(char value) {
    map = new HashMap<Character, PrefixNode>();
    this.value = value;
  }

  public String spellCheck(String str) {
    return this.spellCheck(str, -1, "");
  }

  private String spellCheck(String str, int index, String current) {
    ++index;
    if(index == str.length())
      return this.isAWord ? current : "NO SUGGESTION";
    for(char letter : PrefixNode.getOptions(str.charAt(index))) {  
      if(map.containsKey(letter)) {
        PrefixNode node = map.get(letter);
        String ret = node.spellCheck(str, index, current+letter);
        if(!ret.equals("NO SUGGESTION"))
          return ret;
      }
    }
    String ret = "NO SUGGESTION";
    if(index > 0 && PrefixNode.equalOptions(str.charAt(index), str.charAt(index-1)))
      ret = this.spellCheck(str, index, current);
    return ret;
  }
  
  private static boolean equalOptions(char c1, char c2) {
    char[] a1 = getOptions(c1); Arrays.sort(a1);
    char[] a2 = getOptions(c2); Arrays.sort(a2);
    int i1 = 0; 
    int i2 = 0; 
    while(i1 < a1.length && i2 < a2.length) {
      if(a1[i1] == a2[i2])
        return true;
      if(a1[i1] > a2[i2])
        ++i2;
      else if(a1[i1] < a2[i2])
        ++i1;
    }
    return false;
  }
  
  private static char[] getOptions(char input) {
    String ret = "" + input;
    int index = VOWELS.indexOf(input + ""); 
    if(index >= 0)
      ret += VOWELS.substring(0,index) + VOWELS.substring(index+1);
    else
      ret += input > 90 ? (char)(input - 32) : (char)(input + 32);
    return ret.toCharArray(); 
  }
  
  public void loadWords(String filename) throws IOException {
    Scanner in = new Scanner(new File(filename));
    while(in.hasNext()) 
      this.add(in.next(), 0);
  }

  private void add(String str, int index) {
    if(index == str.length()) {
      this.isAWord = true;
      return;
    }
    PrefixNode node = new PrefixNode(str.charAt(index));
    if(map.containsKey(str.charAt(index)))
      node = map.get(str.charAt(index));
    else
      map.put(str.charAt(index), node);
    node.add(str,index+1);
  }
}
