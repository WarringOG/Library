package com.warring.library.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.function.Predicate;

public class EventExe<T extends Event> implements EventExecutor, Listener {

    private EventStart<T> start;

    public EventExe(EventStart start) {
        this.start = start;
    }

    public void dispatch(Plugin pl) {
        Bukkit.getPluginManager().registerEvent(start.getClazz(), this, start.getPriority(), this, pl);
    }

    @Override
    public void execute(Listener listener, Event event) {
        if (!event.getClass().equals(start.getClazz())) return;

        for (Predicate<T> filter : start.getFilters()) {
            if (!filter.test((T) event)) {
                return;
            }
        }

        T type = (T)start.getClazz().cast(event);
        start.getConsumer().accept(type);
    }
}
