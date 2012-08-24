#include <iostream>

using namespace std;

int main()
{
	char curr, prev = ' ';
	int aCnt = 0, eCnt = 0, iCnt = 0, oCnt = 0, uCnt = 0;
	int blankCnt = 0, tabCnt = 0, spaceCnt = 0;
	int ffCnt = 0, flCnt = 0, fiCnt = 0;
	while(cin >> curr){
		switch(curr){
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
		if(prev == 'f'){
			if(curr == 'f')
				++ffCnt;
			else if(curr == 'l')
				++flCnt;
			else if(curr == 'i')
				++fiCnt;
		}
		prev = curr;
	}
	
	return 0;
}