package programming;

/*
 Introducing Servlets

 * Servlets are small programs that execute on the server side of a web connection. Just as applets dynamically extend
 the functionality of a web browser, servlets dynamically extend the functionality of a web server.

 Background

 * In order to understand the advantages of servlets, you must have a basic understanding of how web browsers and servers
 cooperate to provide content to a user. Consider a request for a static web page. A user enters a Uniform Resource Locator (URL)
 into a browser. The browser generates an HTTP request to the appropriate web server. The web server maps this request to
 a specific file. That file is returned  in an HTTP response to the browser. The HTTP header in the response indicates the
 type of the content. The Multipurpose Internet Mail Extensions (MIME) are used for this purpose. For example, ordinary ASCII
 text has a MIME type of text/plain. The Hypertext Markup Language (HTML) source code of a web page has a MIME type of text/html.

 * Now consider dynamic content. Assume that an online store uses a database to store information about its business. This
 would include items for sale, prices, availability, orders, and so forth. It wishes to make this information accessible to
 customers via web pages. The contents of those web pages must be dynamically generated to reflect the latest information in the
 database.

 * In the early days of the Web, a server could dynamically construct a page by creating a separate process to handle each
 client request. The process would open connections to one or more databases in order to obtain the necessary information.
 It communicated with the web server via an interface known as the Common Gateway Interface (CGI). CGI allowed the seperate
 process to read data from the HTTP request and write data to the HTTP response. A variety of different languages were used
 to build CGI programs. These included C, C++, and Pearl.

 * However, CGI suffered serious performance problems. It was expensive in terms of processor and memory resources to create
 a separate process for each client request. It was also expensive to open and close database connections for each client
 request. In addition, the CGI programs were not platform-independent. Therefore, other techniques were introduced. Among
 these are servlets.

 * Servlets offer several advantages in comparison with CGI. First, performance is significantly better. Servlets execute
 within the address space of a web server. It is not necessary to create a separate process to handle each client request.
 Second, servlets are platform-independent because they are written in Java. Third, the Java security manager on the server
 enforces a set of restrictions to protect the resources on a server machine. Finally, the full functionality of the Java
 class libraries is available to a servlet. It can communicate with applets, databases, or other software via the sockets
 and RMI mechanisms that you have seen already.

 The Life Cycle of a Servlet

 * Three methods are central to the life cycle of a servlet. These are init(), service(), and destroy(). They are implemented
 by every servlet and are invoked at specific times by the server. Let us consider a typical user scenario to understand when
 these methods are called.

 * First, assume that a user enters a Uniform Resource Locator (URL) to a web browser. The browser then generates an HTTP
 request for this URL. This request is then sent to the appropriate server.

 * Second, this HTTP request is received by the web server. The server maps this request to a particular servlet. The servlet
 is dynamically retrieved and loaded into the address space of the server.

 * Third, the server invokes the init() method of the servlet. This method is invoked only when the servlet is first loaded
 into memory. It is possible to pass initialization parameters to the servlet so it may configure itself.

 * Fourth, the server invokes the service() method of the servlet. This method is called to process the HTTP request. You
 will see that it is possible for the servlet to read data that has been provided in the HTTP request. It may also formulate
 an HTTP response for the client. The servlet remains in the server's address space and is available to process any other
 HTTP requests received from clients. The service() method is called for each HTTP request.

 * Finally, the server may decide to unload the servlet from its memory. The algorithms by which this determination is made
 are specific to each server. The server calls the destroy() method to relinquish any resources such as file handles that are
 allocated for the servlet. Important data may be saved to a persistent store. The memory allocated for the servlet and its
 objects can then be garbage collected.

 Servlet Development Options

 * To create servlets, you will need access to a servlet container/server. Two popular ones are Glassfish and Tomcat.
 */
public class Servletz {

    public static void main(String[] args){

    }
}
