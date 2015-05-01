package cp1.ex2;

public class UnsafeQueue<T> implements Queue<T> {
    private final Message<T> left = new Message<T>(null);
    private final Message<T> right = new Message<T>(null);

    private class Message<T> {
        Message<T> prev;
        Message<T> next;

        private final T data;

        public T getData() {
            return data;
        }

        public Message(T data) {
            this.data = data;
        }
    }

    public UnsafeQueue() {
        left.next = right;
        right.prev = left;
    }

    public void push(T e) {
        Message<T> newMessage = new Message<T>(e);
        right.prev.next = newMessage;
        newMessage.next = right;
        right.prev = newMessage;

        notifyAll();
    }

    public T pull() {
        while(left.next == right) {
            try {
                wait();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        T result = left.next.getData();
        left.next.next.prev = left;
        left.next = left.next.next;

        return result;
    }
}
