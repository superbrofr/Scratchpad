/*
 * main.cpp
 *
 *  Created on: May 11, 2011
 *      Author: Charlotte
 */

#include "DynamicStack.h"
#include "DynamicStackIterator.h"
#include "DynamicQueue.h"
#include "DynamicQueueIterator.h"
#include <iostream>

using namespace std;

void test1()
{
	DynamicStack<int> iStack;

	iStack.push(1);
	iStack.push(2);
	iStack.push(3);
	iStack.push(4);
	iStack.push(5);
	iStack.push(6);

	cout << "top: " << iStack.top() << endl;
	iStack.pop();
	iStack.pop();
	cout << "top: " << iStack.top() << endl;
	iStack.pop();
	cout << "top: " << iStack.top() << endl;
	cout << "size: " << iStack.size() << endl;
	cout << "is empty: " << (iStack.isEmpty() ? "T" : "F") << endl;
	iStack.pop();
	iStack.pop();
	iStack.pop();
	cout << "is empty: " << (iStack.isEmpty() ? "T" : "F") << endl;
}

void test2()
{
	DynamicStack<int> iStack;

	iStack.push(1);
	iStack.push(2);
	iStack.push(3);
	iStack.push(4);
	iStack.push(5);
	iStack.push(6);

	cout << "Traverse elements" << endl;

	for(DynamicStackIterator<int> iter = DynamicStackIterator<int>(iStack); iter != iter.end(); iter++){
		cout << "value: " << *iter << endl;
	}
}

void test3()
{
	DynamicQueue<int> iQueue;

	iQueue.enqueue(1);
	iQueue.enqueue(2);
	iQueue.enqueue(3);
	iQueue.enqueue(4);
	iQueue.enqueue(5);
	iQueue.enqueue(6);

	cout << "Queue elements:" << endl;

	while(!iQueue.isEmpty()){
		cout << "value: " << iQueue.dequeue() << endl;
	}
}

void test4()
{
	DynamicQueue<int> iQueue;
	iQueue.enqueue(1);
	iQueue.enqueue(2);
	iQueue.enqueue(3);
	iQueue.enqueue(4);
	iQueue.enqueue(5);
	iQueue.enqueue(6);

	cout << "Traverse queue elements" << endl;

	for(DynamicQueueIterator<int> iter = DynamicQueueIterator<int>(iQueue); iter != iter.end(); iter++){
		cout << "value: " << *iter << endl;
	}
}

int main()
{
	test1();
	test2();
	test3();
	test4();
	return 0;
}
