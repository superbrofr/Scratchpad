#include <iostream>
#include <string>
#include <cstring>

using namespace std;

int main()
{
  //strings
  string str1 = "hello", str2 = "helo";
  if(str1 == str2)
    cout << "1. They're equal." << endl;
  str1 = str2;
  if(str1 == str2)
    cout << "2. They're equal." << endl;
  //c-strings
  char cStr1[] = "hello", cStr2[] = "hell";
  if(!strcmp(cStr1, cStr2))
    cout << "3. They're equal." << endl;
  strcpy(cStr1, cStr2);
  if(!strcmp(cStr1, cStr2))
    cout << "4. They're equal." << endl;
  return 0;
}