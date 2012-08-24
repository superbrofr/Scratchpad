/*
 * NodeIterator.h
 *
 *  Created on: 7 Apr 2011
 *      Author: charlie
 */

#ifndef NODEITERATOR_H_
#define NODEITERATOR_H_

#include "DoubleLinkedNode.h"
#include <iostream>

template<class DataType>
class NodeIterator
{
private:
	enum IteratorStates {BEFORE, DATA, END};

	IteratorStates fState;
	bool fFirst;

	typedef DoubleLinkedNode<DataType> Node;

	Node& fLeftmost;
	Node& fRightmost;
	const Node* fCurrent;

	static Node& findBeginNode(Node& aList)
	{
		Node* temp = &aList;
		while(&temp->getPrevious() != &Node::NIL)
			temp = &temp->getPrevious();
		return *temp;
	}

	static Node& findEndNode(Node& aList)
	{
		Node* temp = &aList;
		while(&temp->getNext() != &Node::NIL)
			temp = &temp->getNext();
		return *temp;
	}

public:
	typedef NodeIterator<DataType> Iterator;

	NodeIterator(Node& aList): fLeftmost(findBeginNode(aList)), fRightmost(findEndNode(aList))
	{
		fCurrent = &aList;
		//Initialise state
		if(&fCurrent->getPrevious() == &Node::NIL)
			fState = BEFORE;
		else if(&fCurrent->getNext() == &Node::NIL)
			fState = END;
		else
			fState = DATA;
		fFirst = true;
	}

	const DataType& operator*() const //dereference
	{
		return fCurrent->getValue();
	}

	Iterator& operator++() //prefix increment
	{
		if((fState == BEFORE) && (fFirst == false)){
			fFirst = false;
			fState = DATA;
			return *this;
		}
		if(&fCurrent->getNext() == &Node::NIL){
			fState = END;
		}
		else{
			fFirst = false;
			fState = DATA;
			fCurrent = &fCurrent->getNext();
		}
		return *this;
	}

	Iterator operator++(int) //postfix increment
	{
		if((fState == BEFORE) && (fFirst == false)){
			fFirst = false;
			fState = DATA;
			return *this;
		}
		if(&fCurrent->getNext() == &Node::NIL){
			fState = END;
		}
		else{
			fState = DATA;
			fFirst= false;
			Iterator temp = *this;
			fCurrent = &fCurrent->getNext();
			return temp;
		}
		return *this;
	}

	Iterator& operator--() //prefix decrement
	{
		if(fState == END){
			fState = DATA;
			return *this;
		}
		if(&fCurrent->getPrevious() == &Node::NIL){
			fState = BEFORE;
		}
		else{
			fState = DATA;
			fCurrent = &fCurrent->getPrevious();
		}
		return *this;
	}

	Iterator operator--(int) //postfix decrement
	{
		if(fState == END){
			fState = DATA;
			return *this;
		}
		else if(&fCurrent->getPrevious() == &Node::NIL){
			fState = BEFORE;
		}
		else{
			fState = DATA;
			Iterator temp = *this;
			fCurrent = &fCurrent->getPrevious();
			return temp;
		}
		return *this;
	}

	bool operator==(const Iterator& aOtherIter) const
	{
		return ((fCurrent->getValue() == aOtherIter.fCurrent->getValue()) && (&fRightmost.getValue() == &aOtherIter.fRightmost.getValue()) && (&fLeftmost.getValue() == &aOtherIter.fLeftmost.getValue()) && (fState == aOtherIter.fState));
	}

	bool operator!=(const Iterator& aOtherIter) const
	{
		return !(*this == aOtherIter);
	}

	Iterator begin() const
	{
		return Iterator(fLeftmost);
	}

	Iterator end() const
	{
		return Iterator(fRightmost);
	}
};

#endif /* NODEITERATOR_H_ */
