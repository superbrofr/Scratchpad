#include <iostream>
#include <string>
#include <cctype>

using namespace std;

int main()
{
	string next, prev = "";
	while(cin >> next){
		if(!isupper(next[0]))
			continue;
		if(next == prev)
			break;
		else
			prev = next;
	}
	
	cout << "Repeated: " << next << endl;
	
	return 0;
}