package com.tinkerpop.blueprints.impls.usergrid;


import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.impls.GraphTest;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.usergrid.drivers.blueprints.UsergridGraph;

import java.lang.reflect.Method;

/**
 * Created by ApigeeCorporation on 7/13/15.
 */
public class UsergridGraphTest extends GraphTest {


    @Override
    public Graph generateGraph() {
        return generateGraph("/Users/nishitarao/dev/blueprints/blueprints-usergrid-graph/src/main/resources/usergrid.properties");
    }

    @Override
    public Graph generateGraph(String s) {
//        PropertiesConfiguration conf = null;
//        try {
//            conf = new PropertiesConfiguration(s);
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//        UsergridGraph graph = new UsergridGraph(conf);
//        return graph;
        Graph usergridgraph = GraphFactory.open(s);
        return usergridgraph;
    }

/*
    public void testVertexTestSuite() throws Exception {
        this.stopWatch();
        doTestSuite(new VertexTestSuite(this));
        printTestPerformance("VertexTestSuite", this.stopWatch());
    }

*/

/*
    public void testEdgeTestSuite() throws Exception {
        this.stopWatch();
        doTestSuite(new EdgeTestSuite(this));
        printTestPerformance("EdgeTestSuite", this.stopWatch());
    }

*/

    public void testGraphTestSuite() throws Exception {
        this.stopWatch();
        doTestSuite(new GraphTestSuite(this));
        printTestPerformance("GraphTestSuite", this.stopWatch());
    }





    @Override
    public void doTestSuite(TestSuite testSuite) throws Exception {
        for (Method method : testSuite.getClass().getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                System.out.println("Testing " + method.getName() + "...");
                Graph graph = this.generateGraph();
                method.invoke(testSuite);
                System.out.println("exectuted tests for : " + method.getName());
                //graph.shutdown();
            }
        }
    }

}
