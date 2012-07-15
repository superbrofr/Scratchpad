#include <iostream>
#include <string>

using namespace std;

int main (int argc, char const *argv[])
{
  string input;
  cin >> input;
  cout << "You typed: " << input << endl;
  const char *otherInput = input.c_str(); // to convert to c-string if needed
  return 0;
}