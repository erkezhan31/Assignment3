package mst;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "data/assign_3_input.json";
        if (args.length>0) inputPath = args[0];
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(new File(inputPath));
        ArrayNode graphs = (ArrayNode)root.get("graphs");
        ArrayNode outResults = om.createArrayNode();
        for (JsonNode gnode : graphs) {
            List<String> nodes = new ArrayList<>();
            for (JsonNode n : gnode.get("nodes")) nodes.add(n.asText());
            List<Edge> edges = new ArrayList<>();
            for (JsonNode e : gnode.get("edges")) {
                String u = e.get("from").asText();
                String v = e.get("to").asText();
                double w = e.get("weight").asDouble();
                edges.add(new Edge(u,v,w));
            }
            Graph g = new Graph(nodes, edges);
            Result p = Prim.run(g);
            Result k = Kruskal.run(g);
            ObjectNode res = om.createObjectNode();
            res.put("graph_id", gnode.get("id").asInt());
            ObjectNode stats = om.createObjectNode();
            stats.put("vertices", g.V());
            stats.put("edges", g.E());
            res.set("input_stats", stats);
            res.set("prim", serializeResult(om, p));
            res.set("kruskal", serializeResult(om, k));
            outResults.add(res);
        }
        ObjectNode out = om.createObjectNode();
        out.set("results", outResults);
        String outPath = "data/assign_3_output_java.json";
        om.writerWithDefaultPrettyPrinter().writeValue(new File(outPath), out);
        System.out.println("Wrote: "+outPath);
    }

    private static ObjectNode serializeResult(ObjectMapper om, Result r) {
        ObjectNode n = om.createObjectNode();
        ArrayNode edges = om.createArrayNode();
        for (Map<String,Object> e : r.mstEdges) {
            ObjectNode en = om.createObjectNode();
            en.put("from", (String)e.get("from"));
            en.put("to", (String)e.get("to"));
            en.put("weight", ((Number)e.get("weight")).doubleValue());
            edges.add(en);
        }
        n.set("mst_edges", edges);
        n.put("total_cost", r.totalCost);
        n.put("operations_count", r.operationsCount);
        n.put("execution_time_ms", r.executionTimeMs);
        return n;
    }
}
