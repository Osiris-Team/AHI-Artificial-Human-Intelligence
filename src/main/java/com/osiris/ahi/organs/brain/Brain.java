package com.osiris.ahi.organs.brain;

import java.util.Objects;

/**
 * The brain of an AI
 * containing {@link Neuron}s and
 * {@link Worker}s which interact with
 * these.
 */
public class Brain {
    private Neuron[] neurons;
    private Worker[] workers;

    /**
     * Creates a new brain with
     * 1 million {@link Neuron}s and
     * 5 {@link Worker}s. <br>
     * See {@link #Brain(Neuron[], Worker[])} for details.
     */
    public Brain() throws Exception {
        this(new Neuron[1000000], new Worker[5]);
    }

    /**
     * See {@link #Brain(Neuron[], Worker[])} for details.
     */
    public Brain(int neuronsAmount, int workersAmount) throws Exception {
        this(new Neuron[neuronsAmount], new Worker[workersAmount]);
    }

    /**
     * Creates a new {@link Brain} from
     * the provided {@link Neuron}s and {@link Worker}s arrays. <br>
     * Note that these arrays get filled/overwritten
     * with new {@link Neuron}s and {@link Worker}s.
     *
     * @param neurons A {@link Neuron}s array. Not null!
     * @param workers A {@link Worker}s array. Not null!
     * @throws Exception
     */
    public Brain(Neuron[] neurons, Worker[] workers) throws Exception {
        this.neurons = neurons;
        this.workers = workers;

        // Null check
        Objects.requireNonNull(neurons);
        Objects.requireNonNull(workers);

        // Empty check
        if (neurons.length == 0) throw new Exception("Neurons array must be bigger than 0!");
        if (workers.length == 0) throw new Exception("Workers array must be bigger than 0!");

        createWorkersAndNeurons();
    }

    /**
     * Creates the {@link Worker}s and {@link Neuron}s. <br>
     * Each worker needs a start and end index for
     * the part of the {@link Brain} it gets assigned to. <br>
     * Aka the part in the {@link Neuron}s array it gets to work with.
     */
    public void createWorkersAndNeurons() throws Exception {
        // Make sure that neurons/workers does
        // not produce a comma value.
        if (neurons.length % 2 == 0) {
            if (workers.length % 2 == 0)
                throw new Exception("Workers array size must be an odd number!");
        } else {
            if (workers.length % 2 != 0)
                throw new Exception("Workers array size must be an even number!");
        }

        int dividedValue = neurons.length / workers.length;
        int lastValue = 0;
        for (int i = 0; i < workers.length; i++) {
            int startIndex = 0;
            int endIndex = 0;

            if (i != 0) startIndex = lastValue + 1;
            endIndex = (lastValue = lastValue + dividedValue);

            System.out.println("Creating worker[" + i + "] for neurons [" + startIndex + " to " + endIndex + "]");
            Worker w = new Worker(this, startIndex, endIndex);
            workers[i] = w;
            // Create the neurons and set their workers
            for (int f = startIndex; f < endIndex; f++) {
                set(f, new Neuron(w, 5, f));
            }
            System.out.println("Success!");
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public Worker[] getWorkers() {
        return workers;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
    }

    /**
     * Sets the {@link Neuron} at index i.
     */
    public void set(int i, Neuron neuron) {
        neurons[i] = neuron;
    }

    /**
     * Returns the {@link Neuron} at index i.
     */
    public Neuron get(int i) {
        return neurons[i];
    }
}
