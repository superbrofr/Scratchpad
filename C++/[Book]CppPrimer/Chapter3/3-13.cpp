#include <iostream>
#include <vector>

using namespace std;

int main()
{
	vector<int> myInts;
	int temp;
	while(cin >> temp)
		myInts.push_back(temp);
	
	for(vector<int>::size_type index = 0; index < myInts.size(); ++index)
		cout << "The sum of " << myInts[index] << " and " << myInts[index + 1] << " is: " << myInts[index] + myInts[index + 1] << endl;
		
	cout << "....." << endl;
	
	for(vector<int>::size_type index = 0; index < myInts.size(); ++index)
		cout << "The sum of " << myInts[index] << " and " << myInts[myInts.size() - index] << " is: " << myInts[index] + myInts[myInts.size() - index] << endl;
	return 0;
}