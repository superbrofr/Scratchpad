/*
 * main.cpp
 *
 *  Created on: May 18, 2011
 *      Author: Charlotte
 */
#include "NTree.h"
#include <iostream>
#include <string>

using namespace std;

void test1()
{
	string A("A");
	string A1("AA");
	string A2("AB");
	string A3("AC");
	string AA1("AAA");
	string AB1("ABA");
	string AB2("ABB");

	typedef NTree<string,3> NS3Tree;

	NS3Tree root(A);

	root.attachNTree(0, new NS3Tree(A1));
	root.attachNTree(1, new NS3Tree(A2));
	root.attachNTree(2, new NS3Tree(A3));

	root[0].attachNTree(0, new NS3Tree(AA1));
	root[1].attachNTree(0, new NS3Tree(AB1));
	root[1].attachNTree(1, new NS3Tree(AB2));

	cout << "root:       " << root.key() << endl;
	cout << "root[0]:    " << root[0].key() << endl;
	cout << "root[1]:    " << root[1].key() << endl;
	cout << "root[2]:    " << root[2].key() << endl;
	cout << "root[0][0]: " << root[0][0].key() << endl;
	cout << "root[1][0]: " << root[1][0].key() << endl;
	cout << "root[1][1]: " << root[1][1].key() << endl;

	NS3Tree copy = root;

	cout << "copy:       " << copy.key() << endl;
	cout << "copy[0]:    " << copy[0].key() << endl;
	cout << "copy[1]:    " << copy[1].key() << endl;
	cout << "copy[2]:    " << copy[2].key() << endl;
	cout << "copy[0][0]: " << copy[0][0].key() << endl;
	cout << "copy[1][0]: " << copy[1][0].key() << endl;
	cout << "copy[1][1]: " << copy[1][1].key() << endl;

	NS3Tree* temp = copy.detachNTree(0);

	if(&copy[0] == &NS3Tree::NIL)
		cout << "Detach succeeded." << endl;
	else
		cout << "Detach failed." << endl;

	delete temp;
}

void test2()
{
	string A("A");
	string A1("AA");
	string A2("AB");
	string A3("AC");
	string AA1("AAA");
	string AB1("ABA");
	string AB2("ABB");

	typedef NTree<string,3> NS3Tree;

	NS3Tree root(A);

	root.attachNTree(0, new NS3Tree(A1));
	root.attachNTree(1, new NS3Tree(A2));
	root.attachNTree(2, new NS3Tree(A3));

	root[0].attachNTree(0, new NS3Tree(AA1));
	root[1].attachNTree(0, new NS3Tree(AB1));
	root[1].attachNTree(1, new NS3Tree(AB2));

	PreOrderVisitor<string> v1;
	PostOrderVisitor<string> v2;
	TreeVisitor<string> v3;

	cout << "Pre-order traversal:" << endl;
	root.doDepthFirstTraversal(v1);
	cout << endl;

	cout << "Post-order traversal:" << endl;
	root.doDepthFirstTraversal(v2);
	cout << endl;

	cout << "Breadth-first traversal:" << endl;
	root.doBreadthFirstTraversal(v3);
	cout << endl;
}

int main()
{
	//test1();
	test2();
}
