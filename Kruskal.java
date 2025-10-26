package mst;

import java.util.*;

public class Kruskal {
    public static Result run(Graph g) {
        long start = System.nanoTime();
        long ops = 0;
        List<Edge> edges = new ArrayList<>(g.getEdges());
        Collections.sort(edges);
        ops += edges.size() * (long)Math.log(edges.size()+1); // rough
        DSU dsu = new DSU(g.getNodes());
        List<Map<String,Object>> mst = new ArrayList<>();
        double total = 0.0;
        for (Edge e : edges) {
            ops++;
            if (dsu.union(e.u, e.v)) {
                Map<String,Object> m = new HashMap<>();
                m.put("from", e.u);
                m.put("to", e.v);
                m.put("weight", e.w);
                mst.add(m);
                total += e.w;
            }
        }
        ops += dsu.ops;
        long end = System.nanoTime();
        return new Result(mst, total, ops, (end-start)/1_000_000.0);
    }
}
