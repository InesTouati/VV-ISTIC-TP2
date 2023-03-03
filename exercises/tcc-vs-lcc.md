# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

Lorsqu'il n'y absolument aucune relation, ou que chaque pair est connectée.

Exemple :
```
3 pairs, aucune relation
public class MyClass {
   int a, int b, int c;
   
   public void method1() {
      return a;
   }
   
   public void method2() {
      return b
   }
   public void method3() {
      return c
   }
} ```

```3 pairs, relation entre chaque pair 
public class MyClass {
   int a, int b, int c, int x;
   
   public void method1() {
      return a+x;
   }
   
   public void method2() {
      return b+x;
   }
   public void method3() {
      return c+x;
   }
} ```
