/*     */ package org.antlr.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Graph
/*     */ {
/*  53 */   protected Map<Object, Node> nodes = new HashMap();
/*     */ 
/*     */   public void addEdge(Object a, Object b)
/*     */   {
/*  57 */     Node a_node = getNode(a);
/*  58 */     Node b_node = getNode(b);
/*  59 */     a_node.addEdge(b_node);
/*     */   }
/*     */ 
/*     */   protected Node getNode(Object a) {
/*  63 */     Node existing = (Node)this.nodes.get(a);
/*  64 */     if (existing != null) return existing;
/*  65 */     Node n = new Node(a);
/*  66 */     this.nodes.put(a, n);
/*  67 */     return n;
/*     */   }
/*     */ 
/*     */   public List<Object> sort()
/*     */   {
/*  82 */     Set visited = new OrderedHashSet();
/*  83 */     ArrayList sorted = new ArrayList();
/*  84 */     while (visited.size() < this.nodes.size())
/*     */     {
/*  86 */       Node n = null;
/*  87 */       for (Iterator it = this.nodes.values().iterator(); it.hasNext(); ) {
/*  88 */         n = (Node)it.next();
/*  89 */         if (!visited.contains(n)) break;
/*     */       }
/*  91 */       DFS(n, visited, sorted);
/*     */     }
/*  93 */     return sorted;
/*     */   }
/*     */ 
/*     */   public void DFS(Node n, Set<Node> visited, ArrayList<Object> sorted) {
/*  97 */     if (visited.contains(n)) return;
/*  98 */     visited.add(n);
/*     */     Iterator it;
/*  99 */     if (n.edges != null) {
/* 100 */       for (it = n.edges.iterator(); it.hasNext(); ) {
/* 101 */         Node target = (Node)it.next();
/* 102 */         DFS(target, visited, sorted);
/*     */       }
/*     */     }
/* 105 */     sorted.add(n.payload);
/*     */   }
/*     */ 
/*     */   public static class Node
/*     */   {
/*     */     Object payload;
/*     */     List<Node> edges;
/*     */ 
/*     */     public Node(Object payload)
/*     */     {
/*  42 */       this.payload = payload;
/*     */     }
/*     */     public void addEdge(Node n) {
/*  45 */       if (this.edges == null) this.edges = new ArrayList();
/*  46 */       if (!this.edges.contains(n)) this.edges.add(n); 
/*     */     }
/*     */ 
/*  49 */     public String toString() { return this.payload.toString(); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.Graph
 * JD-Core Version:    0.6.2
 */