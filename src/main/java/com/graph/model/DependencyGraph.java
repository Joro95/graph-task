package com.graph.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependencyGraph {

    //Key is square name -> value is list of squares that depend on key square (contain it in their expression)
    private Map<String, HashSet<Square>> dependencyGraph;

    public DependencyGraph() {
        this.dependencyGraph = new HashMap<>();
    }


    public void addDependencies(List<String> squareNames, Square dependency){
        for (String squareName : squareNames){
            if (!dependencyGraph.containsKey(squareName)){
                dependencyGraph.put(squareName, new HashSet<>());
            }
            dependencyGraph.get(squareName).add(dependency);
        }
    }

    public void recalculateDependenciesOf(String updatedSquare){
        if (dependencyGraph.containsKey(updatedSquare)){
            Set<Square> dependencies = dependencyGraph.get(updatedSquare);
            for (Square square : dependencies){
                square.calculateValue();
            }
        } else {
            dependencyGraph.put(updatedSquare, new HashSet<>());
        }
    }


}
