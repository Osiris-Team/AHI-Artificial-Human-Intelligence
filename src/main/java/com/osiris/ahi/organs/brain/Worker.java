/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ahi.organs.brain;

import com.osiris.ahi.events.EventSignalDeath;
import com.osiris.ahi.events.Eventable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A worker is something more like a manager that is
 * responsible for a certain section of the {@link Brain}.
 */
public class Worker extends Thread{
    /**
     * Runs all actions in this list when
     * a {@link EventSignalDeath} is thrown. <br>
     * <p>
     * {@link Neuron}
     */

    private Brain brain;
    private Neuron[] neurons;
    private int startNeuronIndex;
    private int endNeuronIndex;
    private int totalCountSynapses;
    private List<Runnable> runnablesList = new CopyOnWriteArrayList<>();
    private long msPerLoop = -1;

    /**
     * Creates a new {@link Worker} for a certain brain region.
     * Define that region by setting the start-/end-index.
     *
     * @param brain      the brain this worker works on.
     * @param startNeuronIndex the start neuron index.
     * @param endNeuronIndex   the end neuron index.
     */
    public Worker(Brain brain, int startNeuronIndex, int endNeuronIndex) {
        this.brain = brain;
        this.neurons = brain.getNeurons();
        this.startNeuronIndex = startNeuronIndex;
        this.endNeuronIndex = endNeuronIndex;
    }

    @Override
    public void run() {
        while(true){
            try{
                long start = System.currentTimeMillis();
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    // The first loop should remove dead synapses
                    for (int j = startNeuronIndex; j < endNeuronIndex+1; j++) {
                        Neuron n = neurons[j];
                        List<Synapse> toRemove = new ArrayList<>(2);
                        for (Synapse s :
                                n.getSynapses()) {
                            toRemove.add(s);
                        }
                        for (Synapse s :
                                toRemove) {
                            if (s.getStrength() < 0) {
                                s.getSenderNeuron().getSynapses().remove(s);
                                s.getReceiverNeuron().getSynapses().remove(s);
                            }
                        }
                    }

                    // Then we fire a positive signal at the first and last neurons
                    neurons[startNeuronIndex].getStrongestSynapse();
                    for (int j = startNeuronIndex; j < endNeuronIndex+1; j++) {
                        Neuron n = neurons[j];
                        List<Synapse> toRemove = new ArrayList<>(2);
                        for (Synapse s :
                                n.getSynapses()) {
                            toRemove.add(s);
                        }
                        for (Synapse s :
                                toRemove) {
                            if (s.getStrength() < 0) {
                                s.getSenderNeuron().getSynapses().remove(s);
                                s.getReceiverNeuron().getSynapses().remove(s);
                            }
                        }
                    }
                }
                msPerLoop = System.currentTimeMillis() - start;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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

    public int getStartNeuronIndex() {
        return startNeuronIndex;
    }

    public void setStartNeuronIndex(int startNeuronIndex) {
        this.startNeuronIndex = startNeuronIndex;
    }

    public int getEndNeuronIndex() {
        return endNeuronIndex;
    }

    public void setEndNeuronIndex(int endNeuronIndex) {
        this.endNeuronIndex = endNeuronIndex;
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

    public long getMsPerLoop() {
        return msPerLoop;
    }
}
