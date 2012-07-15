/*
 * OStream12Bits.cpp
 *
 *  Created on: Mar 30, 2011
 *      Author: Charlotte
 */

#include "OStream12Bits.h"

using namespace std;

OStream12Bits::OStream12Bits()
{
	init();
}

OStream12Bits::OStream12Bits(const char* aFileName)
{
	init();
	open(aFileName);
}

void OStream12Bits::init()
{
	for(int i = 0; i < 32; i++){
		fBuffer[i] = 0;
	}
	fByteIndex = 0;
	fBitIndex = 8;
}

void OStream12Bits::writeBit0()
{
	//no need to explicitly write 0, because it already is
	fBitIndex--;
	finishWriteBit();
}

void OStream12Bits::writeBit1()
{
	fBuffer[fByteIndex] += 1 << (fBitIndex - 1); //write 1 bit to the next MSB
	fBitIndex--;
	finishWriteBit();
}

//Checks if the bit index needs to be reset
//resets if need be, and increments the byte index for the next byte in the buffer
//if the byte index is 32 (end of buffer - last byteIndex is buffer[31]), flush the buffer and reset
void OStream12Bits::finishWriteBit()
{
	if(fBitIndex == 0)
	{
		if(fByteIndex == 31){
			fByteIndex++;
			flush(); //write full buffer to stream
		}
		else{
			fByteIndex++;
			fBitIndex = 8;
		}
	}
}

void OStream12Bits::open(const char* aFileName)
{
	fOStream.open(aFileName, ofstream::binary);
}

bool OStream12Bits::fail()
{
	return fOStream.fail();
}

void OStream12Bits::close()
{
	fOStream.close();
}

//Writes the buffer to the stream and resets the variables
void OStream12Bits::flush()
{
	fOStream.write((char*)fBuffer, fByteIndex + (fBitIndex % 8 ? 1 : 0)); //addition is to write another byte if there is a partial byte (i.e. bitIndex is not 8) (only used for the end)
	init();
}

OStream12Bits& OStream12Bits::operator<<(int aCode)
{
	aCode = aCode & 0x0fff; //mask lower 12 bits - we process 12 bits at a time
	for(int i = 0; i < 12; i++){
		if(aCode & 0x01){
			writeBit1();
		}
		else{
			writeBit0();
		}
		aCode >>= 1; //shift right: aCode = aCode / 2
	}
	return *this;
}
