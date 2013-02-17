package com.viskan.hacontrol;

/*
Copyright 2013 Viskan Distanshandel System AB

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
   limitations under the License.

*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable
{
	ServerSocket serverSocket = null;
	private volatile boolean keepRunning;

	public PortListener()
	{
		keepRunning = true;
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println("Starting Server");
			openSocket();
			System.out.println("Waiting for connections");
			acceptConnections();
			System.out.println("Stopping Server");
			serverSocket.close();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}

	private void acceptConnections() throws Exception
	{
		
		while (keepRunning)
		{
			Socket clientSocket = waitForConnection();
			System.out.println("Accepted " + clientSocket);
			flushConnection(clientSocket);
		}

	}

	private void flushConnection(Socket clientSocket) throws IOException
	{
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		out.println("pong");
		out.flush();
		out.close();
		in.close();
		clientSocket.close();
	}

	private Socket waitForConnection() throws Exception
	{
		Socket clientSocket = serverSocket.accept();
		return clientSocket;
	}

	private void openSocket()
	{
		try 
		{
			serverSocket = new ServerSocket(4444);
		}
		catch (IOException e)
		{
			System.err.println("Could not listen on port: 4444.");
			System.exit(1);
		}
	}

	public void shutDown() throws Exception
	{
		keepRunning = false;
		serverSocket.close();

	}

}
