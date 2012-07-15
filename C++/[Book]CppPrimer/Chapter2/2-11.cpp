#include <iostream>

int main()
{
	int value;
	int pow;
	int result = 1;
	
	std::cout << "Enter the base: ";
	std::cin >> value;
	std::cout << "\nEnter the exponent: ";
	std::cin >> pow;
	
	for(int cnt = 0; cnt != pow; ++cnt)
		result *= value;
		
	std::cout << "\n" << value << " raised to the power of " << pow << ": " << result << std::endl;
	
	return 0;
}