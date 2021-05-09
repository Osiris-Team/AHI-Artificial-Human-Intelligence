/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ahi;

import com.osiris.ahi.events.EventSignalDeath;
import com.osiris.ahi.events.Eventable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A worker is something more like a manager that is
 * responsible for a certain section of the {@link Brain}.
 */
public class Worker {
    /**
     * Runs all actions in this list when
     * a {@link EventSignalDeath} is thrown. <br>
     * <p>
     * {@link Neuron}
     */
    public List<Eventable<EventSignalDeath>> actionsOnSignalDeathEvent = new ArrayList<>();
    private Brain brain;
    private Neuron[] neurons;
    private int startIndex;
    private int endIndex;
    private int totalCountSynapses;
    private List<Runnable> runnablesList = new CopyOnWriteArrayList<>();

    /**
     * Creates a new {@link Worker} for a certain brain region.
     * Define that region by setting the start-/end-index.
     *
     * @param brain      the brain this worker works on.
     * @param startIndex the start neuron index.
     * @param endIndex   the end neuron index.
     */
    public Worker(Brain brain, int startIndex, int endIndex) {
        super();
        this.brain = brain;
        this.neurons = brain.getNeurons();
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public synchronized void executeActionsForEventSignalDeath(EventSignalDeath event) {
        actionsOnSignalDeathEvent.forEach(action -> {
            try {
                action.runOnEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addRunnable(Runnable runnable) {
        runnablesList.add(runnable);
    }

    public List<Thread> executeAndRemoveAllRunnablesAndReturnTheirThreads() {
        List<Thread> threadsList = new ArrayList<>();
        for (Runnable r :
                runnablesList) {
            Thread thread = new Thread(r);
            thread.start();
            runnablesList.remove(r);
            threadsList.add(thread);
        }
        return threadsList;
    }

    public Thread executeRunnableByIndexAndReturnItsThread(int index) {
        Thread thread = new Thread(runnablesList.get(index));
        thread.start();
        return thread;
    }

    public Thread executeRunnableAndReturnItsThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }


    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List<Runnable> getRunnablesList() {
        return runnablesList;
    }

    public void setRunnablesList(List<Runnable> runnablesList) {
        this.runnablesList = runnablesList;
    }

    public int incrementAndGetTotalCountSynapses() {
        return totalCountSynapses++;
    }

    public int getTotalCountSynapses() {
        return totalCountSynapses;
    }

    public void setTotalCountSynapses(int totalCountSynapses) {
        this.totalCountSynapses = totalCountSynapses;
    }
}
