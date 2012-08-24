#include <iostream>
#include <string>

using namespace std;

int main()
{
	// string s;
	// cin >> s;
	// cout << s << endl;
	// return 0;
	
	// string word;
	// while(cin >> word)
		// cout << word << endl;
	// return 0;
	
	string line;
	while(getline(cin, line))
		cout << line << " is " << line.size() << " characters long." << endl;
	return 0;
}