package com.osiris.ahi.organs.brain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

public class Neuron {
    private int indexInArray;
    private List<Synapse> synapses = new ArrayList<>();

    /**
     * Create a new {@link Neuron} with
     * a possible amount of 10 connections.
     *
     * @param worker
     * @param indexInArray The position/index of this {@link Neuron}
     *                     in the {@link Brain#getNeurons()} array.
     */
    public Neuron(int indexInArray) {
        this.indexInArray = indexInArray;
    }

    public int getIndexInArray() {
        return indexInArray;
    }

    public void setIndexInArray(int indexInArray) {
        this.indexInArray = indexInArray;
    }

    public List<Synapse> getSynapses() {
        return synapses;
    }

    /**
     * Removes the provided {@link Neuron} from this Neurons connections.
     */
    public synchronized void removeSynapse(Neuron neuron) {
        synchronized (synapses){
            Synapse synapseToRemove = null;
            for (Synapse s :
                    synapses) {
                if (s.getReceiverNeuron().equals(neuron)) {
                    synapseToRemove = s;
                    break;
                }
            }
            if (synapseToRemove != null)
                synapses.remove(synapseToRemove);
        }
    }

    /**
     *
     * @return null if no Synapses exist for this Neuron.
     */
    public Synapse getStrongestSynapse() {
        synchronized (synapses){
            int highestStrength = 0;
            Synapse synapse = null;
            for (Synapse s :
                    synapses) {
                if (highestStrength < s.getStrength()){
                    highestStrength = s.getStrength();
                    synapse = s;
                }
            }
            return synapse;
        }
    }

    /**
     *
     * @return null if no Synapses exist for this Neuron.
     */
    public Synapse getRandomSynapse(){
        synchronized (synapses){
            if (synapses.size() != 0)
                if ((synapses.size()-1) == 0)
                    return synapses.get(0);
                else
                    return synapses.get(new Random().nextInt(synapses.size()-1));
            else
                return null;
        }
    }

    /**
     * Returns a random Synapse with a 10% chance, otherwise the strongest.
     */
    public Synapse getStrongestOrRandomSynapse(){
        SplittableRandom random = new SplittableRandom();
        if (random.nextInt(0, 10) == 9)
            return getRandomSynapse();
        else
            return getStrongestSynapse();
    }


    /**
     * Fires the {@link Signal} over the provided {@link Synapse}.
     *
     * @param signal  the {@link Signal} to fire.
     * @param synapse the {@link Synapse} over which to fire the {@link Signal}.
     */
    public synchronized void fireSignal(Signal signal, Synapse synapse) {
        synapse.fireSignal(signal);
    }

    /**
     * Receives the provided {@link Signal} and forwards it.
     * If the {@link Signal}s strength is below the {@link #getMaxSignalForwardCount()} it wont forward it.
     * This marks the end of a {@link Signal}s travel and thus we fire an according event.
     * Else it fires the {@link Signal} through the strongest {@link Synapse}.
     */
    public synchronized void receiveSignalAndForward(Signal signal) {
        if (signal.getStrength() < 1) {
            try{
                signal.die();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Theres a 10% chance of using another Synapse instead of the strongest one
        Synapse forwardingSynapse = getStrongestOrRandomSynapse();
        if (forwardingSynapse==null)
            try{
                signal.die();
            } catch (Exception e) {
                e.printStackTrace();
            }
        else
            fireSignal(signal, forwardingSynapse);
    }

}
