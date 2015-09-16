package com.ku.model;
/**
 * this class keeps track of each element information
 * 
 * @author java2novice
 * 
 */
public class Node<E> {
	public E element;
	public Node<E> next;
	public Node<E> prev;

	public Node(E element, Node<E> next, Node<E> prev) {
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public Node<E> getNext() {
		return next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}

	public Node<E> getPrev() {
		return prev;
	}

	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}
}