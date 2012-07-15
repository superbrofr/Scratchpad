#include <iostream>
#include <string>

using namespace std;

int main()
{
  //c-strings
  char cStr1[] = "he";
  char cStr2[] = "llo";
  char finalCString[strlen(cStr1) + strlen(cStr2) + 1];
  strcpy(finalCString, cStr1);
  strcat(finalCString, cStr2);
  cout << "Final String 1: " << finalCString << endl;
  
  //regular strings
  string str1 = "he";
  string str2 = "llo";
  str1 += str2;
  cout << "Final String 2: " << str1 << endl;
  return 0;
}