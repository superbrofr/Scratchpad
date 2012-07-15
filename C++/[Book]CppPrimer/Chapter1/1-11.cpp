#include <iostream> //preprocessor directive

int main()
{	
	std::cout << "FOR:" << std::endl;
	for(int val = 10; val >= 0; --val){
	
		std::cout << val << std::endl;
	}
	
	std::cout << "WHILE:" << std::endl;
	while(val <= 0){
		std::cout << val << std::endl;
		--val;
	}
	
	return 0;
}