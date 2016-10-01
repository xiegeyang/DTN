package Geyang;

import java.util.*;

public class NodesManger {
	private int size;
	private Vector<GoodNode_Runnable> nodesGroup;
	private int lines;
	
	private void setSize(int size){
		if(size <= 0){
			System.out.println("Error : Nodes Group size cannot less than 1, We will make the size with 1");
			this.size = 1;
			return;
		}
		this.size = size;
	}
	
	private void setLines(int lines){
		if(size <= 0){
			System.out.println("Error : Nodes Group lines cannot less than 1, We will make the lines with 0");
			this.lines = 0;
			return;
		}
		this.lines = lines;
	}
	
	public Vector<GoodNode_Runnable> getNodesGroup() {
		return nodesGroup;
	}

	public NodesManger(int size){
		setSize(size);
		nodesGroup = new Vector<>();
		for(int i =0; i<size; i++){
			GoodNode_Runnable node = new GoodNode_Runnable(i, this.nodesGroup);
		}
		Random ran = new Random();
		this.lines = ran.nextInt(size * 2 - size) + size;
		makeConnection(lines);
		
	}
	
	public NodesManger(int size, int lines){
		setSize(size);
		setLines(lines);
		nodesGroup = new Vector<>();
		for(int i =0; i<size; i++){
			GoodNode_Runnable node = new GoodNode_Runnable(i, this.nodesGroup);
		}
		makeConnection(lines);
	}
	
	public NodesManger(int size, int lines, boolean isOneGroup){
		setSize(size);
		setLines(lines);
		nodesGroup = new Vector<>();
		for(int i =0; i<size; i++){
			GoodNode_Runnable node = new GoodNode_Runnable(i, this.nodesGroup);
		}
		makeConnection(lines, isOneGroup);
	}
	
	
	
	public void makeConnection(int lines){
		System.out.println("The number of lines are : "+lines);
		Random ran = new Random();
		for(int i =0;i<lines;i++){
			GoodNode_Runnable nodeA = nodesGroup.elementAt(ran.nextInt(size)); 
			GoodNode_Runnable nodeB = nodesGroup.elementAt(ran.nextInt(size)); 
			if(nodeA != nodeB){
				nodeA.getConnect(nodeB);
			}
		}
	}
	
	public void makeConnection(int lines, boolean isOneGroup){
		System.out.println("The number of lines are : "+lines);
		Random ran = new Random();
		for(int i =0;i<size-1;i++){
			GoodNode_Runnable nodeA = nodesGroup.elementAt(ran.nextInt(size)); 
			GoodNode_Runnable nodeB = nodesGroup.elementAt(ran.nextInt(size)); 
			if(!nodeA.getConnect(nodeB, isOneGroup)) i--;
		}
		for(int i =0 ; i<lines-size+1;i++){
			GoodNode_Runnable nodeA = nodesGroup.elementAt(ran.nextInt(size)); 
			GoodNode_Runnable nodeB = nodesGroup.elementAt(ran.nextInt(size)); 
			nodeA.getConnect(nodeB);
		}
	}
	
	public void test(){
		for(int i =0; i<nodesGroup.size() ;i++){
			GoodNode goodNode = nodesGroup.elementAt(i);
			System.out.println(goodNode.label + " : ");
			for(int j =0; j<goodNode.neighbors.size();j++){
				System.out.print(goodNode.neighbors.elementAt(j).label +" ");
			}
			System.out.println();
		}
		System.out.println("Connection ID of each node: ");
		for(int i =0; i<nodesGroup.size() ;i++){
			GoodNode goodNode = nodesGroup.elementAt(i);
			System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		/*for(int i =0;i<vec.size();i++){
			GoodNode goodNode = vec.elementAt(i);
			for(int j=0;j<goodNode.nodesGroup.size();j++){
				System.out.println(goodNode.nodesGroup.elementAt(j).label+"");
			}
		}*/
		
		for(int i =0;i<nodesGroup.size();i++ ){
			new Thread(nodesGroup.elementAt(i)).start();
		}
	}
	
}
