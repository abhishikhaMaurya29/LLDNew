import java.util.Stack;

public class MinStack {
    Stack<Integer> minStack;
    Stack<Integer> mainStack;

    public MinStack() {
        minStack = new Stack<>();
        mainStack = new Stack<>();
    }

    public void pop() {
        if (!mainStack.isEmpty()) {
            minStack.pop();
            mainStack.pop();
            return;
        }

        throw new UnsupportedOperationException("There are no elements to pop");
    }

    public void push(int element) {
        mainStack.push(element);

        if (minStack.isEmpty()) {
            minStack.push(element);
            return;
        }
        minStack.push(Math.min(element, minStack.peek()));
    }

    public Integer min() {
        if (mainStack.isEmpty()) {
            throw new UnsupportedOperationException("There are no elements present to get the min");
        }

        return minStack.peek();
    }
}
