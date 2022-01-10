import java.lang.System;
import java.net.*; 
import java.io.*;
import java.text.*;
import java.util.Random;
import java.util.*;

class selective_cient {
	static Socket connection;

	public static void main(String a[]) throws SocketException {
		try {
			int v[] = new int[10]; 
			int n = 0;
			Random rands = new Random();
			int rand = 0; 
 			 
			InetAddress addr = InetAddress.getByName("Localhost");
			System.out.println(addr);
			connection = new Socket(addr, 8011);
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			DataInputStream in = new DataInputStream(
					connection.getInputStream());
			int p = in.read();
			System.out.println("NO OF FRAME IS :" + p);

			for (int i = 0; i < p; i++) {
				v[i] = in.read();
				System.out.println(v[i]);
				//g[i] = v[i];
			}
			rand = rands.nextInt(p);			
			v[rand] = -1;
			for (int i = 0; i < p; i++)
			 {
			System.out.println("RECEIVED FRAME IS: " + v[i]);

			}
			for (int i = 0; i < p; i++)
				if (v[i] == -1) {
					System.out.println("REQUEST TO RETRANSMIT FROM PACKET NO "+ (i+1) + " again!!");
					n = i;
					out.write(n);
					out.flush();
				}
			System.out.println();
				v[n] = in.read();
				System.out.println("RECEIVED FRAME IS: " + v[n]);
			
			

			System.out.println("QUITTING");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
