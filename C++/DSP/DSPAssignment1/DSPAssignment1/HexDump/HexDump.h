/*
 * HexDump.h
 *
 *  Created on: 20 Mar 2011
 *      Author: charlie
 */

#ifndef HEXDUMP_H_
#define HEXDUMP_H_

#include <fstream>

class HexDump{
private:
	std::ifstream fInput;
	std::ofstream fOutput;

public:
	~HexDump();

	bool open(char* aFileName);
	void close();

	void run();
};

#endif /* HEXDUMP_H_ */
