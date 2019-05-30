package org.simbrain.util.neat2;

import org.simbrain.network.core.Neuron;
import org.simbrain.network.neuron_update_rules.LinearRule;
import org.simbrain.network.neuron_update_rules.SigmoidalRule;
import org.simbrain.util.neat.ConnectionGene;

import java.awt.geom.Point2D;

public class NodeGene extends Gene<Neuron> {

    /**
     * Types of Node.
     */
    public enum NodeType { input, hidden, output }

    /**
     * The type of this node
     */
    private NodeType type;

    /**
     * Index for this node; used in {@link ConnectionGene}.
     * TODO: Implement.
     */
    private int nodeIndex;

    private Point2D location;

    private String neuronGroupName;

    /**
     * Prototype neuron
     */
    private Neuron prototype;

    public NodeGene(NodeType type) {
        this.type = type;
        this.prototype = new Neuron(null);
    }

    public NodeGene() {
        this(NodeType.hidden);
        this.prototype.setUpdateRule(new SigmoidalRule());
    }

    @Override
    public Neuron getPrototype() {
        return prototype;
    }

    @Override
    public void mutate() {

    }

    @Override
    public NodeGene copy() {
        NodeGene ret = new NodeGene();
        ret.type = this.type;
        ret.neuronGroupName = this.neuronGroupName;
        ret.prototype = this.prototype.deepCopy();
        return ret;
    }

    public String getNeuronGroupName() {
        return neuronGroupName;
    }

    public void setNeuronGroupName(String neuronGroupName) {
        this.neuronGroupName = neuronGroupName;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
        if (type != NodeType.hidden) {
            this.prototype.setUpdateRule(new LinearRule());
        }
    }
}