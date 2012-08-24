#include <iostream>
#include <cstddef>
#include <vector>

using namespace std;

bool areEqual(int arr1[], int arr2[], size_t arrSize)
{
	bool ans = true;
	for(size_t index = 0; index != arrSize; index++)
		if(arr1[index] != arr2[index])
			ans = false;
	return ans;
}

int main()
{
	//comparing arrays
	const size_t array_size = 7;
	int ia1[] = {0, 1, 2, 3, 4, 5, 6};
	int ia2[array_size];
	if(areEqual(ia1, ia2, array_size))
		cout << "Yes, the two arrays are equal." << endl;
	else
		cout << "Nup, arrays aren't equal." << endl;
		
	//comparing vectors
	vector<int> v1;
	for(unsigned i = 0; i < 5; i++)
		v1.push_back(i);
	vector<int> v2 = v1;
	if(v1 == v2)
		cout << "Yes, the two vectors are equal." << endl;
	else
		cout << "Nup, vectors aren't equal." << endl;
	return 0;
}