#include <iostream>
#include <vector>

using namespace std;

int main (int argc, char const *argv[])
{
  cout << "Enter some integers, followed by a '0': " << endl;
  vector<int> myInts;
  int next;
  cin >> next;
  while(next != 0){
    myInts.push_back(next);
    cin >> next;
  }
    
  cout << "Vector size: " << myInts.size() << endl;
  
  size_t dimension = myInts.size();
  int *intArr = new int[dimension];
  for(size_t i = 0; i != myInts.size(); i++)
    intArr[i] = myInts[i];
    
  cout << "Elements: " << endl;
  for(int *curr = intArr; curr != (intArr + dimension); curr++)
    cout << *curr << endl;
  return 0;
}