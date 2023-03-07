# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer


### true positive :
../../commons-collections/src/test/java/org/apache/commons/collections4/AbstractObjectTest.java:111:	EqualsNull:	Avoid using equals() to compare against null

Si obj est effectivement null, renvoie une erreur inutilement puisque le assertFalse signalera déjà le problème, utiliser un == assurerai un contr

 ```
 public void testEqualsNull() {
    final Object obj = makeObject();
    assertFalse(obj.equals(null)); // make sure this doesn't throw NPE either
}
```



### false positive :
../../commons-collections/src/main/java/org/apache/commons/collections4/CollectionUtils.java:627:	CompareObjectsWithEquals:	Use equals() to compare object references.

Les 2 élements comparés sont des int, l'utilisation d'un ==/!= au lieu d'un equals est donc adapté.

```
if (helper.cardinalityA.size() != helper.cardinalityB.size()) {
  return false;
}
```

        
  
