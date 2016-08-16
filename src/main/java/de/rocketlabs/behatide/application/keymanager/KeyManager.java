package de.rocketlabs.behatide.application.keymanager;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KeyManager
{
    private Map<KeyCode, List<KeyEventListener>> keyMap = new HashMap<>();

    public void addMapping(KeyCode keyCode, KeyEventListener mapListener)
    {
        if (!keyMap.containsKey(keyCode)) {
            keyMap.put(keyCode, new LinkedList<>());
        }
        keyMap.get(keyCode).add(mapListener);
    }

    public void fireKeyEvent(KeyEvent e)
    {
        List<KeyEventListener> listeners = keyMap.get(e.getCode());
        if (listeners != null) {
            listeners.forEach(listener -> listener.handleEvent(e));
        }
    }
}
