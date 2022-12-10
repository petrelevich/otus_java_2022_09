package ru.otus.nio;

import java.nio.CharBuffer;

public class BufferExample {
    public static void main(String[] args) {
        new BufferExample().go();
    }

    private void go() {
        var buffer = CharBuffer.allocate(10);
        System.out.printf("capacity: %d limit: %d position: %d%n", buffer.capacity(), buffer.limit(), buffer.position());

        var text = "testText".toCharArray();
        for (var idx = 0; idx < text.length; idx += 2) {
            buffer.put(text, idx, 2);
            System.out.printf("idx: %d capacity: %d limit: %d position: %d %n", idx, buffer.capacity(), buffer.limit(), buffer.position());
        }

        buffer.flip();

        System.out.println("-----");
        for (var idx = 0; idx < buffer.limit(); idx++) {
            System.out.printf("idx: %d char: %s capacity: %d limit:%d position: %d %n", idx, buffer.get(), buffer.capacity(), buffer.limit(), buffer.position());
        }
    }
}
