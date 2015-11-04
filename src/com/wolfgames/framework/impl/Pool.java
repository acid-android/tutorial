package com.wolfgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    /**
     * This method will return an object from the pool or create a new one (if the pool is empty)
     * @return
     */
    public T newObject() {
        T object = null;

        if (freeObjects.isEmpty()) {
            object = factory.createObject();
        }
        else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }

        return object;
    }

    /**
     * Reinserts an object into the pool
     * @param object
     */
    public void free(T object) {
        if (freeObjects.size() < maxSize) {
            freeObjects.add(object);
        }
    }
}
