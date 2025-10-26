package mst;

import java.util.*;

public class Result {
    public final List<Map<String,Object>> mstEdges;
    public final double totalCost;
    public final long operationsCount;
    public final double executionTimeMs;
    public Result(List<Map<String,Object>> mstEdges, double totalCost, long operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }
}
