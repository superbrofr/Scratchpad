#include <iostream>
#include <vector>

using namespace std;

double sum(vector<double>::iterator beg, vector<double>::iterator end)
{
	double result = 0;
	while(beg != end)
		result += *beg++;
	return result;
}

int main()
{
	vector<double> myVec;
	myVec.push_back(3.5);
	myVec.push_back(6.2);
	cout << "Sum: " << sum(myVec.begin(), myVec.end()) << endl;

	return 0;
}