#include <iostream>

using namespace std;

int main()
{
	char ch;
	int aCnt = 0, eCnt = 0, iCnt = 0, oCnt = 0, uCnt = 0;
	int blankCnt = 0, tabCnt = 0, spaceCnt = 0;
	while(cin >> ch){
		switch(ch){
			case 'A':
			case 'a':
				++aCnt;
				break;
			case 'E':
			case 'e':
				++eCnt;
				break;
			case 'I':
			case 'i':
				++iCnt;
				break;
			case 'O':
			case 'o':
				++oCnt;
				break;
			case 'U':
			case 'u':
				++uCnt;
				break;
			case ' ':
				++blankCnt;
				break;
			case '\t':
				++tabCnt;
				break;
			case '\n':
				++spaceCnt;
				break;
		}
	}
	
	return 0;
}