import  java . util . *;

public class Node {
	// An enumerator represents the type of node
	enum NodeType {ANDNODE, ORNODE, LEAF, DEFAULT};
	enum NodeState {SOLVED, UNSOLVED, UNKNOWN};
	
	int 					idx ;							 // index that uniquely identifies the node
	String 				str ;							 // String at the node
	int 					next = 0 ;						 // AND node: points to the next character to expand
														// OR node: Refers to the next production rule to apply
	int 					from ;							 // index pointing to the parent node
	ArrayList < Integer >	 to = new  ArrayList < Integer > ();	 // Array of indexes pointing to child nodes
	NodeType 			type ;							 // Enumerator representing the type of node
	NodeState 			state = NodeState .UNKNOWN ; // Enumerator		 representing the search state of the node
	int 					depth = 0 ;						 // Node depth (used for graph output)
	
	// An associative array that stores the production rules
	static HashMap<String, String[]> rule = new HashMap<String, String[]>();
	
	public Node(int id){
		idx = id;
	}
	
	public Node(int id, String s){
		idx = id;
		str = s;
		decideNodeType();
	}
	
	public Node(int id, String s, int parent, int dp){
		idx = id;
		str = s;
		decideNodeType();
		from = parent;
		depth = dp;
	}
	
	///////////////////////////////////////////////////////////////////////////
	/////////////// The following methods interface with the field ////////////
	///////////////////////////////////////////////////////////////////////////

	/*
	// In principle, idx is assigned in the constructor
	public void setIdx(int index){
		idx = index;
	}
	*/
	
	public int getIdx(){
		return idx;
	}
	
	public void setStr(String input){
		str = input;
	}
	
	public String getStr(){
		return str;
	}

	public void setNext(int id){
		next = id;
	}
	
	public int getNext(){
		return next;
	}
	
	
	public void setFrom(int f){
		from = f;
	}
	
	public int getFrom(){
		return from;
	}
	
	public  void  addTo ( int  t ) {
		to.add(t);
	}
	
	public void removeTo(int nodeId){
		for(int i=0; i<to.size(); i++)
			if(to.get(i) == nodeId)
				to.remove(i);
	}
	
	public ArrayList<Integer> getTo(){
		return to;
	}
	
	/*
	// In principle, type is assigned in the constructor
	public void setType(NodeType nt){
		type = nt;
	}
	*/
	
	public boolean isNodeAND(){
		return type == NodeType.ANDNODE;
	}
	
	public boolean isNodeOR(){
		return type == NodeType.ORNODE;
	}
	
	public boolean isNodeLeaf(){
		return type == NodeType.LEAF;
	}
	
	public void setSolved(){
		state = NodeState.SOLVED;
	}
	
	public void setUnsolved(){
		state = NodeState.UNSOLVED;
	}
	
	public boolean isSolved(){
		return state == NodeState.SOLVED;
	}
	
	public boolean isUnsolved(){
		return state == NodeState.UNSOLVED;
	}
	
	public boolean isUnknown(){
		return state == NodeState.UNKNOWN;
	}
	
	// Add a production rule
	public static void setRule(String key, String[] value){
		rule.put(key, value);
	}
	
	public static String[] getRule(String input){
		return rule.get(input);
	}
	
	//////////////////////////////////////////////////////////////////////////
	/////////////// Interface to field so far ///////////////////
	//////////////////////////////////////////////////////////////////////////
	
	// Output the value of each field
	public void printStatus(){
		for(int i=0; i<depth; i++)
			System.out.print("\t");
		
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" from:" + from + " to:" + to + " state:" + state);
		/ * For debugging
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" next:" + next + " type:" + type + " from:" + from + " to:" + to + " state:" + state);
		*/
	}
	
	// Determine the type of node and store it in type
	public void decideNodeType(){
		if(str.length() > 1){
			type = NodeType.ANDNODE;
		}else if(str.length() == 1){
			char[] ch = str.toCharArray();
			type = ( Character.isUpperCase(ch[0]) ? NodeType.ORNODE :
				   ( Character.isLowerCase(ch[0]) ? NodeType.LEAF : NodeType.DEFAULT ) );
		}
	}
	
	// Determine if all child nodes have been expanded
	public boolean isNodeExpanded(){
		if(isNodeAND()){
			// If it is an AND node, it is judged by comparing the length of the character string with next.
			if(next >= str.length())
				return true;
			else
				return false;
		}else if(isNodeOR()){
			// If it is an OR node, judge by comparing the number of production rules with next
			if(next >= rule.get(str).length)
				return true;
			else
				return false;
		}else{
			// Treat Leaf as expanded
			return true;
		}
	}
	
	// Expand and return one child node
	public Node generateNextNode(int id)
	{
		String st=null;
		
		try{
			if(type == NodeType.ANDNODE){
				st = str.substring(next++, next);
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else if(type == NodeType.ORNODE){
				st = rule.get(str)[next++];
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else{
				throw new RuntimeException();
			}
		}catch(RuntimeException e){
			throw new RuntimeException();
		}
	}
}