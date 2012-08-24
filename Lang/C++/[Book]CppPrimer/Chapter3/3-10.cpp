#include <iostream>
#include <string>
#include <cctype>

using namespace std;

int main()
{
	cout << "Enter some words: "; 
	string s1, s2;
	getline(cin, s1);
	cout << "Removing punctuation..." << endl;
	for(string::size_type i = 0; i < s1.size(); ++i){
		if(!ispunct(s1[i]))
			s2 += s1[i];
	}
	cout << s2 << endl;
}