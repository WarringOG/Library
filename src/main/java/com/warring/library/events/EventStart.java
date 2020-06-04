package com.warring.library.events;

import com.warring.library.WarringPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventStart<T extends Event> {

    private Class<T> clazz;
    private Consumer<T> consumer;
    private EventPriority priority;
    private EventExe eventExe;
    private LinkedList<Predicate<T>> filters;

    public EventStart(Class<T> clazz, EventPriority priority) {
        this.clazz = clazz;
        this.priority = priority;
        filters = new LinkedList<>();
        eventExe = new EventExe(this);
    }

    public static <T extends Event> EventStart<T> register(Class<T> clazz, EventPriority priority) {
       return new EventStart(clazz, priority);
    }

    public static <T extends Event> EventStart<T> register(Class<T> clazz) {
        return new EventStart(clazz, EventPriority.NORMAL);
    }

    public EventStart<T> handleEvent(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public EventStart<T> addFilter(Predicate<T>... filter) {
        for (Predicate<T> d : filter) {
            filters.add(d);
        }
        return this;
    }

    public LinkedList<Predicate<T>> getFilters() {
        return filters;
    }

    public void dispatch() {
        eventExe.dispatch(WarringPlugin.getInstance());
    }

    public EventExe getEventExe() {
        return eventExe;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
