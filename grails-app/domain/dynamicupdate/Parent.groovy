package dynamicupdate

//import org.hibernate.annotations.DynamicUpdate
//
//@DynamicUpdate
class Parent {
	String property1
	String property2
	String property3

	static constraints = {
	}

	static mapping = {
		dynamicUpdate true
		tablePerHierarchy false
	}
}
