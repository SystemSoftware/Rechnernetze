/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topology;


/**
 * Balanced k-Tree where each node may have up to k children
 */
final class KTree extends NetworkTopology{

    
    private final int k;
    
    /**
     * Creates a new k-Tree for a given constant k
     * @param k Maximum number of children per node
     */
    public KTree(int k) {
	if (k < 1)
	    throw new IllegalArgumentException("k must be 1 or more");
	this.k = k;
    }
    /**
     * Creates a new k-Tree and builds it for the specified number of
     * nodes
     * @param k Maximum number of children per node
     * @param numNodes Number of nodes to establish the tree for
     */
    public KTree(int k, int numNodes) {
	this(k);
	rebuild(numNodes);
    }

    
    @Override
    protected void rebuildNetwork(int numNodes) {
	
	//TODO
	
	
	
	
	
	
	
	
	
	
    }

    @Override
    public String toString()
    {
	return "KTree("+k+")";
    }
    
}
