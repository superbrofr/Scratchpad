#include <iostream>

using namespace std;

int main()
{
	//changing the value of a pointer
	int myInt = 1, myInt2 = 2;
	int *pInt = &myInt;
	cout << "my int: " << myInt << endl;
	cout << "Now changing  value of myInt through the pointer" << endl;
	*pInt = 75;
	cout << "my int: " << myInt << endl;
	cout << "Now the pointer to myInt2" << endl;
	pInt = &myInt2;
	cout << "pointer: " << *pInt << endl;
	
	return 0;
}