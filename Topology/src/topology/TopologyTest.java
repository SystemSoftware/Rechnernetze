/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topology;


public class TopologyTest {

    static void findRoute(NetworkTopology.Node a, NetworkTopology.Node b, int len) {
	if (a.wasVisited())
	{
	    int at = a.getDistance();
	    if (at <= len)
		return;
	}
	a.setVisited(len);
	if (a == b)
	    return;
	for (NetworkTopology.Link n : a.getLinks())
	    findRoute(n.opposite(a),b,len+1);
    }
    static int getRouteLength(NetworkTopology.Node a, NetworkTopology.Node b) {
	findRoute(a,b,0);
        return b.getDistance();
    }
    /**
     * Determines the diameter (longest distance between two nodes) of the
     * specified topology
     * @param t Topology to get the diameter of
     * @return Maximum number of hops between any two nodes
     */
    static int getDiameter(NetworkTopology t) {
	int max = 0;
	int n = t.getNodes().size();
	for (int i = 0; i+1 < n; i++)
	{
	    for (int j = i+1; j < n; j++)
	    {
		t.clearNodeVisitedMarking();
		max = Math.max(max, getRouteLength(t.getNodes().get(i),t.getNodes().get(j)));
	    }
	}
	return max;
    }

    /**
     * Determines the connectivity (minimum number of edges to remove before
     * the network dissolves) of the given topology
     * @param topology Topology to get the connectivity of
     * @return Minimum number of edges that must be removed before nodes can no
     * longer reach parts of the rest of the topology.
     */
    static int getConnectivity(NetworkTopology topology) {
	
	//TODO
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	return 0;
    }
    
    /**
     * Tests a given topology for diameter and connectivity for a range of node-
     * counts. The topology is repeatedly rebuild and checked.
     * Results are written to the console
     * @param t Topology to test
     */
    static void test(NetworkTopology t)
    {
	for (int n = 1; n < 16; n++)
	{
	    t.rebuild(n);
	    System.out.println(t+ "[n="+n+"] diameter="+getDiameter(t));
	    System.out.println(t+ "[n="+n+"] connectivity="+getConnectivity(t));
	}

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	for (int k = 2; k < 8; k++)
	{
	    test(new Mesh(k,true));
	    test(new Mesh(k,false));
	    test(new KTree(k));
	}
	
    }
    
}
