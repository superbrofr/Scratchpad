#include <iostream>

using namespace std;

int larger(int i1, int *i2)
{
	if(*i2 > i1)
		return *i2;
	else
		return i1;
}

int main()
{
	int i1, i2;
	cout << "Enter two numbers: ";
	cin >> i1 >> i2;
	cout << "The larger is: " << larger(i1, &i2) << endl;
	return 0;
}