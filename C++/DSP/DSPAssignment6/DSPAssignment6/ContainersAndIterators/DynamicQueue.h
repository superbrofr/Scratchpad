/*
 * DynamicQueue.h
 *
 *  Created on: 13 May 2011
 *      Author: charlie
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
	bool isEmpty() const
	{
		return fElements.isEmpty();
	}

	int size() const
	{
		return fElements.size();
	}

	void enqueue(const T& aElement)
	{
		fElements.addFirst(aElement);
	}

	const T& dequeue()
	{
		if(size() > 0){
			T temp = fElements[fElements.size() - 1];
			fElements.dropLast();
			return temp;
		}
		else{
			throw std::out_of_range("Trying to dequeue an empty queue!");
		}
	}
};

#endif /* DYNAMICQUEUE_H_ */
