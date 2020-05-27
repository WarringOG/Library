package com.warring.library.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

public class EventExe<T> implements EventExecutor, Listener {

    private EventStart start;

    public EventExe(EventStart start) {
        this.start = start;
    }

    public void dispatch(Plugin pl) {
        Bukkit.getPluginManager().registerEvent(start.getClazz(), this, start.getPriority(), this, pl);
    }

    @Override
    public void execute(Listener listener, Event event) throws ClassCastException, EventException {
        T type = (T)start.getClazz().cast(event);
        start.getConsumer().accept(type);
    }
}
