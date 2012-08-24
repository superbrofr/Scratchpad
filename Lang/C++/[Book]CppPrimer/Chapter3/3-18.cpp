#include <iostream>
#include <vector>

using namespace std;

int main()
{
	vector<int> myInts(10, 5);
	cout << "BEFORE: " << endl;
	for(vector<int>::iterator iter = myInts.begin(); iter != myInts.end(); iter++)
		cout << *iter << endl;
	
	for(vector<int>::iterator iter = myInts.begin(); iter != myInts.end(); iter++)
		*iter *= 2;
		
	cout << "AFTER: " << endl;
	for(vector<int>::iterator iter = myInts.begin(); iter != myInts.end(); iter++)
		cout << *iter << endl;
	return 0;
}