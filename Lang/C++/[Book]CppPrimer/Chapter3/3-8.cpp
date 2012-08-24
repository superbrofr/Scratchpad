#include <iostream>
#include <string>

using namespace std;

int main()
{
	cout << "Enter two words: "; 
	string s1, s2, s3;
	cin >> s1 >> s2;
	s3 = s1 + s2;
	
	cout << "Concatenation: " << s3 << endl;
}