/*
 * DoubleLinkedNode.h (PS5)
 *
 */

#ifndef DOUBLELINKEDNODE_H_
#define DOUBLELINKEDNODE_H_

template<class DataType>
class DoubleLinkedNode
{
public:
	typedef DoubleLinkedNode<DataType> Node;

private:
	const DataType* fValue;
	Node* fNext;
	Node* fPrevious;

	DoubleLinkedNode() : fValue((const DataType*)0)
	{
		fNext = &NIL;
		fPrevious = &NIL;
	}

public:

	static Node NIL;

	DoubleLinkedNode( const DataType& aValue ) : fValue(&aValue)
	{
		fNext = &NIL;
		fPrevious = &NIL;
	}

	void insertNode( Node& aNode )
	{
		// if there is a next element, then link it in
		if ( fNext != &NIL )
		{
			aNode.fNext = fNext;
			fNext->fPrevious = &aNode;
		}

		fNext = &aNode;

		// set this aNode's fPrevious
		aNode.fPrevious = this;
	}

	void prependNode( Node& aNode )
	{
		// if there is a previous element, then link it in
		if ( fPrevious != &NIL )												// 2
		{
			aNode.fPrevious = fPrevious;										// 2
			fPrevious->fNext = &aNode;											// 2
		}

		fPrevious = &aNode;														// 2

		// set this aNode's fPrevious
		aNode.fNext = this;														// 2/10
	}

	void dropNode()
	{
		// if there is a next element, then link fPrevious
		if ( fNext != &NIL )
		{
			fNext->fPrevious = fPrevious;
		}

		// if there is a previous element, then link fNext
		if ( fPrevious != &NIL )
		{
			fPrevious->fNext = fNext;
		}
	}

	const DataType& getValue() const
	{
		return *fValue;
	}

	Node& getNext() const
	{
		return *fNext;
	}

	Node& getPrevious() const
	{
		return *fPrevious;
	}
};

template<class DataType>
DoubleLinkedNode<DataType> DoubleLinkedNode<DataType>::NIL;

// Total: 10

#endif /* DOUBLELINKEDNODE_H_ */
