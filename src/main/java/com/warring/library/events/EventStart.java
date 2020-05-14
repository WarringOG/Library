package com.warring.library.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class EventStart<T> implements Listener {

    private Class<T> clazz;
    private Consumer<T> consumer;
    private JavaPlugin pl;
    private EventPriority priority;
    private EventExe eventExe;

    private EventStart(Class<T> clazz, Consumer<T> consumer, JavaPlugin plugin, EventPriority priority) {
        this.clazz = clazz;
        this.consumer = consumer;
        this.pl = plugin;
        this.priority = priority;
        eventExe = new EventExe(this);
    }

    public static <T extends Event> void register(Class<T> clazz, Consumer<T> consumer, JavaPlugin plugin, EventPriority priority) {
       EventStart start = new EventStart(clazz, consumer, plugin, priority);
       start.getEventExe().dispatch(plugin);
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
