package programming;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/*

Networking Basics

* At the core of Java's networking support is the concept of a socket. A socket identifies an endpoint in a network.
Sockets are at the foundation of modern networking because a socket allows a single computer to serve many different
clients at once, as well as to serve many different types of information. This is accomplished through the use of a
port, which is a numbered socket on a particular machine.

* A server port process is said to "listen" to a port until a client connects to it. A server is allowed to accept
multiple clients connected to the same port number, although each session is unique. To manage multiple client connections,
a server process must be multithreaded or have some other means of multiplexing the simultaneous I/O.

* Socket communication takes place via a protocol. Internet Protocol (IP) is a low-level routing protocol that breaks data
into small packets and sends them to an address across a network, which does not guarantee to deliver said packets to the
destination.

* Transmission Control Protocol (TCP) is a higher-level protocol that manages to robustly string together these packets,
sorting and retransmitting them as necessary to reliably transmit data.

* A third protocol, User Datagram Protocol (UDP), sits next to TCP and can be used directly to support fast, connectionless,
unreliable transport of packets.

* Once a connection has been established, a higher-level protocol ensues, which is dependant on which port you are using.
TCP/IP reserves the lower 1,024 ports for specific protocols. Port 21 is for FTP; 23 is for Telnet; 25 is for email; 43 is
for whois; 80 is for HTTP; 119 is for netnews--and the list goes on. It is up to each protocol to determine how a client
should interact with the port.

* For example, HTTP is the protocol that web browsers and servers use to transfer hypertext pages and images. It is a quite
simple protocol for a basic page-browsing web server. Here's how it works. When a client requests a file from an HTTP server,
an action known as a hit, it simply sends the name of the file in a special format to a predefined port and reads back the
contents of the file. The server also responds with a status code to tell the client whether or not the request can be fulfilled
and why.

* A key component of the Internet is the address. Every computer on the Internet has one. An Internet address is a number
that uniquely identifies each computer on the Net. Originally, all Internet addresses consisted of 32-bit values, organized
as four 8-bit values. This address type was specified by IPv4 (Internet Protocol version 4). However, a new addressing
scheme, called IPv6 (Internet Protocol version 6) has come into play. IPv6 uses a 128-bit value to represent an address,
organized into eight 16-bit chunks. Although, there are several reasons for and advantages to IPv6, the main one is that
it supports a much larger address space than does IPv4. Fortunately, when using Java, you won't normally need to worry about
whether IPv4 or IPv6 addresses are used because Java handles the details for you.

* Just as the numbers of an IP address describe a network hierarchy, the name of an Internet address, called its domain name,
describes a machine's location in a name space. For example, www.jeffersonvivanco.com is in the COM top-level domain (reserved
for U.S. commercial sites); it is called jeffersonvivanco, and www identifies the server for web requests. An Internet domain
name is mapped to an IP address by the Domain Naming Service (DNS). This enables users to work with domain names, but the
Internet operates on IP addresses.

* Internet addresses are looked up in a series of hierarchically cached servers. That means that your local computer might
know a particular name-to-IP-address mapping automatically, such as for itself and nearby servers. For other names, it may
ask a local DNS server for IP address information. If that server doesn't have a particular address, it can go to a remote
site and ask for it. This can continue all the way up to the root server. This process might take a long time, so it is
wise to structure your code so that you cache IP address information locally rather than look it up repeatedly.
 */
public class Networkingg {

    public static void main(String[] args){
        inetAddressTest();
    }

    /*

    InetAddress

    * The InetAddress class is used to encapsulate both the numerical IP address and the domain name for that address.
    You interact with this class by using the name of an IP host, which is more convenient and understandable than its
    IP address. The InetAddress class hides the number inside. InetAddress can handle both IPv4 and IPv6 addresses.
     */
    public static void inetAddressTest() {
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println(localAddress);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    /*

    TCP/IP Client Sockets

    * TCP/IP sockets are used to implement reliable, bidirectional, persistent, point-to-point, stream-based connections
    between hosts on the Internet.
    * A socket can be used to connect Java's I/O system to other programs that may reside either on the local machine or
    on any other machine on the Internet.
    * NOTE: As a general rule, applets may only establish socket connections back to the host from which the applet was
    downloaded. This restriction exists because it would be dangerous for applets loaded through a firewall to have access
    to any arbitrary machine.
    * There are two kinds of TCP sockets in Java. One is for servers, and the other is for clients.
    * The ServerSocket class is designed to be a "listener", which waits for clients to connect before doing anything. Thus
    ServerSocket is for servers.
    * The Socket class is for clients. It is designed to connect to server sockets and initiate protocol exchanges. The
    creation of a Socket object implicitly establishes a connection between the client and server.

    TCP/IP Server Sockets

    * The ServerSocket class is used to create servers that listen for either local or remote client programs to connect
    to them on published ports. ServerSockets are quite different from normal Sockets. When you create a ServerSocket, it
    will register itself with the system as having an interest in client connections. The constructors for ServerSocket
    reflect the port number that you want to accept connections on and, optionally, how long you want the queue for said
    port to be. The queue length tells the system how many client connections it can leave pending before it should simply
    refuse connections. The default is 50.
    * ServerSocket has a method called accept(), which is a blocking call that will wait for a client to initiate communications
    and then return with a normal Socket that is then used for communications with the client.
     */
    public static void tcpIpClientSockets(){
        int c;
        // Create a connected to internic.net, port 43
        // Manage this socket with a try-with-resources block
        try (Socket s = new Socket("whois.internic.net", 43)) {
            // Obtain output and input streams
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            // Construct a request string
            String str = "MHProfessional.com\n";
            // Convert to bytes
            byte buf[] = str.getBytes();

            // send request
            out.write(buf);

            // Read and display response
            while ((c = in.read()) != -1){
                System.out.println((char) c);
            }
            s.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*

    URL

    * The Web is a lose collection of higher-level protocols and file formats, all unified in a web browser.
    One of the most important aspects of the Web is that Tim Berners-Lee devised a scalable way to locate all
    of the resources on the Net. Once you can reliably name anything and everything, it becomes a very powerful
    paradigm. The Uniform Resource Locator (URL) does exactly that.

    * The URL provides a reasonably intelligible form to uniquely identify or address information on the Internet.
    URLs are ubiquitous; every browser uses them to identify information on the Web. Within Java's network class
    library, the URL class provides a simple, concise API to access information across the Internet using URLs.

    * All URLs share the same basic format, although some variation is allowed. For example http://www.jeffersonvivanco.com:80.
    A URL specification is based on four components. The first is the protocol to use, separated from the rest of the locator
    by a colon. Common protocols are HTTP, FTP, gopher, and file, although these days almost everything is being done via
    HTTP (in fact, most browsers will proceed correctly if you leave off the "http://" from your URL specification). The
    second component is the host name or IP address of the host to use; this is delimited on the left from the host name
    by a colon and on the right by a slash. (It defaults to port 80, the predefined http port, thus ":80" is redundant.)
    The fourth part is the actual file path. Most HTTP servers will append a file named index.html or index.htm to URLs
    that refer directly to the directory resource.

    URI

    * The URI class encapsulates a Uniform Resource Identifier (URI). URIs are similar to URLs. In fact, URLs constitute
    a subset or URIs. A URI represents a standard way to identify a resource. A URL also describes how to access the resource.

    Cookies

    * The java.net package includes classes and interfaces that help manage cookies and can be used to create a stateful
    (as opposed to stateless) HTTP session. The classes are CookieHandler, CookieManager, and HttpCookie. The interfaces
    are CookiePolicy and CookieStore.

     */
    public static void urlStuff() {
        try {
            URL url = new URL("http://jeffersonvivanco.com");
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    /*

    URLConnection

    * Given a URL object, you can retrieved the data associated with it. To access the actual bits or content information
    of a URL, create a URLConnection object from it, using its openConnection()

    * Once you make a connection to a remote server, you can use URLConnection to inspect the properties of the remote
    object before actually transporting it locally. These attributes are exposed by the HTTP protocol specification and,
    as such, only make sense for URL objects that are using the HTTP protocol.

    HttpURLConnection
    * Java provides a subclass of URLConnection that provides support for HTTP connections. This class is called HttpURLConnection.
    You obtain an HttpURLConnection the same way as URLConnection.
     */
    public static void urlConnectionStuff(){
        try {
            URL url = new URL("http://jeffersonvivanco.com");
            URLConnection urlConnection = url.openConnection();
            // get content-type
            System.out.println("Content-Type: " + urlConnection.getContentType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
    Datagrams

    * TCP/IP-style networking is appropritate for most networking needs. It provides a serialized, predicatable, reliable
    stream of packet data. This is not without its cost, however. TCP includes many complicated algorithms for dealing with
    congestion control on crowded networks, as well as pessimistic expectations about packet loss. This leads to a somewhat
    inefficient way to transport data. Datagrams provide an alternative.

    * Datagrams are bundles of information passed between machines. Once the datagram has been released to its intended
    target, there is no assurance that it will arrive or even that someone will be there to catch it. Likewise, when the
    datagram is received, there is no assurance that it hasn't been damaged in transit or that whoever sent it is still
    there to receive a response.

    * Java implements datagrams on top of the UDP protocol by using two classes: the DatagramPacket object is the data
    container, while the DatagramSocket is the mechanism used to send or receive the DatagramPackets.
    */

    public static int serverPort = 998;
    public static int clientPort = 999;
    public static int buffer_size = 1024;
    public static DatagramSocket ds;
    public static byte buffer[] = new byte[buffer_size];

    public static void datagramExample(String[] args){
        try {
            if (args.length == 1) {
                ds = new DatagramSocket(serverPort);
                theServer();
            } else {
                ds = new DatagramSocket(clientPort);
                theClient();
            }
        } catch (SocketException s){
            s.printStackTrace();
        }
    }

    public static void theServer() {
        try {
            int pos = 0;
            while (true) {
                int c = System.in.read();
                switch (c) {
                    case -1:
                        System.out.println("Server quits!");
                        ds.close();
                        return;
                    case '\r':
                        break;
                    case '\n':
                        ds.send(new DatagramPacket(buffer, pos, InetAddress.getLocalHost(), clientPort));
                        pos = 0;
                        break;
                    default:
                        buffer[pos++] = (byte) c;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void theClient(){
        try {
            while (true){
                DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                ds.receive(p);
                System.out.println(new String(p.getData(), 0, p.getLength()));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
