package Geyang;

import java.util.*;

public abstract class Node {
	protected int label;
	protected Vector<Node> neighbors;
	protected Message msg;
	//protected HashMap<Node, Integer> frequency;
	//protected HashMap<Node, HashMap<Node, Integer>> matrix;
	protected ContactHis matrix[][];
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
		System.out.println("Node " + this.label +" disconnect with node " + newNode.label);
		System.out.println("Connection ID of each node: ");
		for(int i =0; i<nodesGroup.size() ;i++){
			GoodNode goodNode = nodesGroup.elementAt(i);
			System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		}
	}
	
	public void getConnect(Node newNode){
		if(newNode == null) return;
		if(this == newNode) return;
		if(this.neighbors.contains(newNode)) return;
		if(newNode.connectID == this.connectID){
			System.out.println("Warning! Node : " + label + " has already connected to the node : "
					+ newNode.label);
		}
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
		System.out.println("Node " + this.label +" connect with node " + newNode.label);
		System.out.println("Connection ID of each node: ");
		for(int i =0; i<nodesGroup.size() ;i++){
			GoodNode goodNode = nodesGroup.elementAt(i);
			System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		}
	}
	
	public boolean getConnect(Node newNode, boolean isOneGroup){
		if(newNode == null) return false;
		if(this.neighbors.contains(newNode)) return false;
		if(isOneGroup && newNode.connectID == this.connectID){
			return false;
		}
		if(this == newNode) return false;
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
		System.out.println("Node " + this.label +" connect with node " + newNode.label);
		System.out.println("Connection ID of each node: ");
		for(int i =0; i<nodesGroup.size() ;i++){
			GoodNode goodNode = nodesGroup.elementAt(i);
			System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		}
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
		if(isConnect(desNode)&&sendMatrix(desNode)){
			System.out.println("Node : " + label + ", sending message : " + this.msg 
					+ ", to Node : " + desNode.label);
			
		}else{
			return false;
		}
		return true;
	}
	
	public boolean sendMatrix(Node desNode){
		if(desNode == null){
			return false;
		}
		if(!desNode.reseiveMatrix(this.matrix, this)) return false;
		return true;
	}
	
	private boolean reseiveMatrix(ContactHis[][] matrix, Node neighber){
		if(matrix.length == 0) return false;
		if(matrix[0].length == 0) return false;
		//System.out.println("Node : " + label + " has reseived matrix from node : " + neighber.label 
		//		+ ", compare begins!! ");
		if(!compare(matrix)) return false;
		return true;
	}
	
	private boolean compare(ContactHis[][] neiMatrix){
		if(matrix.length == 0) return false;
		if(matrix[0].length == 0) return false;
		for(int i =0;i<matrix.length; i++){
			for(int j =0;j<matrix[i].length;j++){
				if(this.matrix[i][j].getTimes() < neiMatrix[i][j].getTimes()) {
					//System.out.println("Node : " + label + " has changed the matrix " + i + " " + j
					//		+ " from " + matrix[i][j].getTimes() + " to " + neiMatrix[i][j].getTimes());
					this.matrix[i][j].setTimes(neiMatrix[i][j].getTimes());
				}	
			}
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
	
	protected void randomMatrix(ContactHis[][] matrix){
		for(int i =0;i<matrix.length;i++){
			for(int j =0;j<matrix[0].length;j++){
				int temp = (int) (Math.random() * 50);
				matrix[i][j] = new ContactHis(temp);
			}
		}
	}
	
	public void forwardMsg(Message msg, Node nextNode){
		
	}
}
