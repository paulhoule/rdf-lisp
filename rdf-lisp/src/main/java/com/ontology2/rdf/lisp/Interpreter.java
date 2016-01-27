package com.ontology2.rdf.lisp;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.query.QueryExecutionFactory.*;

public class Interpreter {

    static Query sparql(String name) {
        try {
            InputStream in = Interpreter.class.getResourceAsStream(name);
            String text = IOUtils.toString(in, Charsets.UTF_8);
            return QueryFactory.create(text, Syntax.syntaxARQ);
        } catch(IOException x) {
            throw new RuntimeException(x);
        }
    };

    static Query $findRoot=sparql("findroot.sparql");

    public RDFNode eval(Model m) {
        ResultSet rs=create($findRoot,m).execSelect();
        List<String> resultVars=rs.getResultVars();
        if(resultVars.size()==0)
            throw new RuntimeException("Found no result columns");

        if(resultVars.size()>1)
            throw new RuntimeException("Found too many result columns");

        final String columnName=resultVars.get(0);
        List<RDFNode> roots=new ArrayList<>();
        while (rs.hasNext()) {
            roots.add(rs.nextSolution().get(columnName));
        }

        if (roots.isEmpty())
            throw new RuntimeException("Found no expression root");

        if (roots.size()>1)
            throw new RuntimeException("Found more than one expression root");

        return m.createTypedLiteral(4);
    };

}
