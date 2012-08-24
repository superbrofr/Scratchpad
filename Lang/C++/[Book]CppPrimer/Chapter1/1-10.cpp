#include <iostream> //preprocessor directive

int main()
{	
	int sum = 0;
	for(int val = 50; val <= 100; ++val)
		sum += val;
		
	std::cout << "FOR: The sum of 50 to 100 inclusive is " << sum << std::endl;
	
	int val = 50;
	sum = 0;
	while(val <= 100){
		sum += val;
		++val;
	}
		
	std::cout << "WHILE: The sum of 50 to 100 inclusive is " << sum << std::endl;
	
	
	return 0;
}