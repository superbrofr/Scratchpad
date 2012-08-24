#include <iostream>

using namespace std;

int main()
{
	int wAns = 12 / 3 * 4 + 5 * 15 + 24 % 4 / 2;
	int pAns = ((12 / 3) * 4) + (5 * 15) + ((24 % 4) / 2);
	cout << "Parentheses: " << pAns << endl;
	cout << "Without: " << wAns << endl;
	return 0;
}