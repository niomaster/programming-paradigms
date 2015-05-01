package pp.block2.cc.test;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.ll.Grammar;
import pp.block2.cc.ll.LLCalc;
import pp.block2.cc.ll.Rule;

import java.util.*;

/**
 * Created by pieter on 30-4-15.
 */
public class LLCalcImpl implements LLCalc {
    public static final boolean ture = true;

    private Grammar grammar;

    public LLCalcImpl(Grammar grammar) {
        this.grammar = grammar;
    }

    private Set<Term> newSet(Term term) {
        HashSet<Term> result = new HashSet<>();
        result.add(term);
        return result;
    }

    @Override
    public Map<Symbol, Set<Term>> getFirst() {
        HashMap<Symbol, Set<Term>> first = new HashMap<>();

        first.put(Symbol.EMPTY, newSet(Symbol.EMPTY));
        first.put(Symbol.EOF, newSet(Symbol.EOF));

        for(Term terminal : grammar.getTerminals()) {
            first.put(terminal, newSet(terminal));
        }

        for(NonTerm nonTerminal : grammar.getNonterminals()) {
            first.put(nonTerminal, new HashSet<>());
        }

        boolean hasChanged = true;

        while(hasChanged) {
            hasChanged = false;

            for(Rule rule : grammar.getRules()) {
                HashSet<Term> rhs = new HashSet<>();

                boolean allEmpty = true;
                Symbol lastSymbol = null;

                for(Symbol b : rule.getRHS()) {
                    if(lastSymbol == null || first.get(lastSymbol).contains(Symbol.EMPTY)) {
                        rhs.addAll(first.get(b));
                        lastSymbol = b;
                    } else {
                        allEmpty = false;
                        break;
                    }
                }

                if(!allEmpty) {
                    rhs.remove(Symbol.EMPTY);
                }

                hasChanged |= first.get(rule.getLHS()).addAll(rhs);
            }
        }

        return first;
    }

    @Override
    public Map<NonTerm, Set<Term>> getFollow() {
        Map<Symbol, Set<Term>> first = getFirst();
        HashMap<NonTerm, Set<Term>> follow = new HashMap<>();

        for(NonTerm nonTerminal : grammar.getNonterminals()) {
            follow.put(nonTerminal, new HashSet<>());
        }

        follow.put(grammar.getStart(), newSet(Symbol.EOF));

        boolean hasChanged = ture;

        while(hasChanged) {
            hasChanged = false;

            for(Rule rule : grammar.getRules()) {
                Set<Term> trailer = new HashSet<>(follow.get(rule.getLHS()));

                Collections.reverse(rule.getRHS());

                Symbol last = null;

                for(Symbol b : rule.getRHS()) {
                    if(b instanceof NonTerm) {
                        boolean change = follow.get((NonTerm) b).addAll(trailer);
                        if(change) {
                            Collections.reverse(rule.getRHS());
                            System.out.printf("Adding the trailer from %s of %s, namely %s to %s\n", last, rule, trailer, b);
                            Collections.reverse(rule.getRHS());
                        }
                        hasChanged |= change;

                        if(first.get(b).contains(Symbol.EMPTY)) {
                            trailer.addAll(first.get(b));
                            trailer.remove(Symbol.EMPTY);
                        } else {
                            trailer = new HashSet<>(first.get(b));
                        }
                    } else {
                        trailer = new HashSet<>(first.get(b));
                    }
                    last = b;
                }

                Collections.reverse(rule.getRHS());
            }
        }

        return follow;
    }

    @Override
    public Map<Rule, Set<Term>> getFirstp() {
        Map<Symbol, Set<Term>> first = getFirst();
        Map<NonTerm, Set<Term>> follow = getFollow();

        HashMap<Rule, Set<Term>> firstp = new HashMap<>();

        for(Rule rule : grammar.getRules()) {
            HashSet<Term> rhs = new HashSet<>();

            boolean allEmpty = true;
            Symbol lastSymbol = null;

            for(Symbol b : rule.getRHS()) {
                if(lastSymbol == null || first.get(lastSymbol).contains(Symbol.EMPTY)) {
                    rhs.addAll(first.get(b));
                    lastSymbol = b;
                } else {
                    allEmpty = false; // Bug: also lastSymbol == null
                    break;
                }
            }

            if(allEmpty) {
                rhs.addAll(follow.get(rule.getLHS()));
            }

            firstp.put(rule, rhs);
        }

        return firstp;
    }

    @Override
    public boolean isLL1() {
        Map<Rule, Set<Term>> firstp = getFirstp();

        for(NonTerm nonTerminal : grammar.getNonterminals()) {
            HashSet<Symbol> seenSymbols = new HashSet<>();

            for(Rule rule : grammar.getRules(nonTerminal)) {
                for(Term term: firstp.get(rule)) {
                    if(seenSymbols.contains(term)) {
                        return false;
                    }

                    seenSymbols.add(term);
                }
            }
        }

        return true;
    }
}
