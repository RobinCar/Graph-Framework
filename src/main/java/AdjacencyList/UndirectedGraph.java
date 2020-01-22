package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractListGraph;
import Abstraction.IUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public UndirectedGraph() {
		this.nodes = new ArrayList<>();
	}

	public UndirectedGraph(List<A> nodes) {
		super(nodes);
		for (UndirectedNode i : nodes) {
			this.m += i.getNbNeigh();
		}
	}

	public UndirectedGraph(int[][] matrix) {
		this.order = matrix.length;
		this.nodes = new ArrayList<>();
		for (int i = 0; i < this.order; i++) {
			this.nodes.add(this.makeNode(i));
		}
		for (A n : this.getNodes()) {
			for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
				A nn = this.getNodes().get(j);
				if (matrix[n.getLabel()][j] != 0) {
					n.getNeighbours().put(nn, 0);
					nn.getNeighbours().put(n, 0);
					this.m++;
				}
			}
		}
	}

	public UndirectedGraph(UndirectedGraph<A> g) {
		super();
		this.order = g.getNbNodes();
		this.m = g.getNbEdges();
		this.nodes = new ArrayList<>();
		for (A n : g.getNodes()) {
			this.nodes.add(makeNode(n.getLabel()));
		}
		for (A n : g.getNodes()) {
			A nn = this.getNodes().get(n.getLabel());
			for (UndirectedNode sn : n.getNeighbours().keySet()) {
				A snn = this.getNodes().get(sn.getLabel());
				nn.getNeighbours().put(snn, 0);
				snn.getNeighbours().put(nn, 0);
			}
		}

	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	@Override
	public int getNbEdges() {
		return this.m;
	}

	@Override
	public boolean isEdge(A x, A y) {
		return this.getNodeOfList(x).getNeighbours().containsKey(this.getNodeOfList(y));
	}

	@Override
	public void removeEdge(A x, A y) {
		if (isEdge(x, y)) {
			this.getNodeOfList(x).getNeighbours().remove(this.getNodeOfList(y));
			this.getNodeOfList(y).getNeighbours().remove(this.getNodeOfList(x));
		}
	}

	@Override
	public void addEdge(A x, A y) {
		if (!isEdge(x, y)) {
			this.getNodeOfList(x).getNeighbours().put(this.getNodeOfList(y), 0);
			this.getNodeOfList(y).getNeighbours().put(this.getNodeOfList(x), 0);
		}
	}

	// --------------------------------------------------
	// Methods
	// --------------------------------------------------

	/**
	 * Method to generify node creation
	 * 
	 * @param label of a node
	 * @return a node typed by A extends UndirectedNode
	 */
	@Override
	public A makeNode(int label) {
		return (A) new UndirectedNode(label);
	}

	/**
	 * @return the corresponding nodes in the list this.nodes
	 */
	public A getNodeOfList(A src) {
		return this.getNodes().get(src.getLabel());
	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	@Override
	public int[][] toAdjacencyMatrix() {
		int[][] matrix = new int[order][order];
		for (UndirectedNode node : this.getNodes()) {
			for (UndirectedNode neighbour : node.getNeighbours().keySet()) {
				matrix[node.getLabel()][neighbour.getLabel()]++;
			}
		}
		return matrix;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (UndirectedNode n : nodes) {
			s.append("neighbours of ").append(n).append(" : ");
			for (UndirectedNode sn : n.getNeighbours().keySet()) {
				s.append(sn).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
		GraphTools.afficherMatrix(mat);
		UndirectedGraph al = new UndirectedGraph(mat);
		System.out.println(al);
		al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println();
		System.out.println(al);
		al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println();
		System.out.println(al);
		al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println();
		System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
		System.out.println();
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : al.toAdjacencyMatrix()) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		System.out.println(s.toString());
	}

}
