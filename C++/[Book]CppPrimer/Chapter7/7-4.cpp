#include <iostream>

using namespace std;

int abs(int i1)
{
	if(i1 >= 0)
		return i1;
	else
		return -i1;
}

int main()
{
	int i1;
	cout << "Enter a number: ";
	cin >> i1;
	cout << "The absolute value of " << i1 << " is " << abs(i1) << endl;
	return 0;
}