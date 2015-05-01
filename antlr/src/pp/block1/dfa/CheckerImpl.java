package pp.block1.dfa;

public class CheckerImpl implements Checker {
    @Override
    public boolean accepts(State start, String word) {
        if(word.equals("")) {
            return start.isAccepting();
        } else if(start.hasNext(word.charAt(0))) {
            return accepts(start.getNext(word.charAt(0)), word.substring(1));
        } else {
            return false;
        }
    }
}
