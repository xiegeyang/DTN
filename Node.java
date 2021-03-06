package Geyang;


import java.io.IOException;
import java.security.*;
import java.util.*;

public abstract class Node {
	protected int label;
	protected Vector<Node> neighbors;
	protected Message msg;
	//protected HashMap<Node, Integer> frequency;
	//protected HashMap<Node, HashMap<Node, Integer>> matrix;
	protected HistoryObj matrix[][];
	protected static Vector<Node> nodesGroup;
	protected int connectID;
	protected KeyPair pair;
	protected PrivateKey privateKey;
	protected PublicKey publicKey;
	protected HashMap<Node, PublicKey> keyMap = new HashMap<>();
	
	public PublicKey getPub() {
		return publicKey;
	}
	
	public HashMap<Node, PublicKey> getKeyMap(){
		return this.keyMap;
	}
	
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
		if(this.keyMap.containsKey(newNode)) this.keyMap.remove(newNode);
		if(newNode.keyMap.containsKey(this)) newNode.keyMap.remove(this);
		if(newNode.neighbors.contains(this)) newNode.neighbors.remove(this);
		if(this.neighbors.contains(newNode)) this.neighbors.remove(newNode);
		syncConnectID(this, this.label);
		syncConnectID(newNode, newNode.label);
		System.out.println("Node " + this.label +" disconnect with node " + newNode.label);
		//System.out.println("Connection ID of each node: ");
		//for(int i =0; i<nodesGroup.size() ;i++){
		//	Node goodNode = nodesGroup.elementAt(i);
		//	System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		//}
	}
	
	/*public void getConnect(Node newNode){
		if(newNode == null) return;
		if(this == newNode) return;
		if(this.neighbors.contains(newNode)) return;
		if(newNode.connectID == this.connectID){
			System.out.println("Warning! Node : " + label + " has already connected to the node : "
					+ newNode.label);
			return;
		}
		if(!keyMap.containsKey(newNode)){
			keyMap.put(newNode, newNode.getPub());
			System.out.println("Node " + this.label + " got key from node :" + newNode.label + " , key is"  +keyMap.get(newNode).toString());
		}
		if(!newNode.keyMap.containsKey(this)){
			newNode.keyMap.put(this, this.getPub());
			System.out.println("Node " + newNode.label +" got key from node :" + this.label + " , key is"  +newNode.keyMap.get(this).toString());
		}
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
		System.out.println("Node " + this.label +" connect with node " + newNode.label);
		//System.out.println("Connection ID of each node: ");
		//for(int i =0; i<nodesGroup.size() ;i++){
		//	Node goodNode = nodesGroup.elementAt(i);
		//	System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		//}
	}*/
	
	public boolean getConnect(Node newNode, boolean isOneGroup){
		if(newNode == null) return false;
		if(this.neighbors.contains(newNode)) return false;
		if(isOneGroup && newNode.connectID == this.connectID){
			return false;
		}
		if(this == newNode) return false;
		System.out.println("Node " + this.label +" connect with node " + newNode.label);
		if(!this.neighbors.contains(newNode)) this.neighbors.add(newNode);
		if(!newNode.neighbors.contains(this)) newNode.neighbors.add(this);
		syncConnectID(this, newNode);
		if(!keyMap.containsKey(newNode)){
			keyMap.put(newNode, newNode.getPub());
			System.out.println("Node " + this.label + " got key from node :" + newNode.label + " , key is"  +keyMap.get(newNode).toString());
		}
		if(!newNode.keyMap.containsKey(this)){
			newNode.keyMap.put(this, this.getPub());
			System.out.println("Node " + newNode.label +" got key from node " + this.label + " , key is "  +newNode.keyMap.get(this).toString());
		}
		//System.out.println("Connection ID of each node: ");
		//for(int i =0; i<nodesGroup.size() ;i++){
		//	Node goodNode = nodesGroup.elementAt(i);
		//	System.out.println("Node " + nodesGroup.elementAt(i).label+ " : " + goodNode.connectID );
		//}
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
	
	private boolean reseiveMatrix(HistoryObj[][] matrix, Node neighber){
		if(matrix.length == 0) return false;
		if(matrix[0].length == 0) return false;
		System.out.println("Node : " + label + " has reseived matrix from node : " + neighber.label 
				+ ", compare begins!! ");
		if(!compare(matrix)) return false;
		return true;
	}
	
	private boolean compare(HistoryObj[][] neiMatrix){
		if(matrix.length == 0) return false;
		if(matrix[0].length == 0) return false;
		for(int i =0;i<matrix.length; i++){
			for(int j =0;j<matrix[i].length;j++){
				//HistoryObj ch =  verifyHistoryObj()
				if(this.matrix[i][j].getContactHis().getTimes() < neiMatrix[i][j].getContactHis().getTimes()) {
					System.out.println("Node : " + label + " has changed the matrix " + i + " " + j
							+ " from " + matrix[i][j].getContactHis().getTimes() + " to " + neiMatrix[i][j].getContactHis().getTimes());
					this.matrix[i][j].getContactHis().setTimes(neiMatrix[i][j].getContactHis().getTimes());
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
	
	protected void randomMatrix(HistoryObj[][] matrix){
		for(int i =0;i<matrix.length;i++){
			for(int j =0;j<matrix[0].length;j++){
				Random ran = new Random();
				matrix[i][j] = new HistoryObj(ran.nextInt(50));
			}
		}
	}
	
	protected void keyGeneration() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		    kpg.initialize(1024);
		    KeyPair keyPair = kpg.genKeyPair();
			
			this.pair = keyPair;
			this.privateKey = keyPair.getPrivate();
			this.publicKey = keyPair.getPublic();
	        } catch (Exception e) {
	            System.err.println("Caught exception " + e.toString());
	        }
	}
	/*protected SignedObject signHistoryObj(HistoryObj history, PrivateKey key) {
		if (history==null || key ==null) {
			System.err.println("signHistoryObj error: history or private key can't be null");
			return null;
		}
		SignedObject so = null;
		try{
			Signature signingEngine = Signature.getInstance("SHA-1/RSA");
			
			so = new SignedObject(history, key, signingEngine);
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
		return so;
		
	}
	
	public HistoryObj verifyHistoryObj(SignedObject so, PublicKey pKey) {
		if (so ==null || pKey == null) {
			System.err.println("verifyHistoryObj error: so or public key can't be null");
			return null;
		}
		HistoryObj history = null;
		try {
			Signature verificationEngine =
				     Signature.getInstance("SHA-1/RSA");
				 if (so.verify(pKey, verificationEngine))
				     try {
				    	 history = (HistoryObj) so.getObject();
				     } catch (java.lang.ClassNotFoundException e) {
				    	 System.err.println("Caught exception " + e.toString());
				     };
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
		return history;
	}*/
	
	public void sign(HistoryObj historyObj){
		try {
			Signature sign = Signature.getInstance("SHA1withRSA");
			try {
				sign.initSign(this.privateKey);
				Serializer serializer = Serializer.getInstance();
				try {
					historyObj.setData(serializer.serialize(historyObj.getContactHis()));
					sign.update(historyObj.getData());
					historyObj.setSignatureA(sign.sign());
					
				} catch (SignatureException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean verify(Node neignberNode, HistoryObj historyObj){
		boolean isVerify = false;
		try {
			Signature sign = Signature.getInstance("SHA1withRSA");
			sign.initVerify(neignberNode.publicKey);
			sign.update(historyObj.getData());
			isVerify = sign.verify(historyObj.getSignatureA());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isVerify;
	}
}
