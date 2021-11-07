/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ahi.organs.brain;

import java.util.Objects;

/**
 * A {@link Synapse} represents
 * a connection between two {@link Neuron}s.
 * It has the ability of transmitting
 * a {@link Signal} from Neuron 1 to Neuron 2.
 */
public class Synapse {
    private Neuron senderNeuron;
    private Neuron receiverNeuron;
    private int strength = 10;

    /**
     * A {@link Synapse} represents
     * a connection between two {@link Neuron}s.
     * It has the ability of transmitting
     * a {@link Signal} from Neuron 1 to Neuron 2.
     */
    public Synapse(Neuron senderNeuron, Neuron receiverNeuron) {
        Objects.requireNonNull(senderNeuron);
        Objects.requireNonNull(receiverNeuron);
        //if (strength>1.0 || strength<0)
        //    throw new NullPointerException("Strength must be a value between 0.0 and 1.0");
        this.senderNeuron = senderNeuron;
        this.receiverNeuron = receiverNeuron;
        //this.strength = strength;
    }

    /**
     * Fires the provided signal to
     * the second Neuron.
     *
     * @param signal The {@link Signal} to fire/pass over to the next {@link Neuron}.
     */
    public void fireSignal(Signal signal) {
        signal.getSynapsesPathList().add(this);
        signal.setStrength(signal.getStrength() - 1);
        if (signal.isPositive()){
            strength++; // Enhance this synapses strength
        } else {
            strength--; // Decrease this synapses strength
        }
        receiverNeuron.receiveSignalAndForward(signal);
    }

    public Neuron getSenderNeuron() {
        return senderNeuron;
    }

    public void setSenderNeuron(Neuron senderNeuron) {
        this.senderNeuron = senderNeuron;
    }

    public Neuron getReceiverNeuron() {
        return receiverNeuron;
    }

    public void setReceiverNeuron(Neuron receiverNeuron) {
        this.receiverNeuron = receiverNeuron;
    }


    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

}
