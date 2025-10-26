package mst;

import java.util.*;

public class DSU {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long ops = 0;

    public DSU(List<String> nodes) {
        for (String n : nodes) {
            parent.put(n, n);
            rank.put(n, 0);
        }
    }
    public String find(String x) {
        ops++;
        String p = parent.get(x);
        if (!p.equals(x)) {
            String r = find(p);
            parent.put(x, r);
            return r;
        }
        return p;
    }
    public boolean union(String a, String b) {
        ops++;
        String ra = find(a);
        String rb = find(b);
        if (ra.equals(rb)) return false;
        int raRank = rank.get(ra), rbRank = rank.get(rb);
        if (raRank < rbRank) parent.put(ra, rb);
        else if (raRank > rbRank) parent.put(rb, ra);
        else {
            parent.put(rb, ra);
            rank.put(ra, raRank+1);
        }
        return true;
    }
}
