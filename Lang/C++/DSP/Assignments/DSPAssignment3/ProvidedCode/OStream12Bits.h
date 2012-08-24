/*
 * OStream12Bits.h
 *
 * HIT3303/8303
 *
 */

#ifndef OSTREAM12BITS_H_
#define OSTREAM12BITS_H_

#include <fstream>

typedef unsigned char byte;

class OStream12Bits
{
private:
  std::ofstream fOStream;
  byte fBuffer[32];
  int fByteIndex;
  int fBitIndex;

  void init();		// initialize data members
  void finishWriteBit(); // complete write
  void writeBit0();      // write 0
  void writeBit1();       // write 1

public:
  OStream12Bits();
  OStream12Bits( const char* aFileName );

  void open( const char* aFileName );
  void close();
  bool fail();
  void flush();
  OStream12Bits& operator<<( int aCode );
};

#endif /* OSTREAM12BITS_H_ */
