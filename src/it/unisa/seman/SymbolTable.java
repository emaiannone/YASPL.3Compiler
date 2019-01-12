package it.unisa.seman;

import java.util.Iterator;
import java.util.Stack;

public class SymbolTable {
    private Stack<ScopeTable> stack;

    public SymbolTable() {
        stack = new Stack<>();
    }

    public ScopeTable push(ScopeTable st) {
        return stack.push(st);
    }

    public ScopeTable pop() {
        return stack.pop();
    }

    public ScopeTable getCurrentScope() {
        return stack.peek();
    }

    /**
     * True if and only if the key is in the top scope. Used by declarations.
     *
     * @param key
     * @return
     */
    public boolean probe(String key) {
        ScopeTable s = stack.peek();
        return s.getTable().get(key) != null;
    }

    /**
     * True if and only if the key is in one of the active scope. Used by uses.
     *
     * @param key
     * @return
     */
    public SemanticData lookup(String key) {
        SemanticData data = null;
        Iterator<ScopeTable> iterator = stack.iterator();
        while (iterator.hasNext() && data == null) {
            ScopeTable s = iterator.next();
            data = s.getTable().get(key);
        }
        return data;
    }

    public boolean empty() {
        return stack.empty();
    }

    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "stack=" + stack +
                '}';
    }
}
