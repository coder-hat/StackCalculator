package org.coderhat.stackcalculator;

import java.util.Deque;


@FunctionalInterface
public interface JdcCommand
{
    public void apply(Deque<String> dataStack);
}
