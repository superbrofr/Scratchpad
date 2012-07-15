/*
 * DoubleLinkedNodeIterator.h
 *
 */

#ifndef DOUBLELINKEDNODEITERATOR_H_
#define DOUBLELINKEDNODEITERATOR_H_

#include "DoubleLinkedNode.h"

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

	NodeIterator( const Node& aList )
	{
		fLeftmost = &aList;

		if ( fLeftmost != &Node::NIL )
		{
			while ( &fLeftmost->getPrevious() != &Node::NIL )
				fLeftmost = &fLeftmost->getPrevious();
		}

		fRightmost = & aList;

		if ( fRightmost != &Node::NIL )
		{
			while ( &fRightmost->getNext() != &Node::NIL )
				fRightmost = &fRightmost->getNext();
		}

	    // set current to leftmost element;
	    fCurrent = &aList;

	    // set state
	    fState = fCurrent != &Node::NIL ? DATA : END;
	  }


	  const DataType& operator*() const
	  {
		  return fCurrent->getValue();
	  }

	  Iterator& operator++()										// prefix increment
	  {
		  switch ( fState )
		  {
	      case BEFORE:
			  fCurrent = fLeftmost;
			  if ( fCurrent == &Node::NIL )
				  fState = END;
			  else
				  fState = DATA;
			  break;
	      case DATA:
	    	  fCurrent = &fCurrent->getNext();
	    	  if ( fCurrent == &Node::NIL )
	    		  fState = END;
	      default: ;
	    }

	    return *this;
	  }

	  Iterator operator++(int)										// postfix increment
	  {
		  Iterator Result = *this;

		  ++(*this);

		  return Result;
	  }

	  Iterator& operator--()										// prefix decrement
	  {
		  switch ( fState )
		  {
	      case END:
	    	  fCurrent = fRightmost;
	    	  if ( fCurrent == &Node::NIL )
				  fState = BEFORE;
			  else
				  fState = DATA;
	    	  break;
	      case DATA:
	    	  fCurrent = &fCurrent->getPrevious();
	    	  if ( fCurrent == &Node::NIL )
	    		  fState = BEFORE;
	      default: ;
	    }

	    return *this;
	  }

	  NodeIterator operator--(int)									// postfix decrement
	  {
		  Iterator Result = *this;

		  --(*this);

		  return Result;
	  }

	  bool operator==( const Iterator& aOtherIter ) const
	  {
		  return (fCurrent == aOtherIter.fCurrent) &&
		         (fLeftmost == aOtherIter.fLeftmost) &&
		         (fRightmost == aOtherIter.fRightmost) &&
	             (fState == aOtherIter.fState);
	  }

	  bool operator!=( const Iterator& aOtherIter ) const
	  {
		  return !(*this == aOtherIter);
	  }

	  Iterator begin()
	  {
		  Iterator Result = *this;
		  Result.fCurrent = &Node::NIL;
		  Result.fState = BEFORE;

		  return Result;
	  }

	  Iterator end()
	  {
		  Iterator Result = *this;
		  Result.fCurrent = &Node::NIL;
		  Result.fState = END;

		  return Result;
	  }
};


#endif /* DOUBLELINKEDNODEITERATOR_H_ */
