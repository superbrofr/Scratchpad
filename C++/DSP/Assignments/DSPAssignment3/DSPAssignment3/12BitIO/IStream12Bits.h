/*
 * IStream12Bits.h
 *
 *  Created on: 2 Apr 2011
 *      Author: charlie
 */

#ifndef ISTREAM12BITS_H_
#define ISTREAM12BITS_H_

typedef unsigned char byte;
#include <fstream>

class IStream12Bits
{
private:
	std::ifstream fIStream;
	byte fBuffer[32];
	int fByteCount; //number of bytes up to in the buffer
	int fByteIndex;
	int fBitIndex;

	void reload();
	int fetchCode();

public:
	IStream12Bits();
	IStream12Bits(const char* aFileName);

	void open(const char* aFileName);
	void close();
	bool fail();
	bool eof();
	IStream12Bits& operator>>(int& aCode);
};

#endif /* ISTREAM12BITS_H_ */
