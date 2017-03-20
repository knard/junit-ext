package org.knard.junitext.directedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DirectedGraph<NodeType> {
	private final Comparator<Object> byPriority = new Comparator<Object>() {
		
		@Override
		public int compare(Object object1, Object object2) {
			NodePriority p1 = nodePriorities.get(object1);
			if( p1 == null ) {
				p1 = NodePriority.NORMAL;
			}
			NodePriority p2 = nodePriorities.get(object2);
			if( p2 == null ) {
				p2 = NodePriority.NORMAL;
			}
			return p1.compareTo(p2);
		}
	};
	private Map<NodeType, Set<NodeType>> allOutcomingEdges = new HashMap<NodeType, Set<NodeType>>();
	private Map<NodeType, Set<NodeType>> allIncomingEdges = new HashMap<NodeType, Set<NodeType>>();
	private Map<NodeType, NodePriority> nodePriorities = new HashMap<NodeType, NodePriority>();

	public void addDirectedEdge(NodeType from, NodeType to) {
		addOutcomingEdge(from, to);
		addIncomingEdge(to, from);
	}

	private void addIncomingEdge(NodeType to, NodeType from) {
		addEdge(allIncomingEdges, to, from);
	}

	private void addOutcomingEdge(NodeType from, NodeType to) {
		addEdge(allOutcomingEdges, from, to);
	}

	private static <NodeType> void addEdge(Map<NodeType, Set<NodeType>> edges, NodeType from, NodeType to) {
		Set<NodeType> targetNodes = edges.get(from);
		if (targetNodes == null) {
			targetNodes = new HashSet<NodeType>();
			edges.put(from, targetNodes);
		}
		targetNodes.add(to);
		addNode(edges, to);
	}

	public List<NodeType> getTopologicalSort() throws GraphException {
		List<NodeType> result = new ArrayList<NodeType>(allOutcomingEdges.size());
		List<NodeType> nodeWithoutOutcomingEdges = initListWithoutOutcomingEdges();
		Collections.sort(nodeWithoutOutcomingEdges, byPriority);
		while (!nodeWithoutOutcomingEdges.isEmpty()) {
			NodeType node = nodeWithoutOutcomingEdges.remove(0);
			result.add(node);
			for (NodeType havingIncomingEdge : allIncomingEdges.get(node)) {
				Set<NodeType> outcomingEdgeTarget = allOutcomingEdges.get(havingIncomingEdge);
				outcomingEdgeTarget.remove(node);
				if (outcomingEdgeTarget.isEmpty()) {
					nodeWithoutOutcomingEdges.add(havingIncomingEdge);
					allOutcomingEdges.remove(havingIncomingEdge);
				}
			}
			Collections.sort(nodeWithoutOutcomingEdges, byPriority);
		}
		if (allOutcomingEdges.isEmpty()) {
			return result;
		} else {
			throw new GraphException("Graph has at least one cycle");
		}

	}

	private List<NodeType> initListWithoutOutcomingEdges() {
		List<NodeType> result = new LinkedList<NodeType>();
		for (Entry<NodeType, Set<NodeType>> e : allOutcomingEdges.entrySet()) {
			if (e.getValue().isEmpty()) {
				NodeType node = e.getKey();
				result.add(node);
			}
		}
		for (NodeType node : result) {
			allOutcomingEdges.remove(node);
		}
		return result;
	}

	public void addNode(NodeType node) {
		addNode(allIncomingEdges, node);
		addNode(allOutcomingEdges, node);
	}

	private static <NodeType> void addNode(Map<NodeType, Set<NodeType>> edges, NodeType node) {
		if (edges.get(node) == null) {
			edges.put(node, new HashSet<NodeType>());
		}
	}

	public void setPriority(NodeType node, NodePriority nodePriority) {
		nodePriorities.put(node, nodePriority);
	}

}
