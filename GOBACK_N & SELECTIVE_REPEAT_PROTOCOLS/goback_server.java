import java.io.*;
import java.net.*;
import java.util.*;

class goback_server{
public static void main(String args[])throws IOException{

System.out.println("...Server...");
System.out.println("...Waiting...");
InetAddress address = InetAddress.getByName("Localhost");
ServerSocket ss = new ServerSocket(500);

Socket s1 = new Socket();
s1 = ss.accept();
BufferedInputStream in = new BufferedInputStream(s1.getInputStream());
DataOutputStream out = new DataOutputStream(s1.getOutputStream());

System.out.println("received request for sending frames");
int n = in.read();

boolean[] array = new boolean[n];

int pc = in.read();
System.out.println("...Sending...");

if(pc==0){
for(int i=0;i<n;i++){
System.out.println("Sending frame => "+i);
out.write(i);
out.flush();
System.out.println("..Waiting for acknowledge..");
try{
Thread.sleep(5000);
}
catch (Exception e){}
int a = in.read();
System.out.println("received acknowledgment for frame => " +i+ " as "+a);
}
out.flush();
}
else{
for(int i=0;i<n;i++){
if(i==3) {
System.out.println("Sending frame number => " +i);
}
else{
System.out.println("sending frame no => " +i);
out.write(i);
out.flush();
System.out.println("Waiting for acknologment ");


try {
Thread.sleep(7000);
}
catch(Exception e){}
int a = in.read();
if(a!=255){
System.out.println("received ack for frame num =>"+i+" as "+a);
array[i]=true;
}
}
}
for(int a=0;a<n;a++){
if(array[a]==false){
System.out.println("Resending frame => " +a);
out.write(a);
out.flush();
System.out.println("waiting for ack ");
try {
	Thread.sleep(5000);
	}
catch(Exception e){}

int b = in.read();
System.out.println("receiving ack for frame num => "+a+" as "+b);
array[a]=true;
}
}
out.flush();
}
in.close();
System.out.println("Quiting");
}
}



