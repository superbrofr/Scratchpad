#include <iostream>
#include "Sales_item.h"

int main()
{
	Sales_item item1, sum;
	while(std::cin >> item1)
		sum = sum + item1;
	
	std::cout << "TOTAL: " << std::endl;
	std::cout << sum << std::endl;
	return 0;
}