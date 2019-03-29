package dynamicupdate

//import org.hibernate.annotations.DynamicUpdate
//
//@DynamicUpdate
class Child extends Parent {
    String attribute1
    String attribute2

    static constraints = {
    }

    static mapping = {
        dynamicUpdate true
        tablePerHierarchy false
    }
}
