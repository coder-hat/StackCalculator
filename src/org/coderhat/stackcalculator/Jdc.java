package org.coderhat.stackcalculator;

import java.io.Console;

public class Jdc
{
    public static final String CANONICAL_EXIT_COMMAND = "q";
    
    public static void main(String[] args) {
        Console consl = System.console();
        
        JdcEngine jdce = new JdcEngine();
        
        String line = null;
        while (true) {
            line = consl.readLine();
            if (line != null && !line.isEmpty()) {
                if (line.equals(CANONICAL_EXIT_COMMAND)) {
                    break;
                } else {
                    jdce.evaluate(line);
                }
            }
        }
    }
}
