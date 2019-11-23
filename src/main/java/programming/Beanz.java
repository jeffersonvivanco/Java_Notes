package programming;

import models.Person;

import java.beans.*;

/*
Java Beans
* Beans are important because they allow you to build complex systems from software components. These components may be
provided by you or supplied by one or more different vendors. Java Beans defines an architecture that specifies how these
building blocks can operate together.
* To better understand the value of Beans, consider the following.
  * Hardware designers have a wide variety of components that can be integrated together to construct a system. Resistors,
  capacitors, and inductors are examples of simple building blocks. Integrated circuits provide more advanced functionality.
  All of these different parts can be reused.
  * It is not necessary of possible to rebuild these capabilities each time a new system is needed. Also, the same pieces
  can be used in different types of circuits. This is possible because the behavior of these components is understood and
  documented.
* The software industry has also been seeking the benefits of reusability and interoperability of a component-based approach.
To realize these benefits, a component architecture is needed that allows programs to be assembled from software building
blocks, perhaps provided by different vendors. It must also be possible for a designer to select a component, understand its
capabilities, and incorporate it into an application. When a new version of a component becomes available, it should be easy
to incorporate this functionality into existing code. Fortunately, Java Beans provides just such an architecture.

What is a Java Bean?
* A Java Bean is a software component that has been designed to be reusable in a variety of different environments.
* There is no restriction on the capibility of a Bean. It may perform a simple function, such as obtaining an inventory
value, or a complex function, such as forecasting the performance of a stock portfolio.
* A Bean may be visible to an end user.
  * One example of this is a button on a graphical user interface.
* A Bean may also be invisible to a user.
  * Software to decode a stream of multimedia information in real time is an example of this type of building block.
* Finally, a Bean may be designed to work autonomously on a user's workstation or to work in cooperation with a set of other
distributed components.
  * Software to generate a pie chart from a set of data points is an example of a Bean that can execute locally.

Advantages of Java Beans
The following list enumerates some of the benefits that Java Bean technology provides for a component developer:
  * A Bean obtains all the benefits of Java "write-once, run-anywhere" paradigm.
  * The properties, events, and methods of a Bean that are exposed to another application can be controlled.
  * Auxiliary software can be provided to help configure a Bean. This software is only needed when the design-time parameters
  for that component are being set. It does not need to be included in the run-time environment.
  * The state of a Bean can be saved in persistent storage and restored at a later time.
  * A Bean may register to receive events from other objects and generate events that are sent to other objects.

Introspection
At the core of Java Beans is introspection. This is the process of analyzing a Bean to determine its capabilities.
* This is an essential feature of the Java Beans API because it allows another application, such as a design tool, to
obtain information about a component. Without introspection, the Java Beans technology could not operate.
* There are two ways in which the developer of a Bean can indicate which of its properties, events, and methods should be
exposed.
  * With the first method, simple naming conventions are used. These allow the introspection mechanisms to infer information
  about a Bean.
  * In the second way, an additional class that extends the BeanInfo interface is provided that explicitly supplies this
  information.

Design Patterns for Properties
* A property is a subset of a Bean's state.
* The values assigned to the properties determine the behavior and appearance of that component.
* A property is set through a setter method.
* A property is obtained by a getter method.
* There are two types of properties: simple and indexed
  * Simple Properties - A simple property has a single value. It can be identified by the following design patterns, where
  N is the name of the property and T is its type:
  public T getN()
  public void setN(T arg)
    * A read/write property has both of these methods to access its values.
    * A read-only property has only a get method.
    * A write-only property has only a set method.
    * For a boolean property, a method of the form isPropertyName() can also be used as an accessor.
  * Indexed Properties - An indexed property consists of multiple values. It can be identified by the following design patterns,
  where N is the name of the property and T is its type:
  public T getN(int index);
  public void setN(int index, T value);
  public T[] getN();
  public void setN(T values[]);

Design Patterns for Events
* Beans can generate events and send them to other objects. These can be identified by the following design patterns, where
T is the type of the event:
public void addTListener(TListener eventListener); // can be used to multicast an event, which means that more than one listener
// can register for the event notification.
public void addTListener(TListener eventListener) throws TooManyListenersException; // unicasts an event, which means the number
// of listeners is restricted to one
public void removeTListener(TListener eventListener);

Methods and Design Patterns
* Design patterns are not used for naming nonproperty methods. The introspection mechanism finds all of the public methods of
a Bean. Protected and private methods are not presented.

Using the BeanInfo Interface
* As the preceding discussion shows, design patterns implicitly determine what information is available to the user of a Bean.
The BeanInfo interface enables you to explicitly control what information is available. The BeanInfo interface defines several
methods, including these:
  * PropertyDescriptor[] getPropertyDescriptors()
  * EventSetDescriptor[] getEventSetDescriptors()
  * MethodDescriptor[] getMethodDescriptors()
* By implementing these methods, a developer can designate exactly what is presented to a user, bypassing introspection based
on design patterns.
* When creating a class that implements BeanInfo, you must call that class bnameBeanInfo, where bname is the name of the Bean.

Bound and Constrained Properties
* A Bean that has a bound property generates an event when the property is changed. The event is of type PropertyChangeEvent and
is sent to objects that previously registered an interest in receiving such notifications. A class that handles this event
must implement the PropertyChangeListener interface.
* A Bean that has a constrained property generates an event when an attempt is made to change its value. It also generates
an event of type PropertyChangeEvent. It too is sent to objects that previously registered an interest in receiving such
notifications. However, those other objects have the ability to veto the proposed change by throwing a PropertyVetoException.
This capability allows a Bean to operate differently according to its run-time environment. A class that handles this event
must implement the VeteoableChangeListener interface.

Persistence
* Persistence is the ability to save the current state of a Bean, including the values of a Bean's properties and instance
variables, to nonvolatile storage and to retrieve them at a later time. The object serialization capabilities provided by
the Java class libraries are used to provide persistence for Beans.
* The easiest way to serialize a Bean is to have it implement the java.io.Serializable interface, which is simply a marker
interface. Implementing java.io.Serializable makes serialization automatic. Your Bean need take no other action. Automatic
serialization can also be inherited.
* When using automatic serialization, you can selectively prevent a field from being saved through the use of the transient
keyword. Thus, data members of a Bean specified as transient will not be serialized.
* If a Bean does not implement java.io.Serializable, you must provide serialization yourself, such as by implementing
java.io.Externalizable. Otherwise, containers cannot save the configuration of your component.

Customizers
* A Bean developer can provide a customizer that helps another developer configure the Bean. A customizer can provide a
step-by-step guide through the process that must be followed to use the component in a specific context. Online documentation
can also be provided. A Bean developer has great flexibility to develop a customizer that can differentiate his or her product
in the marketplace.

The Java Beans API
* Introspector - this class provides several static methods that support introspection. Of most interest is getBeanInfo().
This method returns a BeanInfo object that can be used to obtain information about the Bean.
* PropertyDescriptor - this class describes the characteristics of a Bean property. It supports several methods that manage
and describe properties.
* EventSetDescriptor - this class represents a Bean event. It supports several methods that obtain the methods that a Bean
uses to add or remove event listeners, and to otherwise manage events.
* MethodDescriptor - this class represents a Bean method.

 */
public class Beanz {

    public static void main(String[] args){
        Person person = new Person("Jeff", 23);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(person.getClass());
            PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor p : propertyDescriptors){
                System.out.println(p.getName());
            }
        } catch (IntrospectionException e){
            System.out.println("Error with introspection");
        }
    }
}