package com.wolfgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

import com.wolfgames.framework.Input;

import android.view.KeyEvent;
import android.view.View;

public class KeyboardHandler implements View.OnKeyListener {
    boolean[] pressedKeys = new boolean[128]; /*stores the current state (pressed or not) of each key, indexed by the key's key code*/
    Pool<Input.KeyEvent> keyEventPool; /*holds the instances of our KeyEvent class*/
    List<Input.KeyEvent> keyEventsBuffer = new ArrayList<Input.KeyEvent>(); /*the KeyEvent instances that have not been consumed yet*/
    List<Input.KeyEvent> keyEvents = new ArrayList<Input.KeyEvent>(); /*the KeyEvent instance that we return by calling getKeyEvents()*/

    public KeyboardHandler(View view) {
        Pool.PoolObjectFactory<Input.KeyEvent> factory = new Pool.PoolObjectFactory<Input.KeyEvent>() {
            public Input.KeyEvent createObject() {
                return new Input.KeyEvent();
            }
        };

        keyEventPool = new Pool<Input.KeyEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     * This is called each time the View receives a new key event
     */
    @Override
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
        // ignore any Android key events that encode a multiple action event
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized(this) {
            // fetch a KeyEvent from the pool, which could be recycled from the pool or brand new (doesn't really matter though)
            Input.KeyEvent keyEvent = keyEventPool.newObject();

            // record the keycode and char from the key event
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();

            // if the key is pressed down, set the key in the pressed keys array to "true"
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                keyEvent.type = Input.KeyEvent.KEY_DOWN;
                if ((keyCode > 0) && (keyCode < 127)) {
                    pressedKeys[keyCode] = true;
                }
            }

            // if the key is released, set the key in the pressed keys array to "false"
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = Input.KeyEvent.KEY_UP;
                if ((keyCode > 0) && (keyCode < 127)) {
                    pressedKeys[keyCode] = false;
                }
            }

            keyEventsBuffer.add(keyEvent);
        }

        return false;
    }

    /**
     * Indicates whether or not a key is pressed down
     * @param keyCode
     * @return true if the specified key is pressed down, false otherwise
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }

        return pressedKeys[keyCode];
    }

    public List<Input.KeyEvent> getKeyEvents() {
        synchronized(this) {
            int len = keyEvents.size();
            for (int i = 0; i < len; i++) {
                keyEventPool.free(keyEvents.get(i));
            }

            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();

            return keyEvents;
        }
    }
}

