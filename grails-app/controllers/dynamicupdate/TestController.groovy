package dynamicupdate

import grails.gorm.transactions.Transactional

class TestController {

	def index() {
		Child child = Child.get(1)
		render child.properties.sort()
	}

	@Transactional
	def create_child() {
		Child child = new Child(property1: '1p', property2: '2p', property3: '3p', attribute1: '1a', attribute2: '2a')
		child.save()
	}


	@Transactional
	def update_ap() {
		Child child = Child.get(1)
		child.attribute1 = 'aa11'
		child.property1 = 'prop11'
		child.save()
	}

	@Transactional
	def update_p() {
		Child child = Child.get(1)
		child.property1 = 'prop11'
		child.save()
	}

	@Transactional
	def update_a() {
		Child child = Child.get(1)
		child.attribute1 = 'atrib11'
		child.save()
	}

	@Transactional
	def create_parent() {
		Parent parent = new Parent(property1: '1p', property2: '2p', property3: '3p')
		parent.save()
	}

	@Transactional
	def update_parent() {
		Parent parent = Parent.get(2)
		parent.property1 = 'prop11'
		parent.save()
	}
}
