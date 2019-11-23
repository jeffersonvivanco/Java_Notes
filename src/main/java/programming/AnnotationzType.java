package programming;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/*
Type Annotations
* With the advent of JDK 8, annotations can also be specified in most cases in which a type is used. This expanded aspect
of annotations is called type annotation.
* For example, you can annotate the return type of a method, the type of "this" within a method, a cast, array levels, an
inherited class, and a "throws" clause.
* You can also annotate generic types, including generic type parameter bounds and generic type arguments.
* Type annotations are important because they enable tools to perform additional checks on code to help prevent errors. Understand
that, as a general rule, javac will not perform these checks itself. A separate tool is used for this purpose, although
such a tool might operate as a compiler plug-in.
 */
public class AnnotationzType {

    public static void main(String[] args){
        myMeth(10);
    }

    public static void myMeth(int i){
        // Use type annotation on a type argument
        TypeAnnoDemo<@TypeAnno Integer> ob = new TypeAnnoDemo<>();

        // Use type annotation with new
        @TypeAnno TypeAnnoDemo<Integer> ob2 = new @TypeAnno TypeAnnoDemo<>();

        Object x = 10;
        Integer y;

        // Use type annotation on a cast
        y = (@TypeAnno Integer) x;
    }
}

// Use an annotation on a type parameter
class TypeAnnoDemo<@What(description = "Generic Data Type") T> {

    // Use a type annotation on a constructor
    public @TypeAnno TypeAnnoDemo() {}

    // Annotate the type (in this case String), not the field.
    @TypeAnno String str;

    // This annotates the field test
    @EmptyOk String test;

    /*
    * You can also annotate the type of "this" (called the receiver). As you know, "this" is an implicit argument to all
    instance methods and it refers to the invoking object. Beginning with JDK 8, you can explicitly declare "this" as the
    first parameter to a method. In this declaration, the type of "this" must be the type of its class.
    * It is important to understand that it is not necessary to declare "this" unless you are annotating it.
     */
    public int f(@TypeAnno TypeAnnoDemo<T> this, int x){
        return 10;
    }

    // Annotate the return type
    // Note: You cannot annotate a return type of void
    public @TypeAnno Integer f2(int j, int k){
        return j + k;
    }

    // Annotate the method declaration
    public @Recommended Integer f3(String str){
        return str.length() / 2;
    }

    // Use a type annotation with a throws clause
    public void f4() throws @TypeAnno NullPointerException {
        // ..
    }

    // Annotate array levels
    String @TypeAnno [] @TypeAnno [] w;

    // Annotate the array element type, in this case Integer
    @TypeAnno Integer[] vec;

}

// Use type annotation with inheritance clause
class SomeClass extends @TypeAnno TypeAnnoDemo<Boolean> {}

// note: A type annotation must include ElementType.TYPE_USE as a target

// A marker annotation that can be applied to a type
@Target(ElementType.TYPE_USE)
@interface TypeAnno { }

// An annotation that can be applied to a type parameter
@Target(ElementType.TYPE_PARAMETER)
@interface What {
    String description();
}

// An annotation that can be applied to a field declaration
@Target(ElementType.FIELD)
@interface EmptyOk { }

// An annotation that can be applied to a method declaration
@Target(ElementType.METHOD)
@interface Recommended { }
