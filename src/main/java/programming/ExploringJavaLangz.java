package programming;

/*
Exploring Java Lang

Memory Management
* Although Java provides automatic garbage collection, sometimes you will want to know how large the object heap is and how
much of it is left.
* You can use this information, for example, to check your code for efficiency or to approximate how many more objects of
a certain type can be instantiated. To obtain these values, use the totalMemory() and freeMemory() methods.
 */
public class ExploringJavaLangz {

    public static void main(String[] args){
        memoryDemo();
    }

    private static void memoryDemo(){
        Runtime r = Runtime.getRuntime();
        long mem1, mem2;
        int someInts[] = new int[100];
        System.out.println("Total memory: " + r.totalMemory());
        mem1 = r.freeMemory();
        System.out.println("Free memory: " + mem1);

        r.gc(); // request garbage collection

        mem1 = r.freeMemory();
        System.out.println("Free memory after gc: " + mem1);

        int i;
        for (i=0; i<100; i++){
            someInts[i] = i;
        }
        mem2 = r.freeMemory();
        System.out.println("Free memory after allocation: " + mem2);
        System.out.println("Memory used by allocation: " + (mem1 - mem2));

        // discard integers
        someInts = null;
        r.gc();
        mem2 = r.freeMemory();
        System.out.println("Free memory after gc discarded integers: " + mem2);
    }
}
