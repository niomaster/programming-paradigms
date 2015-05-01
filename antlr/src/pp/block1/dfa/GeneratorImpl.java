package pp.block1.dfa;

import java.util.LinkedList;
import java.util.List;

public class GeneratorImpl implements Generator {
    @Override
    public List<String> scan(State dfa, String text) {
        LinkedList<String> result = new LinkedList<>();

        while(!text.isEmpty()) {
            String maxWord = null;

            String chars = text;
            String curWord = "";
            State s = dfa;

            while(!chars.isEmpty() && s.hasNext(chars.charAt(0))) {
                s = s.getNext(chars.charAt(0));
                curWord += chars.charAt(0);
                chars = chars.substring(1);

                if(s.isAccepting()) {
                    maxWord = curWord;
                }
            }

            if(maxWord == null) {
                return null;
            } else {
                result.add(maxWord);
                text = text.substring(maxWord.length());
            }
        }

        return result;
    }
}
