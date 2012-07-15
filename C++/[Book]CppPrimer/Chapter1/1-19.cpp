#include <iostream>

int main()
{
	std::cout << "Enter two numbers: " << std::endl;
	
	int lower, upper;
	std::cin >> lower >> upper;
	//swap numbers if needed
	if(lower > upper){
		int temp = lower;
		lower = upper;
		upper = temp;
	}
	
	int numCount = 0;
	for(int i = lower; i <= upper; ++i){
		std::cout << i << " ";
		numCount++;
		if(numCount == 10){
			std::cout << std::endl;
			numCount = 0;
		}
	}
}