#include <iostream>
#include <string>

using namespace std;

int main()
{
	string next, prev = "";
	while(cin >> next){
		if(next == prev)
			break;
		else
			prev = next;
	}
	
	cout << "Repeated: " << next << endl;
	
	return 0;
}