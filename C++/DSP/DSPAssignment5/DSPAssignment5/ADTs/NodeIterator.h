/*
 * NodeIterator.h
 *
 *  Created on: 8 May 2011
 *      Author: charlie
 */

#ifndef NODEITERATOR_H_
#define NODEITERATOR_H_
template<class DataType>
class NodeIterator
{
private:
	enum IteratorStates { BEFORE, DATA , END };

	IteratorStates fState;

	typedef DoubleLinkedNode<DataType> Node;

	const Node* fLeftmost;
	const Node* fRightmost;
	const Node* fCurrent;

public:
	typedef NodeIterator<DataType> Iterator;

	NodeIterator(const Node& aList)
	{
		//Set leftmost
		fLeftmost = &aList;
		if(fLeftmost != &Node::NIL){
  		while (&fLeftmost->getPrevious() != &Node::NIL)
  			fLeftmost = &fLeftmost->getPrevious();
		}

		//Set rightmost
		fRightmost = &aList;
		if(fRightmost != &Node::NIL){
  		while (&fRightmost->getNext() != &Node::NIL)
  			fRightmost = &fRightmost->getNext();
		}

	    //start iterator at leftmost element
	    fCurrent = fLeftmost;

	    //set state
      fState = fCurrent != &Node::NIL ? DATA : END;
	  }

	  const DataType& operator*() const
	  {
		  return fCurrent->getValue();
	  }

	  Iterator& operator++()
	  {
		  if(fState == BEFORE){
			  fCurrent = fLeftmost;
			  if(fCurrent == &Node::NIL)
				  fState = END;
			  else
				  fState = DATA;
		  }
		  else if(fState == DATA){
			  fCurrent = &fCurrent->getNext();
			  if(fCurrent == &Node::NIL)
				  fState = END;
		  }
	    return *this;
	  }

	  Iterator operator++(int)
	  {
		  Iterator returnIter = *this;
		  ++(*this);

		  return returnIter;
	  }

	  Iterator& operator--()
	  {
		  if(fState == END){
			  fCurrent = fRightmost;
			  if(fCurrent == &Node::NIL)
				  fState = BEFORE;
			  else
				  fState = DATA;
		  }
		  else if(fState == DATA){
			  fCurrent = &fCurrent->getPrevious();
			  if(fCurrent == &Node::NIL)
				  fState = BEFORE;
		  }

	    return *this;
	  }

	  NodeIterator operator--(int)
	  {
		  Iterator returnIter = *this;
		  --(*this);

		  return returnIter;
	  }

	  bool operator==(const Iterator& aOtherIter) const
	  {
		  return (fCurrent == aOtherIter.fCurrent) && (fLeftmost == aOtherIter.fLeftmost) && (fRightmost == aOtherIter.fRightmost) && (fState == aOtherIter.fState);
	  }

	  bool operator!=(const Iterator& aOtherIter) const
	  {
		  return !(*this == aOtherIter);
	  }

	  Iterator begin()
	  {
		  Iterator returnIter = *this;
		  returnIter.fCurrent = &Node::NIL;
		  returnIter.fState = BEFORE;

		  return returnIter;
	  }

	  Iterator end()
	  {
		  Iterator returnIter = *this;
		  returnIter.fCurrent = &Node::NIL;
		  returnIter.fState = END;

		  return returnIter;
	  }
};

#endif /* NODEITERATOR_H_ */
