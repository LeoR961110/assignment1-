
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class HttpServer extends Thread { 

	private final int HTTP_PORT;
	
	private ServerSocket listen = null;
	
	private boolean running = true;
	
	private Hashtable knownRequests = new Hashtable(); 
   
	public HttpServer(int port) {
		this.HTTP_PORT = port;
		
		if (this.HTTP_PORT <= 0) {
			System.err.println("HttpServer not started, as -Port is " +
             this.HTTP_PORT);
			return;
		}
	
		System.err.println("Creating new HttpServer on Port = " +
            this.HTTP_PORT);
		
		setDaemon(true);
		
		start();
	}

	public void registerRequest(String urlPath, String data) {
		System.out.println("Registering urlPath: " +
				urlPath + "=" + data);
		knownRequests.put(urlPath.trim(), data);
	}
	public void removeRequest(String urlPath) {
		knownRequests.remove(urlPath.trim());
	}
	//实现父类Thread的run方法
	public void run() {
		try {
			
			this.listen = new ServerSocket(HTTP_PORT);
		
			while (running) {
	
				Socket accept = this.listen.accept();
				System.out.println("New incoming request on Port=" +
               HTTP_PORT + " ...");
				if (!running) {
					System.out.println("Closing http server Port=" +
               HTTP_PORT + ".");
					break;
				}
			
				HandleRequest hh = new HandleRequest
				(accept, knownRequests);
			}
		
		} catch (java.net.BindException e) {
			System.out.println(	"HTTP server problem, Port "	+
					listen.getInetAddress().toString()	+
					  ":" + HTTP_PORT+" is not available: "	+
					   e.toString());
		} catch (java.net.SocketException e) {
			System.out.println(	"Socket "	+ listen.getInetAddress()
              .toString()+ ":" + HTTP_PORT+ " closed successfully: "
         					+ e.toString());
		} catch (IOException e) {
			System.out.println("HTTP server problem on port : " +
             HTTP_PORT + ": " + e.toString());
		}
	
		if (this.listen != null) {
			try {
				
				this.listen.close();
			} catch (java.io.IOException e) {
				System.out.println("this.listen.close()" + e.toString());
			}
			this.listen = null;
		}
	}
	
	public void shutdown()
	{
		System.out.println("Entering shutdown");
	
		running = false;
		try {
			
			if (this.listen != null) {
				this.listen.close();
				this.listen = null;
			}
		} catch (java.io.IOException e) {
			System.out.println("shutdown problem: " + e.toString());
		}
	}
    
	public static void main(String[] args) {
		
		HttpServer server = new HttpServer(3333);
		
             from MiniHttpServer!”*/
		server.registerRequest("/Hello",
				"Hello World from  MiniHttpServer!");
		
		System.out.println("Press any key to exit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
