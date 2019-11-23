package programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*
Reflection
* Reflection is the ability of software to analyze itself. This is provided by the java.lang.reflect package and elements
in Class. Reflection is an important capability, especially when using components called Java Beans.
* It allows you to analyze a software component and describe its capabilities dynamically, at run time rather than at
compile time. For example, by using reflection, you can determine what methods, constructors, and fields a class supports.

Invoking Methods
* Reflection provides a means for invoking methods on a class.
* Typically, this would only be necessary if it is not possible to cast an instance of the class to the desired type in
non-reflective code.
* Methods are invoked with Method.invoke()
* The first argument is the object instance on which this particular method is to be invoked. (If the method is static,
the first argument should be null.) Subsequent arguments are the method's parameters.

 */
public class Reflectionz {
    private static final Logger logger = LoggerFactory.getLogger(Reflectionz.class);

    public static void main(String[] args){
        try {
            Class<?> person = Class.forName("models.Person"); // getting class object

            logger.info("Constructors:");
            for (Constructor c: person.getConstructors()){
                logger.info(c.getName());
            }
            logger.info("Declared fields:"); // only fields declared by the class
            for (Field f : person.getDeclaredFields()){
                // getModifiers() returns an int containing flags that describe which modifiers apply for this element
                logger.info("field: {}, modifier: {}",f.getName(), Modifier.toString(f.getModifiers()));
            }
            logger.info("Declared methods:"); // only methods declared by the class. Methods inherited from superclasses
            // such as Object are not included
            for (Method m: person.getDeclaredMethods()){
                logger.info(m.getName());
            }

        } catch (ClassNotFoundException e){
            logger.error("Error in Reflectionz. Message: {}", e.getMessage());
        }
    }
}
