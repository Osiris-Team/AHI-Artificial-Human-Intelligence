package com.osiris.ahi.organs.brain;

import com.osiris.ahi.events.EventSignalDeath;
import com.osiris.ahi.events.Eventable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The brain of an AI
 * containing {@link Neuron}s and
 * {@link Worker}s which interact with
 * these.
 */
public class Brain extends Thread{
    private Neuron[] neurons;

    private Thread workerStatusPrinterThread;
    private long msPerLoop = -1;

    /**
     * Creates a new brain with
     * 1 million {@link Neuron}s and
     * 5 {@link Worker}s. <br>
     * See {@link #Brain(Neuron[], Worker[])} for details.
     */
    public Brain() throws Exception {
        this(new Neuron[1000000]);
    }

    /**
     * See {@link #Brain(Neuron[], Worker[])} for details.
     */
    public Brain(int neuronsAmount) throws Exception {
        this(new Neuron[neuronsAmount]);
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
    public Brain(Neuron[] neurons) throws Exception {
        this.neurons = neurons;

        // Null check
        Objects.requireNonNull(neurons);

        // Empty check
        if (neurons.length == 0) throw new Exception("Neurons array must be bigger than 0!");

        for (int i = 0; i < neurons.length; i++) {
            set(i, new Neuron(i));
        }
        this.start();

        workerStatusPrinterThread = new Thread(() ->{
            try{
                while(true){
                    Thread.sleep(1000);
                    if (this.isInterrupted() || !this.isAlive()){
                        System.out.println("Thread dead: "+this);
                        break;
                    }
                    else{
                        System.out.println(this.getName()+" | Neurons: "+neurons.length+" | Time per "+Integer.MAX_VALUE+" steps: "+(msPerLoop/1000)+"s ("+msPerLoop+"ms)");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        workerStatusPrinterThread.start();
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try{
                long start = System.currentTimeMillis();
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    // The first loop should remove dead synapses
                    for (int j = 0; j < neurons.length; j++) {
                        Neuron n = neurons[j];
                        List<Synapse> toRemove = new ArrayList<>(2);
                        for (Synapse s :
                                n.getSynapses()) {
                            toRemove.add(s);
                        }
                        for (Synapse s :
                                toRemove) {
                            if (s.getStrength() < 1) {
                                s.getSenderNeuron().getSynapses().remove(s);
                                s.getReceiverNeuron().getSynapses().remove(s);
                            }
                        }
                    }

                    // Then we fire a positive signal at the first and last neurons
                    fireSignal(neurons[0]);
                    fireSignal(neurons[neurons.length-1]);
                }
                msPerLoop = System.currentTimeMillis() - start;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized Signal fireSignal(Neuron senderNeuron){
        Synapse synapse = null;
        if(senderNeuron.getSynapses().size() == 0){
            // Connect with neighbor Neuron
            Neuron receiverNeuron = null;
            try{
                receiverNeuron = neurons[senderNeuron.getIndexInArray()+1];
            } catch (Exception e) {
                receiverNeuron = neurons[0]; // Back to first Neuron
            }
            synapse = connectNeurons(senderNeuron, receiverNeuron);
        } else{
            synapse = senderNeuron.getStrongestOrRandomSynapse(); // Null is impossible
        }
        Signal signal = new Signal(true, 100, event -> {
            //System.out.println("Signal died with strength "+event.getSignal().getStrength()+ " at "+event.getTimestamp().toString());
        });
        synapse.fireSignal(signal);
        return signal;
    }

    /**
     * Connects this {@link Neuron} to the neighboring {@link Neuron} by creating a new connection ({@link Synapse})
     * between them and adding it to this {@link Neuron}s {@link Synapse}s list. <br>
     * - Note that only a total of 10 connections are allowed! <br>
     * - This {@link Synapse} gets added to the front of the list. <br>
     * - If the neighboring {@link Neuron} already is connected, try the next neighbor and so on...
     *
     * @param neuron   the {@link Neuron} to connect to.
     * @param strength a value between 0 and 1. The closer to 1, the stronger is the connection.
     */
    public synchronized Synapse connectNeurons(Neuron senderNeuron, Neuron receiverNeuron) {
        // Check if this Neuron is already connected
        for (Synapse s :
                senderNeuron.getSynapses()) {
            if (s.getReceiverNeuron().equals(receiverNeuron)) {
                return s;
            }
        }

        Synapse s = new Synapse(senderNeuron, receiverNeuron);
        senderNeuron.getSynapses().add(s); // Add this synapse only to the sender neuron
        return s;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
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
