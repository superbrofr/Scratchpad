#include <iostream>
#include <bitset>

using namespace std;

int main()
{
	try{
		bitset<100> bits(99UL);
		bits.to_ulong();
	}catch(overflow_error err){
		cout << "Error!  " << err.what() << endl;
	}
	
	return 0;
}