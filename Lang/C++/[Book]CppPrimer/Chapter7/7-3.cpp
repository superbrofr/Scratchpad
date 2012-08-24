#include <iostream>

using namespace std;

int raise(int i1, int i2)
{
	int result;
	if(i2 == 0)
		result = 0;
	else{
		result = i1;
		for(int i = 1; i < i2; ++i)
			result *= i1;
	}
	
	return result;
}

int main()
{
	int i1, i2;
	cout << "Enter two numbers: ";
	cin >> i1 >> i2;
	cout << i1 << " to the power of " << i2 << " is " << raise(i1, i2) << endl;
	return 0;
}