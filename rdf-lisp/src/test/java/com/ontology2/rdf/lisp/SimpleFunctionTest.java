package com.ontology2.rdf.lisp;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.junit.Test;

import java.io.InputStream;

import static org.apache.jena.riot.Lang.NTRIPLES;
import static org.apache.jena.riot.Lang.TURTLE;

public class SimpleFunctionTest {
    @Test
    public void four() {
        InputStream that=getClass().getResourceAsStream("four.ttl");
        Model expression= ModelFactory.createDefaultModel();
        RDFDataMgr.read(expression,that, TURTLE);
        RDFDataMgr.write(System.out,expression, NTRIPLES);
        Interpreter i=new Interpreter();
        RDFNode result=i.eval(expression);
        System.out.println(result);
    }
}
