package oop.ex6.main;

/*
 * This class represents a parameter with the data: name, is Initialized, is final,
 * and the type. This allows us to create a list of variable objects, and get all the required data
 * for us
 */
class VarWrapper {
    /* the name of the parameter */
    String name;
    /* if the variable is initialized */
    boolean isInitialized;
    /* if the variable final or not */
    boolean isFinal;
    /* the type of the variable */
    String type;


    /* Constructor */
    VarWrapper(String type, String name, boolean isFinal, boolean isInitialized) {
        this.name = name;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.type = type;
    }
}
