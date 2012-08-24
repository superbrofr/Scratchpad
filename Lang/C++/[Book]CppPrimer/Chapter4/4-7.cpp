#include <iostream>
#include <cstddef>
#include <vector>

using namespace std;

int main()
{
	//copying array
	const size_t array_size = 7;
	int ia1[] = {0, 1, 2, 3, 4, 5, 6};
	int ia2[array_size];
	
	for(size_t ix = 0; ix != array_size; ix++)
		ia2[ix] = ia1[ix];
		
	//copying vector
	vector<int> v1;
	for(unsigned i = 0; i < 5; i++)
		v1.push_back(i);
		
	vector<int> v2 = v1;
	return 0;
}