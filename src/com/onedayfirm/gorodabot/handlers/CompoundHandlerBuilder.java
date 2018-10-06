package com.onedayfirm.gorodabot.handlers;

public class CompoundHandlerBuilder {

    private Handler first;
    private Handler last;

    public CompoundHandlerBuilder add(Handler handler) {
        if (first == null) {
            first = handler;
            last = handler;
        } else {
            last = last.setNext(handler);
        }
        return this;
    }

    public Handler build() {
        return first;
    }
}
