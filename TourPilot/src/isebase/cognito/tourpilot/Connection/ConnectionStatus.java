package isebase.cognito.tourpilot.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;

public class ConnectionStatus {
	
	public static final int InitState = -1;
	public static final int Connection = 0;
	public static final int Invitation = 1;
	public static final int DateSycnhronizing = 2;
	public static final int SendHelloRequest = 3;
	public static final int CompareCkeckSums = 4;
	public static final int ParseRecievedData = 5;
	public static final int CloseConnection = 6;
	
	public int CurrentState;

	public boolean isFinished;
	
	public Socket socket;
	
	public OutputStream OS;
	public InputStream IS;
	
	public ServerCommandParser serverCommandParser;
	
	public SynchronizationHandler localSynchHandler;
	public SynchronizationHandler UISynchHandler;
		
	private String currMessage = "" ;	

	public String getMessage() {
		return currMessage;
	}

	public void setMessage(String currMessage) {
		this.currMessage = currMessage;
	}

	public boolean lastExecuteOK = true;
	
	public String[] dataFromServer;
		
	public ConnectionStatus(SynchronizationHandler synchHandler){
		CurrentState = InitState;
		isFinished = false;
		dataFromServer = new String[0];
		UISynchHandler = synchHandler;
		localSynchHandler = new SynchronizationHandler() {			
			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
				
			}
			
			@Override
			public void onItemSynchronized(String text) {	
				currMessage = text;
			}
		};
		serverCommandParser = new ServerCommandParser(localSynchHandler);
	}
	
	public void nextState(){
		CurrentState++;
		currMessage = "";
	}
	
	public void closeConnection(){
		try {
			if(socket != null && !socket.isClosed()){
				socket.close();
				OS.close();
				IS.close();	
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
