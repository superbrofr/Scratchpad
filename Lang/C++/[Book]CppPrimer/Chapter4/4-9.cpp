#include <cstddef>

int main()
{
	const size_t arr_size = 10;
	int arr[arr_size];
	for(size_t i = 0; i != arr_size; i++)
		arr[i] = i;
	return 0;
}