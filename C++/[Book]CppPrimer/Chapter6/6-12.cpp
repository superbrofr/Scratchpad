#include <iostream>
#include <string>

using namespace std;

int main()
{
	string currWord, prevWord;
	string currMaxWord;
	int maxCount = 0, currCount = 0;
	bool end = false;
	while(!end && (cin >> currWord)){
		if(currWord == prevWord){
			++currCount;
		}
		else{
			currCount = 1;
		}
			
		if(currCount > maxCount){
			currMaxWord = currWord;
			maxCount = currCount;
		}
		if(currWord == "EXIT")
			end = true;
		prevWord = currWord;
	}
	
	cout << currMaxWord << "      :      " << maxCount << endl;
	
	return 0;
}