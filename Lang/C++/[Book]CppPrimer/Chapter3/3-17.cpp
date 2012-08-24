#include <iostream>
#include <vector>
#include <string>
#include <cctype>

using namespace std;

string convertToUpper(string s)
{
	for(string::size_type index = 0; index < s.size(); index++){
		if(islower(s[index])){
			s[index] = toupper(s[index]);
		}
	}
	return s;
}

int main()
{		
	vector<string> myWords;
	string temp;
	while(cin >> temp)
		myWords.push_back(temp);
	
	int wordCount = 0;
	for(vector<string>::iterator iter = myWords.begin(); iter != myWords.end(); ++iter){
		*iter = convertToUpper(*iter);
		cout << *iter << "  ";
		wordCount++;
		if(wordCount == 8){
			cout << endl;
			wordCount = 0;
		}
	}
	return 0;
}