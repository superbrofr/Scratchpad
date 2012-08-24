/*
 * List.h
 *
 */

#ifndef LIST_H_
#define LIST_H_

#include "DoubleLinkedNode.h"
#include "NodeIterator.h"
#include <stdexcept>

#include <iostream>

template<class T>
class List
{
private:
  typedef DoubleLinkedNode<T>	Value;
  typedef DoubleLinkedNode<T>*	ListImpl;

  ListImpl fTop;								// leftmost element
  ListImpl fLast;								// rightmost element
  int fCount;									// number of nodes

public:
  typedef NodeIterator<T> ListIterator;

  List()										// List constructor
  {
	  fTop = &Value::NIL;																// 2
	  fLast = &Value::NIL;																// 2
	  fCount = 0;																		// 1/5
  }

  List( const List& aOtherList )				// List copy constructor
  {
	  fTop = &Value::NIL;																// 2
	  fLast = &Value::NIL;																// 2
	  fCount = 0;																		// 1

	  for ( ListIterator iter = aOtherList.begin(); iter != aOtherList.end(); iter++ )	// 5
		  add( *iter );																	// 3/13
  }

  ~List()										// List destructor
  {
	  while ( fTop != &Value::NIL )														// 4
		  dropFirst();																	// 1/5
  }

  List& operator=( const List& aOtherList )		// List assignment operator
  {
	  while ( fTop != &Value::NIL )														// 4
		  dropFirst();																	// 1

	  for ( ListIterator iter = aOtherList.begin(); iter != aOtherList.end(); iter++ )	// 5
		  add( *iter );																	// 3

	  return *this;																		// 2/15
  }

  bool isEmpty() const							// empty list predicate
  {
	  return fCount == 0;																// 2
  }

  int size() const								// get number of nodes
  {
	  return fCount;																	// 2
  }

  void add( const T& aElement )					// add element at end
  {
	  ListImpl pNewNode = new Value( aElement );										// 3
	  fCount++;																			// 1

	  if ( fTop == &Value::NIL )														// 2
	  {
		  fTop = pNewNode;		// set first node										// 1
		  fLast = pNewNode;		// make new node last									// 1
	  }
	  else
	  {
		  fLast->insertNode( *pNewNode );	// append new node							// 3
		  fLast = pNewNode;					// make new node last						// 1/12
	  }
  }

  void addFirst( const T& aElement )			// add element at top
  {
	  ListImpl pNewNode = new Value( aElement );										// 3
	  fCount++;																			// 1

	  if ( fTop == &Value::NIL )														// 2
	  {
		  fTop = pNewNode;		// set first node										// 1
		  fLast = pNewNode;		// make new node last									// 1
	  }
	  else
	  {
		  fTop->prependNode( *pNewNode );	// prepend new node							// 3
		  fTop = pNewNode;					// make new node first						// 1/12
	  }
  }

  bool drop( const T& aElement )				// delete matching element
  {
	  for ( ListImpl p = fTop; p != &Value::NIL; p = &p->getNext() )					// 3
	  {
		  if ( p->getValue() == aElement )												// 2
		  {
			  ListImpl lPrevious = &p->getPrevious();									// 2
			  ListImpl lNext = &p->getNext();											// 2

			  // check fTop && fLast (this will set fTop and fLast to &NIL eventually)
			  if ( fTop == p )															// 1
				  fTop = lNext;															// 1

			  if ( fLast == p )															// 1
				  fLast = lPrevious;													// 1

			  p->dropNode();															// 1
			  delete p;																	// 1
			  fCount--;																	// 1

			  return true;																// 1
			}
		}

		return false;																	// 1/18
  }

  void dropFirst()								// delete first node
  {
	  if ( fTop != &Value::NIL )
	  {
		ListImpl lTop = &fTop->getNext();												// 3

		fTop->dropNode();																// 1
		delete fTop;		// free memory now											// 1
		fCount--;																		// 1
		fTop = lTop;																	// 1

		if ( fTop == &Value::NIL )														// 2
		{
			fLast = &Value::NIL;														// 1/10
		}
	  }
  }

  void dropLast()								// delete last node
  {
	  if ( fLast != &Value::NIL )
	  {
		ListImpl lLast = &fLast->getPrevious();											// 3

		fLast->dropNode();																// 1
		delete fLast;		// free memory now											// 1
		fCount--;																		// 1
		fLast = lLast;																	// 1

		if ( fLast == &Value::NIL )														// 2
		{
			fTop = &Value::NIL;															// 1/10
		}
	  }
  }

  const T& operator[]( int aIndex ) const		// List indexer
  {
	  if ( (aIndex >= 0) && (aIndex < size()) )											// 3
	  {
		  ListImpl p = fTop;															// 1

		  while ( aIndex-- )															// 2
		  {
			  p = &p->getNext();														// 2
		  }

		  return p->getValue();															// 2
	  }
	  else
	  {
		  throw std::out_of_range( "aIndex out of bounds!" );							// 2/12
	  }
  }

  ListIterator begin() const					// List iterator
  {
	  return ListIterator( *fTop );														// 2
  }

  ListIterator end() const						// List iterator
  {
	  return ListIterator( *fTop ).end();												// 3
  }
};

// Total: 5+13+5+15+2+2+12+12+18+10+10+12+2+3 = 121

#endif /* LIST_H_ */
