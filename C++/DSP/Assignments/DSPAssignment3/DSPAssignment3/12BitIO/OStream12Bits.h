/*
 * OStream12Bits.h
 *
 *  Created on: 2 Apr 2011
 *      Author: charlie
 */

#ifndef OSTREAM12BITS_H_
#define OSTREAM12BITS_H_

typedef unsigned char byte; //8 bit positive number i.e. a byte
#include <fstream>

//This class is an adapter for a ofstream object (that works with 12 bits rather than 8 at a time)
class OStream12Bits
{
	std::ofstream fOStream;
	byte fBuffer[32]; //read 32 bits before actually writing to the file
	int fByteIndex;
	int fBitIndex; //how many bits to the left to shift the output

	void init(); //initialise data members
	void finishWriteBit(); //complete write to file
	void writeBit0(); //write 0
	void writeBit1(); //write 1

public:
	OStream12Bits();
	OStream12Bits(const char* aFileName);

	void open(const char* aFileName);
	void close();
	bool fail();
	void flush(); //flush the buffer (i.e. write it to the file) and reset byteIndex/bitIndex
	OStream12Bits& operator<<(int aCode);
};

#endif /* OSTREAM12BITS_H_ */
