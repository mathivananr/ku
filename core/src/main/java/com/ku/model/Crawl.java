package com.ku.model;
import java.util.NoSuchElementException;

public class Crawl<E> {

	private Node<E> head;
	private Node<E> tail;
	private int size;

	public Crawl() {
		size = 0;
	}

	/**
	 * returns the size of the linked list
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * return whether the list is empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * adds element at the starting of the linked list
	 * 
	 * @param element
	 */
	public void addFirst(E element) {
		Node<E> tmp = new Node<E>(element, head, null);
		if (head != null) {
			head.prev = tmp;
		}
		head = tmp;
		if (tail == null) {
			tail = tmp;
		}
		size++;
		//System.out.println("adding: " + element);
	}

	/**
	 * adds element at the end of the linked list
	 * 
	 * @param element
	 */
	public void addLast(E element) {

		Node<E> tmp = new Node<E>(element, null, tail);
		if (tail != null) {
			tail.next = tmp;
		}
		tail = tmp;
		if (head == null) {
			head = tmp;
		}
		size++;
		System.out.println("adding: " + element);
	}

	/**
	 * this method walks forward through the linked list
	 */
	public void iterateForward() {

		System.out.println("iterating forward..");
		Node<E> tmp = head;
		while (tmp != null) {
			//System.out.println(tmp.element);
			tmp = tmp.next;
		}
	}

	/**
	 * this method walks backward through the linked list
	 */
	public void iterateBackward() {

		System.out.println("iterating backword..");
		Node<E> tmp = tail;
		while (tmp != null) {
			//System.out.println(tmp.element);
			tmp = tmp.prev;
		}
	}

	/**
	 * this method removes element from the start of the linked list
	 * 
	 * @return
	 */
	public E removeFirst() {
		if (size == 0)
			throw new NoSuchElementException();
		Node<E> tmp = head;
		head = head.next;
		head.prev = null;
		size--;
		//System.out.println("deleted: " + tmp.element);
		return (E) tmp.element;
	}

	/**
	 * this method removes element from the end of the linked list
	 * 
	 * @return
	 */
	public E removeLast() {
		if (size == 0)
			throw new NoSuchElementException();
		Node<E> tmp = tail;
		tail = tail.prev;
		tail.next = null;
		size--;
		//System.out.println("deleted: " + tmp.element);
		return (E) tmp.element;
	}

	public Node<E> getHead() {
		return head;
	}

	public void setHead(Node<E> head) {
		this.head = head;
	}

	public Node<E> getTail() {
		return tail;
	}

	public void setTail(Node<E> tail) {
		this.tail = tail;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static void main(String a[]) {

		Crawl<String> dll = new Crawl<String>();
		dll.addLast("10");
		dll.addLast("34");
		dll.addLast("56");
		dll.addLast("364");
		dll.iterateForward();
		dll.removeFirst();
		dll.removeLast();
		dll.iterateBackward();
	}
}