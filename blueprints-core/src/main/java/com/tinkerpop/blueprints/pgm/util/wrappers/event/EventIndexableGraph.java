package com.tinkerpop.blueprints.pgm.util.wrappers.event;


import com.tinkerpop.blueprints.pgm.AutomaticIndex;
import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Index;
import com.tinkerpop.blueprints.pgm.IndexableGraph;
import com.tinkerpop.blueprints.pgm.Parameter;
import com.tinkerpop.blueprints.pgm.WrappableGraph;
import com.tinkerpop.blueprints.pgm.util.wrappers.event.util.EventIndexSequence;

import java.util.Set;

/**
 * EventIndexableGraph is merely a proxy to index methods exposing EventGraph methods in the "evented" way. Like the
 * EventGraph it extends from, this graph implementations raise notifications to the listeners for the
 * following events: new vertex/edge, vertex/edge property changed, vertex/edge property removed,
 * vertex/edge removed.
 *
 * @author Stephen Mallette
 */
public class EventIndexableGraph<T extends IndexableGraph> extends EventGraph<T> implements IndexableGraph, WrappableGraph<T> {

    public EventIndexableGraph(final T graph) {
        super(graph);
    }

    public void dropIndex(final String name) {
        this.getRawGraph().dropIndex(name);
    }

    public <T extends Element> Index<T> createManualIndex(final String indexName, final Class<T> indexClass, final Parameter... indexParameters) {
        return new EventIndex<T>(this.getRawGraph().createManualIndex(indexName, indexClass, indexParameters), this.graphChangedListeners);
    }

    public <T extends Element> AutomaticIndex<T> createAutomaticIndex(final String indexName, final Class<T> indexClass, final Set<String> autoIndexKeys, final Parameter... indexParameters) {
        return new EventAutomaticIndex<T>(this.getRawGraph().createAutomaticIndex(indexName, indexClass, autoIndexKeys, indexParameters), this.graphChangedListeners);
    }

    public <T extends Element> Index<T> getIndex(final String indexName, final Class<T> indexClass) {
        final Index<T> index = this.rawGraph.getIndex(indexName, indexClass);
        if (null == index)
            return null;
        else {
            if (index.getIndexType().equals(Index.Type.MANUAL))
                return new EventIndex<T>(index, this.graphChangedListeners);
            else
                return new EventAutomaticIndex<T>((AutomaticIndex<T>) index, this.graphChangedListeners);
        }
    }

    public Iterable<Index<? extends Element>> getIndices() {
        return new EventIndexSequence(this.rawGraph.getIndices().iterator(), this.graphChangedListeners);
    }
}