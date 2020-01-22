package GraphAlgorithms;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import Abstraction.AbstractListGraph;
import AdjacencyList.DirectedGraph;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList extends GraphTools {

	private static int _DEBBUG = 0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public GraphToolsList() {
		super();
	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	// ------------------------------------------
	// Methods
	// ------------------------------------------

	public void explorerGraphe(AbstractListGraph<AbstractNode> graph) {
		Set<AbstractNode> atteint = new HashSet<AbstractNode>();
		for (AbstractNode n : graph.getNodes()) {
			if (!atteint.contains(n)) {
				explorerSommet(n, atteint);
			}
		}
	}

	public void explorerSommet(AbstractNode n, Set<AbstractNode> a) {
		a.add(n);
		if (n instanceof DirectedNode) {
			for (AbstractNode t : ((DirectedNode) n).getSuccs().keySet()) {
				if (!a.contains(t)) {
					explorerSommet(t, a);
				}
			}
		} else if (n instanceof UndirectedNode) {
			for (AbstractNode t : ((UndirectedNode) n).getNeighbours().keySet()) {
				if (!a.contains(t)) {
					explorerSommet(t, a);
				}
			}
		}
	}

	public void explorerGrapheLargeur(AbstractListGraph<AbstractNode> graph) {
		boolean[] mark = new boolean[graph.getNbNodes()];
		for (AbstractNode n : graph.getNodes()) {
			mark[n.getLabel()] = false;
		}
		Queue<AbstractNode> toVisit = new PriorityQueue();
		toVisit.add(graph.getNodes().get(0));
		mark[graph.getNodes().get(0).getLabel()] = true;
		while (!toVisit.isEmpty()) {
			AbstractNode n = toVisit.poll();
			if (n instanceof DirectedNode) {
				for (AbstractNode t : ((DirectedNode) n).getSuccs().keySet()) {
					if (!mark[t.getLabel()]) {
						mark[t.getLabel()] = true;
						toVisit.add(n);
					}
				}
			} else if (n instanceof UndirectedNode) {
				for (AbstractNode t : ((UndirectedNode) n).getNeighbours().keySet()) {
					if (!mark[t.getLabel()]) {
						mark[t.getLabel()] = true;
						toVisit.add(n);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(Matrix);
		System.out.println(al);

		// A completer
	}
}
