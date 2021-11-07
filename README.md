# AHI-Artificial-Human-Intelligence

A humble attempt of creating a digital human.

## Status

This project is in development. Any help is welcome.

## Example
- [Simple example on how to create and run a digital human](/src/test/java/AIPlayground.java)

## Research

<div>
  <details>
    <summary>Body</summary>
    <p> Contains all organs for a functioning organism.
The main organ of course being the brain, which is connected to I/O (Input/Output) organs to interact with the surrounding world.
</p>
  </details>
  <details>
    <summary>Brain</summary>
<p>
Contains neurons. Because of hardware limitations its not possible to have the same amount of neurons a human has.
Currently we are creating brains with 1 million neurons (0,1% of a real average brain with 100 billion neurons).
Note that a real average brain also has 10000 synapses (connection to other neuron) per neuron.
So we are both in quantity of neurons and synapses in disadvantage,
which means that our artificial human, basically has shorter memory and is a bit dumber.
A real brain however has a maximal brainwave frequency of 30Hz, which means
that our artificial human has the possibility of thinking much faster than us.
</p>
  </details>
  <details>
  <summary>Neuron</summary>
<p>
Can connect to other Neurons, which results in a Synapse and receive/send Signals over it.
</p>
  </details>
  <details>
  <summary>Signal/Neurotransmitter</summary>
<p>
Can be sent/received by Neurons.
    Can die when its not forwarded to another Neuron or has not enough strength.
  Can either be excitatory (positive) or inhibitory (negative).
</p>
  </details>
  <details>
  <summary>Memory</summary>
<p>The path of Neurons a Signal took until it died.
</p>
  </details>
</div>
