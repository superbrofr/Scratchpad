#include <iostream>
#include <string>

using namespace std;

int main()
{
	cout << "Enter two words: "; 
	string s1, s2;
	cin >> s1 >> s2;
	if(s1 == s2){
		cout << "Words are the same." << endl;
	}
	else{
		if(s1 > s2)
			cout << "The first word is larger." << endl;
		else
			cout << "The second word is larger." << endl;
	}
	
	if(s1.size() == s2.size()){
		cout << "Both words have the same number of letters." << endl;
	}
	else{
		if(s1.size() > s2.size())
			cout << "The first word is longer." << endl;
		else
			cout << "The second word is longer." << endl;
	}
}