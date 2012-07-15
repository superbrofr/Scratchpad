#include <iostream>

using namespace std;

int &get(int *arry, int index)
{
	return arry[index];
}

int main()
{
	int io[10];
	cout << "BEFORE: " << endl;
	for(int i = 0; i < 10; ++i)
		cout << io[i] << endl;

	for(int i = 0; i < 10; ++i)
		get(io, i) = 0;

	cout << "AFTER: " << endl;
	for(int i = 0; i < 10; ++i)
		cout << io[i] << endl;
}