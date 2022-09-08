package gntp.lesson.board.utils;

public class CalculatorNextSeq {
    public static String getNextSeq(String currentSeq, String nextSeq) {

        String result = null;

        if(nextSeq.equals(" ")) {
            result = currentSeq+"a";
        } else {
            int len = nextSeq.length();
            char letter = (char)(nextSeq.charAt(len-1)+1);
            result = nextSeq.substring(0,len-1)+letter;
        }
        return result;
    }
}
