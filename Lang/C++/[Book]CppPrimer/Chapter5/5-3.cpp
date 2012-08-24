#include <iostream>

using namespace std;

int main()
{
	int input;
	cout << "Enter an integer: ";
	cin >> input;
	if(input % 2 == 0)
		cout << "EVEN";
	else
		cout << "ODD";
	return 0;
}