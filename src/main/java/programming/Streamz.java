package programming;

/*
There are three interfaces that are quite important to the stream classes: Closeable, Flushable, AutoCloseable
* AutoCloseable provides support for the try-with-resources statement, which automates the process of closing a resource.
  Only objects of classes that implement AutoCloseable can be managed by try-with-resources. The AutoCloseable interface
  defines only the close() method:
    * This method closes the invoking object, releasing any resources that it may hold. It is called automatically at the
    end of a try-with-resources statement, thus eliminating the need to explicitly call close().
* Because AutoCloseable interface is implemented by all of the I/O classes that open a stream, all such streams can be
  automatically closed by a try-with-resources statement.
* Automatically closing a stream ensures that it is properly closed when it is no longer needed, thus preventing memory
  leaks and other problems.
* Objects of a class that implements Flushable can force buffered output to be written to the stream to which the object
  is attached. It defines the flush method.
  * Flushing a stream typically causes buffered output to be physically written to the underlying device.

Two ways to close a stream - In general, a stream must be closed when it is no longer needed. Failure to do so can lead
to memory leaks and resource starvation.
1. Explicitly call close() - close() is typically called within a finally block.
2. Automate the process by using the try-with-resources statement in an enhanced form of try. When the try block ends,
   the resource is automatically released.

The Stream Classes - Java's stream-based I/O is built upon 4 abstract classes: InputStream, OutputStream, Reader, Writer
* InputStream and OutputStream are designed for byte streams
* Reader and Writer are designed for character streams

The Byte Streams - The byte stream classes provide a rich environment for handling byte oriented I/O. A byte stream can
be used with any type of object, including binary data.
* InputStream - is an abstract class that defines Java'a model of streaming byte input. Used to read bytes.
  * close() - Closes the input source. Further read attempts will generate an IOException.
  * read() - Returns an integer representation of the next available byte of input. -1 is returned when the end of the
  file is encountered.
  * read(byte buffer[]) - Attempts to read up to buffer.length bytes into buffer and returns the actual number of bytes
  that were successfully read. -1 is returned when the end of the file is encountered.
  * read(byte buffer[], int offset, int numBytes) - Attempts to read up to numBytes into buffer starting at buffer[offset],
  returning the number of bytes successfully read. -1 is returned when the end of the file is encountered.
  * reset() - resets the input pointer to the previously set mark.
* OutputStream - is an abstract class that defines streaming byte output. Used to write bytes.
  * close() - Closes the output stream. Further write attempts will generate an IOException
  * flush() - Finalizes the output state so that any buffers are cleared. That is, it flushes the output buffers.
  * write(int b) - Writes a single byte to an output stream. Note that the parameter is an int, which allows you to call
  write() with an expression without having to cast it back to byte.

ByteArrayInputStream - is an implementation of an input stream that uses a byte array as the source.
* The close() method has no effect on a ByteArrayInputStream. Therefore, it is not necessary to call close(), but doing so
is not an error.

ByteArrayOutputStream - is an implementation of an output stream that uses a byte array as the destination.
* close acts similar to ByteArrayInputStream
* has two constructors
  * ByteArrayOutputStream() - a buffer of 32 bytes is created
  * ByteArrayOutputStream(int numBytes) - a buffer is created with a size equal to that specified by numBytes
* The buffer size will be increased automatically, if needed.
 */
public class Streamz {
}
