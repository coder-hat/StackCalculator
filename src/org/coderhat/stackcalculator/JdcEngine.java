package org.coderhat.stackcalculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO 2019-6-26 Match behavior? dc -e "64 0.5 ^ p" -> 1, with "Runtime warning: non-zero scale in exponent" emitted

public class JdcEngine
{    
    public static final Map<String, JdcCommand> commands;
    static {
        commands = new HashMap<>();
        commands.put("+", ds -> BiEval(ds, (BigDecimal x1, BigDecimal x2) -> x2.add(x1)));
        commands.put("-", ds -> BiEval(ds, (BigDecimal x1, BigDecimal x2) -> x2.subtract(x1)));
        commands.put("*", ds -> BiEval(ds, (BigDecimal x1, BigDecimal x2) -> x2.multiply(x1)));
        commands.put("/", ds -> BiEval(ds, (BigDecimal x1, BigDecimal x2) -> x2.divide(x1)));
        commands.put("^", ds -> BiEval(ds, (BigDecimal x1, BigDecimal x2) -> x2.pow(x1.intValue())));
        commands.put("c", ds -> ds.clear());
        commands.put("d", JdcEngine::dupTop2);
        commands.put("f", ds -> ds.forEach(System.out::println));
        commands.put("n", ds -> {if (!ds.isEmpty()) System.out.println(pop(ds));});
        commands.put("p", ds -> {if (!ds.isEmpty()) System.out.println(peek(ds));});
        commands.put("r", JdcEngine::swapTop2);
    };

    
    private Deque<String> ds;
    
    
    public JdcEngine() {
        ds = new ArrayDeque<String>();
    }
    
    
    public void evaluate(String s) {
        String[] tokens = s.split("\\s+");
        for (String t : tokens) {
            if (commands.containsKey(t)) {
                commands.get(t).apply(ds);
            } else {
                push(ds, t);
            }
        }
    }
    
    /**
     * Pops the top two values, x1 and x2 off of "stack" ds, and applies BiFunction f.apply(x1, x2). Results is pushed
     * to ds.
     * 
     * @param ds
     *            The {@link Deque} to use as a stack.
     * @param f
     *            The {@link BiFunction} to apply.
     */
    public static void BiEval(Deque<String> ds, BiFunction<BigDecimal, BigDecimal, BigDecimal> f) {
        BigDecimal x1 = pop(ds);
        BigDecimal x2 = pop(ds);
        BigDecimal y = f.apply(x1, x2);
        push(ds, y);
    }
    
    public static void dupTop2(Deque<String> ds) {
        BigDecimal x1 = pop(ds);
        push(ds, x1);
        push(ds, x1);
    }
    
    public static void swapTop2(Deque<String> ds) {
        BigDecimal x1 = pop(ds);
        BigDecimal x2 = pop(ds);
        push(ds, x1);
        push(ds, x2);
    }
    
    //----- Stack mimicry
    
    public static BigDecimal peek(Deque<String> ds) {
        return new BigDecimal(ds.peekFirst());
    }
    
    public static BigDecimal pop(Deque<String> ds) {
        return new BigDecimal(ds.removeFirst());
    }
    
    public static void push(Deque<String> ds, BigDecimal bd) {
        push(ds, bd.toString());
    }
    
    public static void push(Deque<String> ds, String s) {
        ds.addFirst(s);
    }
 
}

// NOTE 2019-6-25
// Use a deque<>, not a stack<> because of this language in Java's stack documentation:
// > A more complete and consistent set of LIFO stack operations is provided
// > by the Deque interface and its implementations, which should be used
// > in preference to this class. For example:
// > Deque<Integer> stack = new ArrayDeque<Integer>();"

// NOTE 2019-6-25
// Additional thoughts on using a Java Deque as a Stack
// "Java Stack vs Deque"
// http://baddotrobot.com/blog/2013/01/10/stack-vs-deque/

// NOTE 2019-6-25
// It took a while to find a reference for how to iterate a deque in reverse order.
// Here's a concise example:
// "Iterate over Deque in Java (Forward and Backward directions)"
// https://www.techiedelight.com/iterate-over-deque-java-forward-backward/

// NOTE 2019-6-26
// BigDecimal Square Roots prior to Java 9
// "Square root of BigDecimal in Java"
// https://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java

