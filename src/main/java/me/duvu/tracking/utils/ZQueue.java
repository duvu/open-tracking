package me.duvu.tracking.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author beou on 11/4/18 07:24
 */
public class ZQueue<E> extends ConcurrentLinkedQueue<E> {

    private static final long serialVersionUID = -7312187186573252517L;

    public List<E> poll(int n) {
        List<E> list = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            list.add(poll());
        }
        return list;
    }
}
