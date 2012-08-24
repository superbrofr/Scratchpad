#include <iostream>
#include <cstdlib>

using namespace std;

int main(int argc, char *argv[])
{
	if(argc == 3){
		// NOTE: The first argument is the name of the program
		int lSum = (atoi(argv[1]) + atoi(argv[2]));
		cout << "Sum: " << lSum << endl;
	}
	else{
		cout << "Invalid number of arguments!" << endl;
	}

	return 0;
}