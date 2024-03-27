package org.max.seminar_2;

public class ConsumerViber implements IConsumer{

    private int count = 0;

    @Override
    public void getMessage(String message) {
        System.out.println("ConsumerViber get message: " + message);
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }

}
