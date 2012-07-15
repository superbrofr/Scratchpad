#include <iostream>

using namespace std;

int main()
{
	int a = 4, b = 3, c = 2, d = 1;
	if((a > b) && (b > c) && (c > d))
		cout << "Everything is A-Ok!" << endl;
	else
		cout << "Not okay man, NOT OKAY" << endl;
	return 0;
}