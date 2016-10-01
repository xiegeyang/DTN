package Geyang;

import java.util.*;

public abstract class Node {
	protected int label;
	protected Vector<Node> neighbors;
	protected Message msg;
	//protected HashMap<Node, Integer> frequency;
	//protected HashMap<Node, HashMap<Node, Integer>> matrix;
	protected static Vector<GoodNode_Runnable> nodesGroup;
	protected int connectID;
	
	
	public void setMsg(Message msg){
		if(msg == null) return;
		encriptMsg(msg);
	}
	
	public Node(){
		neighbors = new Vector<>();
		//frequency = new HashMap<>();
		//matrix = new HashMap<>();
		nodesGroup = new Vector<>();
	}
	
	public Node(int label){
		this.label = label;
		this.connectID = label;
		neighbors = new Vector<>();
		//frequency = new HashMap<>();
		//matrix = new HashMap<>();
		//nodesGroup = new Vector<>();
	}
	
	public void disConnect(Node newNode){
		if(newNode == null) return;
		if(this == newNode) return;
		if(newNode.connectID != this.connectID){
			return;
		}
		if(newNode.neighbors.contains(this)) newNode.neighbors.remove(this);
		if(this.neighbors.contains(newNode)) this.neighbors.remove(newNode);
		syncConnectID(this, this.label);
		syncConnectID(newNode, newNode.label);
	}
	
	public void getConnect(Node newNode){
		if(newNode == null) return;
		if(this == newNode) return;
		if(newNode.connectID == this.connectID){
			System.out.println("Warning! Node : " + label + " has already connected to the node : "
					+ newNode.label);
		}
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
	}
	
	public boolean getConnect(Node newNode, boolean isOneGroup){
		if(newNode == null) return false;
		if(newNode.connectID == this.connectID){
			return false;
		}
		if(this == newNode) return false;
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
		return true;
	}
	
	private void syncConnectID(Node origiNode, Node newNode){
		if(origiNode == null || newNode == null) return;
		if(origiNode.connectID == newNode.connectID) return;
		origiNode.connectID = newNode.connectID;
		for(int i = 0;i<origiNode.neighbors.size();i++){
			syncConnectID(origiNode.neighbors.elementAt(i), newNode);
		}
		return;
	}
	
	private void syncConnectID(Node origiNode, int connectedID){
		if(origiNode == null || connectedID < 0) return;
		if(origiNode.connectID == connectedID) return;
		origiNode.connectID = connectedID;
		for(int i = 0;i<origiNode.neighbors.size();i++){
			syncConnectID(origiNode.neighbors.elementAt(i), connectedID);
		}
		return;
	}
	
	public boolean sendMessage(Node desNode){
		if(desNode == null){
			return false;
		}
		if(isConnect(desNode)){
			System.out.println("Node : " + label + ", sending message : " + this.msg 
					+ ", to Node : " + desNode.label);
		}else{
			return false;
		}
		return true;
	}
	
	public boolean isConnect(Node des){
		if(des == null) return false;
		if(des.connectID !=this.connectID){
			System.out.println("Error! Node : " + label + " does not connect to the node : " + des.label);
			return false;
		}
		return true;
	}
	
	private boolean encriptMsg(Message msg){
		if(msg == null ) return false;
		this.msg = msg;
		return true;
	}
	
	public void forwardMsg(Message msg, Node nextNode){
		
	}
}
