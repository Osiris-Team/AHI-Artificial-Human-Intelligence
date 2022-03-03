package com.osiris.ahi.organs.brain;

public class BrainV2 {
    public int cubeSize = 126 * 126 * 126;
    public NeuronV2[] neurons = createNeuronsCube();

    private NeuronV2[] createNeuronsCube() {
        NeuronV2[] neurons = new NeuronV2[cubeSize];
        int index = 0;
        for (byte i = 0; i < 126; i++) {
            neurons[i] = new NeuronV2(i, (byte) 0, (byte) 0);
            index++;
        }
        for (byte i = 0; i < 126; i++) {
            neurons[i] = new NeuronV2((byte) 0, i, (byte) 0);
            index++;
        }
        for (byte i = 0; i < 126; i++) {
            neurons[i] = new NeuronV2((byte) 0, (byte) 0, i);
            index++;
        }
    }

}
