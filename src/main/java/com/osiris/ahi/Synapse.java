/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ahi;

import java.util.Objects;

/**
 * A {@link Synapse} represents
 * a connection between two {@link Neuron}s.
 * It has the ability of transmitting
 * a {@link Signal} from Neuron 1 to Neuron 2.
 */
public class Synapse {
    private Neuron neuron1;
    private Neuron neuron2;
    //private Double strength;

    /**
     * A {@link Synapse} represents
     * a connection between two {@link Neuron}s.
     * It has the ability of transmitting
     * a {@link Signal} from Neuron 1 to Neuron 2.
     */
    public Synapse(Neuron neuron1, Neuron neuron2) {
        Objects.requireNonNull(neuron1);
        Objects.requireNonNull(neuron2);
        //if (strength>1.0 || strength<0)
        //    throw new NullPointerException("Strength must be a value between 0.0 and 1.0");
        this.neuron1 = neuron1;
        this.neuron2 = neuron2;
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
        neuron2.receiveSignalAndForward(signal);
    }

    public Neuron getNeuron1() {
        return neuron1;
    }

    public void setNeuron1(Neuron neuron1) {
        this.neuron1 = neuron1;
    }

    public Neuron getNeuron2() {
        return neuron2;
    }

    public void setNeuron2(Neuron neuron2) {
        this.neuron2 = neuron2;
    }

    /*
    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

     */
}
