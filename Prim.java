package mst;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Prim {
    public static Result run(Graph g) {
        long start = System.nanoTime();
        long ops = 0;
        Set<String> visited = new HashSet<>();
        List<Map<String,Object>> mst = new ArrayList<>();
        double total = 0.0;
        if (g.V() == 0) return new Result(mst, total, ops, 0);
        String startNode = g.getNodes().get(0);
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.naturalOrder());
        visited.add(startNode);
        for (Edge e : g.getAdj().get(startNode)) {
            pq.add(e); ops++;
        }
        while (!pq.isEmpty() && visited.size() < g.V()) {
            Edge e = pq.poll(); ops++;
            String to = visited.contains(e.u) ? e.v : e.u;
            if (visited.contains(to)) { ops++; continue; }
            visited.add(to);
            Map<String,Object> m = new HashMap<>();
            m.put("from", e.u);
            m.put("to", e.v);
            m.put("weight", e.w);
            mst.add(m);
            total += e.w;
            for (Edge ne : g.getAdj().get(to)) {
                String other = ne.u.equals(to) ? ne.v : ne.u;
                if (!visited.contains(other)) { pq.add(ne); ops++; }
            }
        }
        long end = System.nanoTime();
        return new Result(mst, total, ops, (end-start)/1_000_000.0);
    }
}
