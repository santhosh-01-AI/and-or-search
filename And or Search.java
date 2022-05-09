public class AndOrSearch {

	public static void main(String[] args) {
		
		AndOrGraph graph = new AndOrGraph();
		
		// Set production rules
		String[] x = {"ab", "cY"};
		String[] y = {"a", "c"};
		String[] z = {"Yc"};
		Node.setRule("X", x);
		Node.setRule("Y", y);
		Node.setRule("Z", z);
		
		// Set the root node
		graph.setRoot("XYZ");
		// Set the character to search
		graph.setSolution("c");
		
		graph.search();
		
		System .out .println ( " Obtained string:" + graph .getSolution ( ))	 ;
	}

}