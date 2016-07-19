package de.rocketlabs.behatide.application.keymanager;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KeyManager
{
    private Map<KeyCode, List<KeyManagerListener>> keyMap = new HashMap<>();

    public <T extends Event> void addMap(KeyCode keyCode, KeyManagerListener mapListener)
    {
        if (!keyMap.containsKey(keyCode)) {
            keyMap.put(keyCode, new LinkedList<>());
        }
        keyMap.get(keyCode).add(mapListener);
    }

    public <T extends Event> void fireKeyMapEvent(KeyEvent e)
    {
        List<KeyManagerListener> listeners = keyMap.get(e.getCode());
        if (listeners != null) {
            listeners.forEach(listener -> listener.handleEvent(e));
        }
    }
//
//    public static <T extends Event> void fireEvent(T event)  {
//
//        List<EventListener<?>> listeners = map.get(event.getClass());
//        if (listeners != null) {
//            listeners.forEach(listener -> {
//                ((EventListener<T>) listener).handleEvent(event);
//            });
//        }
//    }
}
