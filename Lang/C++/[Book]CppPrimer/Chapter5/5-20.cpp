#include <iostream>

using namespace std;

int main()
{
	cout << "Enter a couple numbers: ";
	int x, y;
	cin >> x >> y;
	x < y ? cout << "The first number was smaller." : cout << "The second number was smaller.";
	return 0;
}