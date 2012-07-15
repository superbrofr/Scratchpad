#include <iostream>

using namespace std;

int main()
{
	cout << "Enter ints!" << endl;
	int input;
	cin >> input;
	while(input != 42)
		cin >> input;
		
	cout << "42 D:" << endl;
	return 0;
}