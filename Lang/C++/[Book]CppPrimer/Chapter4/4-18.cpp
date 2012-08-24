#include <cstddef>
#include <iostream>

using namespace std;

int main()
{
	const size_t arrSize = 5;
	int myArr[] = {0, 1, 2, 3, 4};
	cout << "Elements Before:" << endl;
	for(size_t i = 0; i != arrSize; i++)
		cout << myArr[i] << endl;
		
	for(int *curr = myArr, *end = (myArr + arrSize); curr != end; curr++)
		*curr = 0;
		
	cout << "Elements After:" << endl;
	for(size_t i = 0; i != arrSize; i++)
		cout << myArr[i] << endl;
	return 0;
}