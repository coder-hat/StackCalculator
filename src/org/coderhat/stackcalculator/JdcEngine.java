package org.coderhat.stackcalculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


// Use a deque<>, not a stack<> because of this language in Java's stack documentation:
// "A more complete and consistent set of LIFO stack operations is provided
// by the Deque interface and its implementations, which should be used
// in preference to this class. For example:
//    Deque<Integer> stack = new ArrayDeque<Integer>();"

public class JdcEngine
{    
    public static final Map<String, JdcCommand> commands;
    static {
        commands = new HashMap<>();
        commands.put("+", ds -> ds.push(ds.pop() + ds.pop()));
        commands.put("-", ds -> ds.push(ds.pop() - ds.pop()));
        commands.put("*", ds -> ds.push(ds.pop() * ds.pop()));
        commands.put("/", ds -> ds.push(ds.pop() / ds.pop()));
        commands.put("c", ds -> ds.clear());
        commands.put("f", ds -> ds.forEach(System.out::println));
        commands.put("n", ds -> {if (!ds.isEmpty()) System.out.println(ds.pop());});
        commands.put("p", ds -> {if (!ds.isEmpty()) System.out.println(ds.peekFirst());});
    };

    
    private Deque<Double> ds;
    
    
    public JdcEngine() {
        ds = new ArrayDeque<Double>();
    }
    
    
    public void evaluate(String s) {
        String[] tokens = s.split("\\s+");
        for (String t : tokens) {
            if (commands.containsKey(t)) {
                commands.get(t).apply(ds);
            } else {
                Double d = new Double(t);
                ds.push(d);
            }
        }
    }
    
    
    
}
