/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Network topology container
 */
public abstract class NetworkTopology {

    /**
     * Rebuilds the topology network.
     * Is only ever called after clear() is issued.
     * @param numNodes Number of nodes to construct the topology for
     */
    protected abstract void rebuildNetwork(int numNodes);
    
    /**
     * Non-directional edge between two nodes.
     */
    public class Link {
	public final Node from,	//!< Node the link originates in
			    to;	//!< Node the link terminates in
	
	public Link(Node from, Node to) {
	    this.from = from;
	    this.to = to;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Link))
		return false;
	    Link o = (Link)other;
	    return (from == o.from && to == o.to)
		    ||
		    (from == o.to && to == o.from);
	}

	@Override
	public int hashCode() {
	    int hash = 5;
	    hash = 17 * hash + Objects.hashCode(this.from);
	    hash = 17 * hash + Objects.hashCode(this.to);
	    return hash;
	}
	/**
	 * Fetches the opposite node in the local edge
	 * @param n Node to fetch the opposite of
	 * @return Opposing node, or null, if the specified node is not part
	 * of the local edge.
	 */
	public Node opposite(Node n) {
	    if (from == n)
		return to;
	    if (to == n)
		return from;
	    return null;
	}
	
	private int levelOfRemoval = 0;
	
	/**
	 * Increases the internal counter to consider this link removed
	 */
	public void tagAsRemoved() {
	    levelOfRemoval ++;
	}
	/**
	 * Decreases the internal counter to consider this link removed
	 */
	public void untagAsRemoved() {
	    levelOfRemoval --;
	}
	
	/**
	 * Checks whether or not this edge is tagged as removed
	 * @return True if the internal removal counter is greater than zero
	 */
	public boolean isTaggedAsRemoved() {
	    return levelOfRemoval > 0;
	}
	/**
	 * Unsets the removal tagging
	 */
	public void clearRemovalTag() {
	    levelOfRemoval = 0;
	}
	
    }
    
    /**
     * Participating network node of the local topology
     */
    public class Node {
	/**
	 * All links (out and inbound), terminating in the local node
	 */
	private ArrayList<Link>	links = new ArrayList<>();
	
	/**
	 * Fetches all local links
	 * @return Unmodifable list of all links starting or ending in the local node
	 */
	public List<Link>    getLinks() {
	    return Collections.unmodifiableList(links);
	}
	/**
	 * 
	 * @return Number of links beginning or ending in the local node
	 */
	public int countPeers() {return links.size();}
	
	/**
	 * Attempts to establish a new link to the specified peer
	 * @param peer Node to connect to
	 * @return True, if a new connection was established, false if a
	 * connection to or from the specified peer already existed
	 */
	public boolean	connectTo(Node peer) {
	    Link link = new Link(this,peer);
	    if (links.contains(link))
		return false;
	    links.add(link);
	    peer.links.add(link);
	    allLinks.add(link);
	    return true;
	}
	
	
	/**
	 * Attempts to remove a link to the specified node
	 * @param peer Node to disconnect from
	 * @return True, if an existing connection was found and removed,
	 * false otherwise
	 */
	public boolean	disconnectFrom(Node peer) {
	    Link link = new Link(this,peer);
	    if (!links.remove(link))
		return false;
	    peer.links.remove(link);
	    allLinks.remove(link);
	    return true;
	}
	
	/**
	 * Drops all connections from/to the local node
	 * @param cascade Set true to also clear the links of all neighbors
	 */
	public void clearLinkage(boolean cascade) {
	    if (links.isEmpty())
		return;
	    ArrayList<Link> p = links;
	    links = new ArrayList<>();
	    if (cascade)
		for (Link n : p)
		    n.opposite(this).clearLinkage(true);
	}

	private int visited = -1;
	
	public void setVisited() {
	    if (visited < 0)
		visited = 0;
	}
	public void setVisited(int distance) {
	    visited = distance;
	}
	
	public void clearVisitedMark() {
	    visited = -1;
	}
	
	public int getDistance() {
	    return visited;
	}
	
	public boolean wasVisited() {
	    return visited >= 0;
	}
    }
    
    protected final ArrayList<Node> nodes = new ArrayList<>();

    private final ArrayList<Link> allLinks = new ArrayList<>();
    
    /**
     * Removes all nodes and links in the local topology
     */
    public void clear()	{nodes.clear(); allLinks.clear();}
    /**
     * Clears all nodes and rebuilds the local topology
     * @param numNodes 
     */
    public final void rebuild(int numNodes){
	clear();
	rebuildNetwork(numNodes);
    }
    
    /**
     * Fetches all nodes in the local topology
     * @return Unmodifiable list of all contained nodes
     */
    public List<Node>   getNodes()	{return Collections.unmodifiableList(nodes);}
    /**
     * Fetches all edges in the local topology
     * @return Unmodifable list of all contained inter-node edges
     */
    public List<Link>   getLinks()	{return Collections.unmodifiableList(allLinks);}
    
    
    /**
     * Clears the removed-tag of all links
     */
    public void clearLinkRemovalTags() {
	for (Link l : allLinks)
	    l.clearRemovalTag();
    }
    
    /**
     * Clears the visited marking (and distance setting) of all nodes
     */
    public void clearNodeVisitedMarking() {
	for (Node n : nodes)
	    n.clearVisitedMark();
    }
    
}
