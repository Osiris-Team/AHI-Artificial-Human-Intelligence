/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ahi.organs.brain;

import com.osiris.ahi.events.EventSignalDeath;

import java.util.ArrayList;
import java.util.List;

/**
 * Can change the strength of a {@link Synapse}(connection).
 * It also has its own strength, which
 * decreases with each new {@link Neuron} it reaches. <br>
 * When this {@link Signal}s strength is below the
 * {@link Neuron#getMaxSignalForwardCount()} the {@link Signal} dies
 * and throws a {@link EventSignalDeath}.
 */
public class Signal {
    private List<Synapse> synapsesPathList = new ArrayList<>();
    private boolean isPositive;
    private int strength;

    public Signal(boolean isPositive, int strength) {
        this.isPositive = isPositive;
        this.strength = strength;
    }

    public EventSignalDeath createAndGetDeathEvent() {
        return new EventSignalDeath(this);
    }

    /**
     * When this {@link Signal} gets passed
     * from one {@link Neuron} to another {@link Neuron}
     * via a {@link Synapse}, that {@link Synapse} gets
     * added to this {@link List<Synapse>}.
     *
     * @return A list containing all passed {@link Synapse}s in chronological order.
     */
    public List<Synapse> getSynapsesPathList() {
        return synapsesPathList;
    }

    /**
     * See {@link #getSynapsesPathList()} for details.
     */
    public void setSynapsesPathList(List<Synapse> synapsesPathList) {
        this.synapsesPathList = synapsesPathList;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
