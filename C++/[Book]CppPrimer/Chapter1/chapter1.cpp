#include <iostream> //preprocessor directive
#include "Sales_item.h" //non-standard headers enclosed in quotations rather than <>

int main()
{
	// std::cout << "Enter two numbers: " << std::endl; //uses the output operator to print to the standard output. The output operator writes the right-hand operand to the left-hand operand (which must be a ostream object), and returns the value of the left-hand operandd (i.e. the ostream)
	// int v1, v2;
	// std::cin >> v1 >> v2;
	
	// std::cout << "The sum of ";
	// std::cout << v1;
	// std::cout << " and ";
	// std::cout << v2;
	// std::cout << " is ";
	// std::cout << v1 + v2;
	// std::cout << std::endl;
	
	// return 0;
	
	
	
	// int sum = 0, val = 1;
	// while(val <= 10){
		// sum += val;
		// ++ val;
	// }
	
	// std::cout << "The sum of 1 - 10 inclusive is " << sum << std::endl;
	// return 0;
	
	
	
	// int sum = 0;
	// for(int val = 1; val <= 10; ++val)
		// sum += val;
		
	// std::cout << "The sum of 1 - 10 inclusive is " << sum << std::endl;
	// return 0;
	
	
	// std::cout << "Enter two numbers: " << std::endl;
	// int v1, v2;
	// std::cin >> v1 >> v2;
	
	// int lower, upper;
	// if(v1 <= v2){
		// lower = v1;
		// upper = v2;
	// }
	// else{
		// lower = v2;
		// upper = v1;
	// }
	
	// int sum = 0;
	// for(int val = lower; val <= upper; ++val)
		// sum += val;
		
	// std::cout << "Sum of " << lower << " to " << upper << " inclusive is " << sum << std::endl;
	// return 0;
	
	
	// std::cout << "Enter some numbers: " << std::endl;
	
	// int sum = 0, value;
	// while(std::cin >> value)
		// sum += value;
	// std::cout << "Sum is: " << sum << std::endl;
	// return 0;
	
///START USE OF SALE ITEM CLASS!!!!!!!!!	

	// Sales_item book;
	// std::cin >> book; //read number of copies sold and sales price
	// std::cout << book << std::endl;
	// return 0;
	
	
	// Sales_item item1, item2;
	// std::cin >> item1 >> item2;
	// std::cout << item1 + item2 << std::endl;
	// return 0;
	
	
	// Sales_item item1, item2;
	// std::cin >> item1 >> item2;
	// if(item1.same_isbn(item2)){
		// std::cout << item1 + item2 << std::endl;
		// return 0;
	// }
	// else{
		// std::cerr << "Data must refer to the same ISBN." << std::endl;
		// return -1;
	// }
	
	
	Sales_item total, trans;
	if(std::cin >> total){
		while(std::cin >> trans)
			if(total.same_isbn(trans))
				total = total + trans;
			else{
				std::cout << total << std::endl;
				total = trans;
			}
		std::cout << total << std::endl; //print last one
	}
	else{
		std::cout << "No data?" << std::endl;
		return -1;
	}
	return 0;
}