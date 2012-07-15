#include <iostream>
#include <bitset>

using namespace std;

int main()
{
	bitset<10> bits(99999999999999999999999999999999999999999999999999999999999999999999UL);
	bits.to_ulong();
	
	return 0;
}