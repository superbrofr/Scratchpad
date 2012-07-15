#include <iostream> //preprocessor directive

int main()
{	
	std::cout << "Enter some numbers: " << std::endl;
	
	int value, negCount = 0;
	while(std::cin >> value){
		if(value < 0)
			negCount++;
	}
	std::cout << "Number of negative numbers is: " << negCount << std::endl;
	return 0;
}