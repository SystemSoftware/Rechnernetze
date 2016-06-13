/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topology;

import java.util.ArrayList;

/**
 * Matrix-like mesh topology
 */
class Mesh extends NetworkTopology {

    final int w;
    final boolean circular;
    
    /**
     * Constructs a new mesh.
     * The mesh will fill row by row. If @a circular is set, then incomplete
     * bottom rows will only partially link to the top row.
     * @param width Maximum number of nodes that may be arranged in one row
     * @param circular Set true to connect nodes on one edge to the opposing
     * edge.
     */
    public Mesh(int width, boolean circular)
    {
	w = width;
	this.circular = circular;
    }
    
    @Override
    public String toString()
    {
	return (circular? "CircularMesh":"Mesh")+"("+w+")";
    }    
    
    @Override
    protected void rebuildNetwork(int numNodes)
    {
	for (int i = 0; i < numNodes; i++)
	{
	    Node n = new Node();
	    nodes.add(n);
	    if (i >= w)
	    {
		n.connectTo(nodes.get(i-w));
	    }
	    
	    if ((i%w)>0)
		n.connectTo(nodes.get(i-1));
	    if (circular)
	    {
		if ((i%w)+1 == w)
		    n.connectTo(nodes.get((i/w) * w));
		if (i/w == (numNodes-1) / w)
		    n.connectTo(nodes.get((i%w)));
	    }
	}
	
    }

}
