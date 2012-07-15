#include <iostream>
#include <string>

using namespace std;

int main()
{
	char rsp = ' ';
	do{
		cout << "Enter two strings: " << endl;
		string str1, str2;
		cin >> str1 >> str2;
		if(str1 < str2)
			cout << "The first is lexicographically smaller." << endl;
		else if(str2 < str1)
			cout << "The second is lexicographically smaller." << endl;
		else
			cout << "They are the same." << endl;
			
		cout << "Go again? [y/n]:  ";
		cin >> rsp;
	}while(rsp != 'n');
	
	return 0;
}