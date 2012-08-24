#include <iostream>

using namespace std;

int sum(int* beg, int* end)
{
	int result = 0;
	while(beg != end)
		result += *beg++;
	return result;
}

int sum2(const int arr[], size_t size)
{
	int result = 0;
	for(size_t i  = 0; i != size; ++i)
		result += arr[i];
	return result;
}

int main()
{
	int myArr[2] = {1, 7};
	cout << "Sum 1: " << sum(myArr, myArr + 2) << endl;
	cout << "Sum 2: " << sum2(myArr, sizeof(myArr)/sizeof(*myArr));
}