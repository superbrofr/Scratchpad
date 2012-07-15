/*
 * main.cpp
 *
 *  Created on: 7 Apr 2011
 *      Author: charlie
 */

#include "DoubleLinkedNode.h"
#include "NodeIterator.h"
#include <iostream>
#include <string>

using namespace std;

//Test harness 1
void testDoubleLinkedNodes()
{
	string s1("One");
	string s2("Two");
	string s3("Three");

	typedef DoubleLinkedNode<string> StringNode;

	StringNode n1(s1);
	StringNode n2(s2);
	StringNode n3(s3);

	n1.insertNode(n3);
	n1.insertNode(n2);

	cout << "Three elements:" << endl;

	for(StringNode* pn = &n1; pn != &StringNode::NIL; pn = &pn->getNext()){
		cout << "(";
		if(&pn->getPrevious() != &StringNode::NIL)
			cout << pn->getPrevious().getValue();
		else
			cout << "<NULL>";

		cout << "," << pn->getValue() << ",";

		if(&pn->getNext() != &StringNode::NIL)
			cout << pn->getNext().getValue();
		else
			cout << "<NULL>";

		cout << ")" << endl;
	}

	n1.getNext().dropNode();

	cout << "Two elements:" << endl;

	for(StringNode* pn = &n1; pn != &StringNode::NIL; pn = &pn->getNext()){
		cout << "(";
		if(&pn->getPrevious() != &StringNode::NIL)
			cout << pn->getPrevious().getValue();
		else
			cout << "<NULL>";

		cout << "," << pn->getValue() << ",";

		if(&pn->getNext() != &StringNode::NIL)
			cout << pn->getNext().getValue();
		else
			cout << "<NULL>";

		cout << ")" << endl;
	}
}

void testListIterator()
{
	typedef DoubleLinkedNode<int> IntNode;

	IntNode n1(1);
	IntNode n2(2);
	IntNode n3(3);
	IntNode n4(4);
	IntNode n5(5);
	IntNode n6(6);

	n1.insertNode(n6);
	n1.insertNode(n5);
	n1.insertNode(n4);
	n1.insertNode(n3);
	n1.insertNode(n2);

	NodeIterator<int> iter(n1);

	cout << "Forward iteration I:" << endl;
	for(; iter != iter.end(); iter++)
		cout << *iter << endl;

	cout << "Backward iteration I:" << endl;
	for(iter--; iter != iter.begin(); iter--)
		cout << *iter << endl;


	cout << "Forward iteration II:" << endl;
	for(++iter; iter != iter.end(); ++iter)
		cout << *iter << endl;

	cout << "Backward iteration II:" << endl;
	for(--iter; iter != iter.begin(); --iter)
		cout << *iter << endl;

}

int main()
{
	testDoubleLinkedNodes();

	testListIterator();

	return 0;
}
