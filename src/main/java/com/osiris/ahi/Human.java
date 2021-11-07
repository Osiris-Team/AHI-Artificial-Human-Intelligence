package com.osiris.ahi;

import com.osiris.ahi.organs.brain.Brain;
import com.osiris.ahi.organs.brain.Neuron;
import com.osiris.ahi.organs.brain.Synapse;

public class Human {
    private Brain brain;

    /**
     * Creates an artificial human. <br>
     * Currently available organs: <br>
     * 1. {@link #getBrain()} <br>
     * A brain with 1 million {@link Neuron}s (0,1% of a real average brain with 100 billion {@link Neuron}s).
     * Note that a real average brain also has 10000 {@link Synapse}s (connections) per {@link Neuron}.
     * In comparison,
     * a real brain has around 100 billion {@link Neuron}s,
     * which means that we are currently only using 0,1%.
     *         // 100 billion neurons | We use  which are 1 million neurons
     *         // 10000 connections per neuron | We use 0,1% which are 10 connections
     * @throws Exception
     */
    public Human() throws Exception {
        this(new Brain(1000000));
    }

    public Human(Brain brain) {
        this.brain = brain;
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }
}
