package com.warring.library.commands.backend;

public interface CommandPost<T> {

    void execute(T p, String[] args);
}
