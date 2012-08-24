#include <iostream>
#include <vector>

using namespace std;

int main()
{
	vector<int> vec1, vec2;
	vec1.push_back(0);
	vec1.push_back(1);
	vec1.push_back(1);
	vec1.push_back(2);
	
	vec2.push_back(0);
	vec2.push_back(1);
	vec2.push_back(1);
	vec2.push_back(2);
	vec2.push_back(3);
	vec2.push_back(5);
	vec2.push_back(8);
	
	vector<int>::size_type size1 = vec1.size();
	vector<int>::size_type size2 = vec2.size();
	
	bool ans = true;
	if(size1 < size2){
		for(vector<int>::size_type i = 0; i != size1; ++i){
			if(vec1[i] != vec2[i])
				ans = false;
		}
	}
	else{
		for(vector<int>::size_type i = 0; i != size2; ++i)
			if(vec1[i] != vec2[i])
				ans = false;
	}
	
	if(ans)
		cout << "True." << endl;
	else
		cout << "False." << endl;
	
	return 0;
}