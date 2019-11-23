package programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
Annotations (Metadata)
* Since JDK 5, Java has supported a feature that enables you to embed supplemental information into a source file. This
information, called an annotation, does not change the actions of a program. Thus, an annotation leaves the semantics of
a program unchanged.
* However, this information can be used by various tools during both development and deployment. For example, an annotation
might be processed by a source-code generator.

Specifying a Retention Policy
* A retention policy determines at what point an annotation is discarded. Java defines 3 such policies, which are encapsulated
within the java.lang.annotation.RetentionPolicy enumeration. They are SOURCE, CLASS, and RUNTIME.
  * An annotation with a retention policy of SOURCE is retained only in the source file and is discarded during compilation.
  * An annotation with a retention policy of CLASS is stored in the .class file during compilation. However, it is not
  available through the JVM during run time.
  * An annotation with a retention policy of RUNTIME is stored in the .class file during compilation and is available through
  the JVM during run time. Thus, RUNTIME retention offers the greatest annotation persistence.
  * Note: An annotation on a local variable declaration is not retained in the .class file.
* If no retention policy is specified for an annotation, then the default policy of CLASS is used.

Obtaining Annotations at Run Time by use of Reflection
* Although annotations are designed mostly for use by other development or deployment tools, if they specify a retention policy of
RUNTIME, they can be queried at run time by any Java program through the use of reflection. Reflection is the feature that enables
information about a class to be obtained at run time.

The Built-In annotations
* @Documented - is a marker interface that tells a tool that an annotation is to be documented. It is designed to be used
only as an annotation to an annotation declaration.
* @Target - specifies the types of items to which an annotation can be applied. It is designed to be used only as an annotation
to another annotation.
  * Takes one argument, which is an array of constants of the ElementType enumeration.
  * ex: @Target( { ElementType.FIELD, ElementType.LOCAL_VARIABLE } )
  * If you don't use @Target, then, except for type parameters, the annotation can be used on any declaration. For this
  reason, it is often a good idea to explicitly specify the target or targets so as to clearly indicate the intended uses
  of an annotation.
* @Inherited - is a marker annotation that can be used only on another annotation declaration. Furthermore, it affects only
annotations that will be used on class declarations.
  * causes the annotation for a superclass to be inherited by a subclass
  * therefore, when a request for a specific annotation is made to the subclass, if that annotation is not present in the
  subclass, then its superclass is checked. If that annotation is present in the superclass, and if it is annotated with
  @Inherited, then the annotation will be returned.
* @Override - is a marker annotation that can be used only on methods. A method annotated with @Override must override a
method from a superclass. If it doesn't, a compile-time error will result. It is used to ensure that a superclass method
is actually overridden, and not simply overloaded.

Type Annotations
===> Look at AnnotationzType

Some restrictions
* First, no annotation can inherit another.
* Second, all methods declared by an annotation must be without parameters. Further more, they must return one of the
following.
  * A primitive type, such as int or double
  * An object type String or Class
  * An enum type
  * Another annotation type
  * An array of one of the preceding types
* Annotations cannot be generic, in other words, they cannot take type parameters.
* Finally, annotation methods cannot specify a throws clause.
 */
public class Annotationz {

    private static final Logger logger = LoggerFactory.getLogger(Annotationz.class);

    public static void main(String[] args){
        getAnnotations();
    }

    @MyAnno(str = "computer", val = 1)
    public static String myMeth(){
        return "comp";
    }

    // obtaining annotations at run time by use of reflection
    public static void getAnnotations(){
        try {
            Class<?> a = Class.forName("programming.Annotationz");

            // getting annotations
            logger.info("Declared Annotations:");
            // annotation will be null if retention policy of annotation is not RUNTIME
            /*
            * ".class" gets class object, this construct is called a class literal
            * You can use this type of expression whenever a Class object of a known class is needed
            * In general you can obtain a class literal for classes, interfaces, primitive types, and arrays
            */
            MyAnno annotation = a.getDeclaredMethod("myMeth").getDeclaredAnnotation(MyAnno.class);
            // printing value at annotation at method
            logger.info("str: {}, val: {}", annotation.str(), annotation.val());
        } catch (ClassNotFoundException | NoSuchMethodException e){
            logger.error("No class found: {}", e.getMessage());
        }
    }

}

/*
example of annotation
* "@" tells the compiler that an annotation type is being declared
* all annotations consist solely of method declarations. However, you don't provide bodies for these methods. Instead, Java
implements these methods. Moreover, the methods act much like fields, as you will see.
* Any type of declaration can have an annotation associated with it. For example, classes, methods, fields, parameters, and
enum constants can be annotated. Even an annotation can be annotated. In all cases, an annotation precedes the rest of the
declaration.
* You can give annotation members default values that will be used if no value is specified when the annotation is applied.
A default value is specified by adding a default clause to a member's declaration.
 */
@Repeatable(MyRepeatedAnnos.class)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
    String str() default "testing";
    int val() default 1;
}

/*
example of marker annotation
* A marker annotation is a special kind of annotation that contains no members. Its sole purpose is to mark an item. Thus,
its presence as an annotation is sufficient. The best way to determine if a marker annotation is present is to use the method
isAnnotationPresent().
 */
@Retention(RetentionPolicy.RUNTIME)
@interface  MyMarker {}

/*
example of single-member annotations
* A single-member annotation contains only one member. It works like a normal annotation except that it allows a shorthand
form of specifying the value of the member. When only one member is present, you can simply specify the value for that member
when the annotation is applied--you don't need to specify the name of the member. However, in order to use this shorthand,
the name of the member must be value.
* You can use the single-value syntax when applying an annotation that has other members, but those other members must all
have default values.
 */
@interface MySingle {
    int value();
}

/*
example of a repeating annotations
* Another new JDK 8 annotation feature enables an annotation to be repeated on the same element.
* For an annotation to be repeatable, it must be annotated with @Repeatable annotation. Its "value" field specifies the
container type for the repeatable annotation. The container is specified as an annotation for which the "value" field is
an array of the repeatable annotation type. Thus, to create a repeatable annotation, you must create a container annotation
and then specify that annotation type as an argument to the @Repeatable annotation.
* To access the repeated annotations using a method such as getAnnotation(), you will use the container annotation, not
the repeatable annotation.
* Below is container annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyRepeatedAnnos {
    MyAnno[] value();
}
