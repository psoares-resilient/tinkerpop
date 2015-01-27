package com.tinkerpop.gremlin.process.graph.step.map;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.Traverser;
import com.tinkerpop.gremlin.process.graph.marker.Reducing;
import com.tinkerpop.gremlin.process.graph.step.util.ReducingBarrierStep;
import com.tinkerpop.gremlin.process.traverser.TraverserRequirement;
import com.tinkerpop.gremlin.util.tools.MeanNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class MeanStep<S extends Number> extends ReducingBarrierStep<S, MeanNumber> implements Reducing<MeanNumber, Traverser<S>> {

    private static final Set<TraverserRequirement> REQUIREMENTS = new HashSet<>(Arrays.asList(TraverserRequirement.OBJECT, TraverserRequirement.BULK));

    public MeanStep(final Traversal traversal) {
        super(traversal);
        this.setSeedSupplier(MeanNumber::new);
        this.setBiFunction((seed, start) -> seed.add(start.get(), start.bulk()));
    }

    @Override
    public Set<TraverserRequirement> getRequirements() {
        return REQUIREMENTS;
    }

    @Override
    public Reducer<MeanNumber, Traverser<S>> getReducer() {
        return new Reducer<>(this.getSeedSupplier(), this.getBiFunction(), true);
    }
}