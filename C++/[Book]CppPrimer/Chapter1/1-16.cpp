#include <iostream> //preprocessor directive

int main()
{	
	std::cout << "Enter two numbers: " << std::endl;
	int v1, v2;
	std::cin >> v1 >> v2;

	if(v1 <= v2){
		std::cout << "The larger input was " << v2 << std::endl;
	}
	else{
		std::cout << "The larger input was " << v1 << std::endl;
	}
	
	return 0;
}