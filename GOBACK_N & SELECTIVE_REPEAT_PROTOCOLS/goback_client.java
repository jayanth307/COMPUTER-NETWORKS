import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

class goback_client{

public static void main(String args[]) throws IOException{
InetAddress address = InetAddress.getByName("Localhost");
System.out.println(address);
Socket s1 = new Socket(address,500);
BufferedInputStream in = new BufferedInputStream(s1.getInputStream());
DataOutputStream out = new DataOutputStream(s1.getOutputStream());
Scanner sc = new Scanner(System.in);

System.out.println("...client...");
System.out.println("Connect");
System.out.println("Enter the num of frames to be request to server");
int c = sc.nextInt();

out.write(c);
out.flush();

System.out.println("Enter type of trans. Error =1 : No Error=0");
int choice = sc.nextInt();
out.write(choice);

int i=0,j=0,check =0;
if(choice==0){
	for(j=0;j<c;j++){
		i = in.read();
		System.out.println("receiver frame number => " +i);
		System.out.println("Sending acknowlwdgement for frame number=> "+i);
		out.write(i);
		out.flush();
	}
out.flush();
}
else{
	for(j=0;j<c;j++){
		i = in.read();
		if(i==check){
			System.out.println("i => " +i+ "check => " +check);
			System.out.println("received frame number => "+i);
			System.out.println("sending acknowledgement for frame num => " +i);
			out.write(i);
			check++;
		}
		else{
			j--;
			System.out.println("Discarded frame no => " +i);
			System.out.println("Sending negative ack ");
			out.write(-1);
		}
		out.flush();
	}
}
in.close();
out.close();
System.out.println("Quiting");
}
}