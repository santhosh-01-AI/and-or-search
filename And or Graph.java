import  java . util . *;

public class AndOrGraph {

	ArrayList < Node >	 nodes = new  ArrayList < Node > ();		 // AND / OR Dynamic array representing the graph
	Node 			current ;							 // The node you are currently paying attention to
	String 			solution ;							 // Variable that represents the search target
	
	public void setRoot(String st){
		if(!nodes.isEmpty()){
			System .out .println ( " Print the current graph" );
			nodes.clear();
		}
		current = new Node(0, st, 0, 0);
		nodes.add(current);
	}
	
	public void setSolution(String st){
		solution = st;
	}
	
	// Remove the node specified by idx from the array
	public void deleteNode(int idx){
		// Delete the edge from the parent node
		nodes.get(nodes.get(idx).getFrom()).removeTo(idx);
		nodes.remove(idx);
	}
	
	// Method to check and assign the search state of the node
	public void setNodeState(Node nd){
		if(nd.isUnknown()){
			if(nd.isNodeLeaf()){
				// Leaf is solved when it matches solution
				if(nd.getStr().equals(solution)){
					nd.setSolved();
					System.out.println("\"" + solution +"\" is found");
				}else{
					nd.setUnsolved();
				}
			}else if(nd.isNodeAND()){
				int i;
				// AND node is solved when all child nodes are solved
				for(i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isUnsolved()){
						nd.setUnsolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setSolved();
			}else if(nd.isNodeOR()){
				int i;
				// OR node is solved when at least one of the child nodes is solved
				for( i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isSolved()){
						nd.setSolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setUnsolved();
			}
		}
	}
	
	// Display all nodes information
	public void printNodes(){
		System.out.println("Print Nodes(current idx = " + current.getIdx() + ")");
		for(int i=0; i<nodes.size(); i++){
			nodes.get(i).printStatus();
		}
		System.out.println("End");
	}
	
	// Method to search the solution graph
	public void search(){
		// Loop until the root node is solved
		while(nodes.get(0).isUnknown()){
			// Loop to expand the current node
			while(current.isUnknown()){
				try{
					printNodes();
					// Create a child node and add it to the solution graph
					current = current.generateNextNode(nodes.size());
					System.out.println("Generated node: ");
					current.printStatus();
					System.out.println();
					nodes.add(current);
				}catch(RuntimeException e){
					System.out.println("Error");
				}
				// Update the node search state
				setNodeState(current);
			}
			System.out.println("Return to parent node:" + current.getFrom());
			// Return to the parent node and update the search state
			current = nodes.get(current.getFrom());
			setNodeState(current);
			
			// Code to do different processing with solved and unsolved
			/*
			if (current.isSolved ()) {
				current = nodes.get(current.getFrom());
				SetNodeState(current);
			}else{
				int idx = current.getIdx();
				current = nodes.get(current.getFrom());
				nodes.remove(idx);
				current.removeTo(idx);
				SetNodeState(current);
			}
			*/
		}
		System . out . println ( "Explore is over\nSolutionグラフ:" );
		printNodes();
		
		if(!nodes.isEmpty() && nodes.get(0).isSolved()){
			System .out .println ( " Search successful \ nSolution graph nodes obtained" );
		}else{
			System . out . println ( "Exploration failed" );
		}		
	}
	
	// Method to get the string from the solution graph
	public String getSolution(){
		String result = "";
		
		if(nodes.isEmpty() || !nodes.get(0).isSolved()){
			System .out .println ( "This graph is not solved" ) ;
			return result;
		}
		
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).isNodeLeaf() && nodes.get(i).isSolved()){
				result += nodes.get(i).getStr();
			}
		}
		return result;
	}
}