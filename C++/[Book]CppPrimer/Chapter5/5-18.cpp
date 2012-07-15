#include <vector>
#include <string>
#include <iostream>

using namespace std;

int main()
{
	vector<string*> myStringPtrs;
	string str1 = "hi";
	string str2 = "there";
	string str3 = "buddy";
	myStringPtrs.push_back(&str1);
	myStringPtrs.push_back(&str2);
	myStringPtrs.push_back(&str3);
	
	vector<string*>::iterator myIter = myStringPtrs.begin();
	while(myIter != myStringPtrs.end()){
		cout << **myIter << "   ...   " << (*myIter)->size() << " characters" << endl;
		myIter++;
	}
	return 0;
}