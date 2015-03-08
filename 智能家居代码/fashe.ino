#include <SPI.h>
#include <Mirf.h>
#include <nRF24L01.h>
#include <MirfHardwareSpiDriver.h>

char words;

void setup(){
 Serial.begin(9600);
  Mirf.spi = &MirfHardwareSpi;  
  Mirf.init();
   Mirf.setTADDR((byte *)"deng");
  Mirf.payload = sizeof(char);           
   Mirf.config();   
}
void loop(){
 
while(Serial.available()>0)
 {words=Serial.read();    
      Mirf.send((byte *)&words);  
 
     while(Mirf.isSending())
     { }
 }              
}
