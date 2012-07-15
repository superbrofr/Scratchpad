/*
 * OStream12Bits.cpp
 *
 * HIT3303/8303
 *
 */

#include "OStream12Bits.h"

using namespace std;

void OStream12Bits::init()
{
	for ( int i = 0; i < 32; i++ )
		fBuffer[i] = 0;

	fByteIndex = 0;
	fBitIndex = 8;
}

void OStream12Bits::finishWriteBit()
{
	if ( fBitIndex == 0 )
	{
		if ( fByteIndex == 31 )
	    {
			fByteIndex++;
			flush();
	    }
	    else
	    {
	    	fByteIndex++;
	    	fBitIndex = 8;
	    }
	  }
}

void OStream12Bits::writeBit0()
{
	fBitIndex--;
	finishWriteBit();
}

void OStream12Bits::writeBit1()
{
	fBuffer[fByteIndex] += 1 << (fBitIndex - 1);
	fBitIndex--;
	finishWriteBit();
}

OStream12Bits::OStream12Bits()
{
	init();
}

OStream12Bits::OStream12Bits( const char* aFileName )
{
	init();
	open( aFileName );
}

void OStream12Bits::open( const char* aFileName )
{
	fOStream.open( aFileName,  ofstream::binary );
}

bool OStream12Bits::fail()
{
	return fOStream.fail();
}

void OStream12Bits::close()
{
	flush();
	fOStream.close();
}

void OStream12Bits::flush()
{
  fOStream.write( (char*)fBuffer, fByteIndex + (fBitIndex % 8 ? 1 : 0) );
  init();
}

OStream12Bits& OStream12Bits::operator <<( int aCode )
{
	aCode = aCode & 0x0fff; // mask 12 lower bits

	for ( int i = 0; i < 12; i++ ) // write 12 Bits
	{
		if ( aCode & 0x01 ) // The current lowest bit is set.
			writeBit1();
	    else
	    	writeBit0();
	    aCode >>= 1; // Code = Code / 2
	}

	return *this;
}
