/*
 * DynamicQueue.h
 */

#ifndef DYNAMICQUEUE_H_
#define DYNAMICQUEUE_H_

#include "List.h"
#include <stdexcept>

template<class T>
class DynamicQueue
{
private:
	List<T> fElements;

public:
	bool isEmpty() const;
	int size() const;
	void enqueue( const T& aElement );
	const T& dequeue();
};

template<class T>
bool DynamicQueue<T>::isEmpty() const
{
	return fElements.isEmpty();									// 2
}

template<class T>
int DynamicQueue<T>::size() const
{
	return fElements.size();									// 2
}

template<class T>
void DynamicQueue<T>::enqueue( const T& aElement )
{
	fElements.add( aElement );									// 2
}

template<class T>
const T& DynamicQueue<T>::dequeue()
{
	if ( !isEmpty() )											// 2
	{
		const T& Result = fElements[0];							// 2
		fElements.dropFirst();									// 2
		return Result;											// 1
	}
	else
		throw std::underflow_error( "Queue is empty!" );		// 1/14
}

#endif /* DYNAMICQUEUE_H_ */
