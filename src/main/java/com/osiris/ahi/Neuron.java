package com.osiris.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class Neuron {
    private Worker worker; // The brain this neuron is in.
    private int maxSignalForwardCount;
    private int indexInArray;

    private List<Synapse> synapses = new ArrayList<>();

    /**
     * Create a new {@link Neuron} with
     * a possible amount of 10 connections.
     * @param worker
     * @param indexInArray The position/index of this {@link Neuron}
     *                    in the {@link Brain#getNeurons()} array.
     */
    public Neuron(Worker worker, int maxSignalForwardCount, int indexInArray) {
        this.worker = worker;
        this.maxSignalForwardCount = maxSignalForwardCount;
        this.indexInArray = indexInArray;
    }

    public int getIndexInArray() {
        return indexInArray;
    }

    public void setIndexInArray(int indexInArray) {
        this.indexInArray = indexInArray;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Synapse> getSynapses() {
        return synapses;
    }

    public void setSynapses(List<Synapse> synapses) {
        this.synapses = synapses;
    }

    /**
     * See {@link #setMaxSignalForwardCount(int)} for details.
     */
    public int getMaxSignalForwardCount() {
        return maxSignalForwardCount;
    }

    /**
     * {@link Signal}s which have a strength
     * below this threshold will not trigger new {@link Signal}s.
     */
    public void setMaxSignalForwardCount(int maxSignalForwardCount) {
        this.maxSignalForwardCount = maxSignalForwardCount;
    }

    /**
     * Connects this {@link Neuron} to the neighboring {@link Neuron} by creating a new connection ({@link Synapse})
     * between them and adding it to this {@link Neuron}s {@link Synapse}s list. <br>
     *  - Note that only a total of 10 connections are allowed! <br>
     *  - This {@link Synapse} gets added to the front of the list. <br>
     *  - If the neighboring {@link Neuron} already is connected, try the next neighbor and so on...
     * @param neuron the {@link Neuron} to connect to.
     * @param strength a value between 0 and 1. The closer to 1, the stronger is the connection.
     */
    public synchronized Synapse addAndGetSynapse()  {
        if (synapses.size() >= 10) return null; // This neuron has already 10 connections to other neurons!

        boolean add;
        for (int i = indexInArray+1; i < worker.getNeurons().length; i++) {
            add = true;
            // Check if this Neuron is already connected
            for (Synapse s:
                 synapses) {
                if (s.getNeuron2().equals(this)){
                    add = false;
                    break;
                }
            }

            if (add){
                Synapse s = new Synapse(this, worker.getNeurons()[i]);
                synapses.add(s);
                worker.incrementAndGetTotalCountSynapses();
                return s;
            }
        }
        return null;
    }

    /**
     * Removes the provided {@link Neuron} from this Neurons connections.
     */
    public synchronized void removeSynapse(Neuron neuron){
        Synapse synapseToRemove = null;
        for (Synapse s :
                synapses) {
            if (s.getNeuron2().equals(neuron)){
                synapseToRemove = s;
                break;
            }
        }
        if (synapseToRemove!=null)
            synapses.remove(synapseToRemove);
    }

    /**
     * The strongest connection({@link Synapse}) is
     * always the first one in the  list.
     */
    public synchronized Synapse getStrongestSynapse(){
        try{
            return synapses.get(0);
        } catch (Exception e) {
            return addAndGetSynapse();
        }
    }

    /**
     * Fires the {@link Signal} over the provided {@link Synapse}.
     * @param signal the {@link Signal} to fire.
     * @param synapse the {@link Synapse} over which to fire the {@link Signal}.
     */
    public synchronized void fireSignal(Signal signal, Synapse synapse){
        synapse.fireSignal(signal);
    }

    /**
     * Receives the provided {@link Signal} and forwards it.
     * If the {@link Signal}s strength is below the {@link #getMaxSignalForwardCount()} it wont forward it.
     * This marks the end of a {@link Signal}s travel and thus we fire an according event.
     * Else it fires the {@link Signal} through the strongest {@link Synapse}.
     */
    public synchronized void receiveSignalAndForward(Signal signal){
        if (signal.getSynapsesPathList().size() >= maxSignalForwardCount){
            worker.executeActionsForEventSignalDeath(signal.createAndGetDeathEvent());
            return;
        }

        // Theres a 10% chance of using another Synapse instead of the strongest one
        Synapse forwardingSynapse = null;
        SplittableRandom random = new SplittableRandom();
        if (random.nextInt(0,10) == 9){
            int index = random.nextInt(0,9);
            try{
                forwardingSynapse = synapses.get(index);
            } catch (Exception e) {
                // If this connection doesn't exist yet create it
                forwardingSynapse = addAndGetSynapse();
            }
        }
        else
            try{
                forwardingSynapse = getStrongestSynapse();
            } catch (Exception e) {
                // If this connection doesn't exist yet create it
                forwardingSynapse = addAndGetSynapse();
            }


        fireSignal(signal, forwardingSynapse);
    }

}
