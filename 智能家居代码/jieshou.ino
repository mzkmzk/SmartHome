#include <Mirf.h>
#include <MirfHardwareSpiDriver.h>
#include <MirfSpiDriver.h>
#include <nRF24L01.h>
#include <SPI.h>
#include <IRremote.h>

float dataFl;
int dataInt;
float words;
int shoudong;
IRsend irsend;
int mode;

void setup() {
  Serial.begin(9600);
  Mirf.spi = &MirfHardwareSpi;
  Mirf.init();
  //mode = -1;
  Mirf.setRADDR((byte *)"shoudong");
  Mirf.payload = sizeof(int);
  Mirf.config();
  Serial.println("ready");
  pinMode(6,OUTPUT);
}

void loop() {
  
  if(Mirf.dataReady()) {
     Mirf.getData((byte *)&dataInt);
    
     if(shoudong != dataInt) {
       
          shoudong = dataInt;
          Mirf.rxFifoEmpty();
       Serial.println(shoudong);
       if(shoudong == 1) {//开启温控
            mode = 1;
       } 
       if(shoudong == 2) {//关闭温控
            mode = -1;
       } 
       if(mode == 1) {
          wenkong();
       }
       
       /*if(shoudong == 0 || shoudong == 2) {//制冷/开机/25度/风速高
           fasheCode25();
       } else if(shoudong == 1) {//制冷/开机/26度/风速高
           fasheCode26();
       } else if(shoudong == 3) {//制冷/开机/25度/风速低
           fasheCode25fd();
       } else if(shoudong == 4) {//制冷/开机/25度/风速低/上下扫风
           fasheCode25s();
       } else if(shoudong == 5) {//制冷/开机/25度/风速低/干燥/上下扫风
           fasheCode25g();
       } else if(shoudong == 6) {//制冷/关机/25度/风速低
          fasheCodeOff25();
       }*/
     }

  }
}

void wenkong() {
  Mirf.setRADDR((byte *)"wenkong");
  Mirf.payload = sizeof(float);
  Mirf.config();
  if(Mirf.dataReady()) {
     Mirf.getData((byte *)&dataFl);
     words=dataFl;
     Mirf.rxFifoEmpty();
       if(words >= 30.0) {
         fasheCode25();
       }
       else if(words < 25.0) {
          fasheCodeOff25();
       }
     Serial.println(words);
  }
}


//制冷/开机/25度/风速高
void fasheCode25() {
  Serial.print("SendIR25Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x39, 8);
  sendGree(0x09, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x00, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0xE0, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/开机/20度/风速高
void fasheCode20() {
  Serial.print("SendIR25Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x39, 8);
  sendGree(0x04, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x00, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0x90, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/关机/25度/风速低
void fasheCodeOff25() {
  Serial.print("SendIR25End: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x31, 8);
  sendGree(0x09, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x00, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0x60, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/开机/25度/风速低
void fasheCode25fd() {
  Serial.print("SendIR25Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x59, 8);
  sendGree(0x09, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x00, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0xE0, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/开机/26度/风速高
void fasheCode26() {
  Serial.print("SendIR26Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x39, 8);
  sendGree(0x0A, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x00, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0xF0, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/开机/25度/风速高/干燥/上下扫风
void fasheCode25g() {
  Serial.print("SendIR26Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x59, 8);
  sendGree(0x09, 8);
  sendGree(0xE0, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x01, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0xE0, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

//制冷/开机/25度/风速低/上下扫风
void fasheCode25s() {
  Serial.print("SendIR26Start: ");
  irsend.enableIROut(38);
  sendpresumable();
  sendGree(0x59, 8);
  sendGree(0x09, 8);
  sendGree(0x20, 8);
  sendGree(0x50, 8);
  sendGree(0x02, 3);
  irsend.mark(560);
  irsend.space(10000);
  irsend.space(10000);
  sendGree(0x01, 8);
  sendGree(0x21, 8);
  sendGree(0x00, 8);
  sendGree(0xE0, 8);
  irsend.mark(560);
  irsend.space(0);
  
  delay(3000);
}

void sendpresumable()
{
  irsend.mark(9000);
  irsend.space(4500);
}  

void sendGree(byte ircode, byte len)
{
  byte mask = 0x01;
  for(int i = 0;i < len;i++)
  {
    if (ircode & mask)
    {
      send1();
    }
    else
    {
      send0();
    }
    mask <<= 1;
  }
}

void send0()
{
  irsend.mark(560);
  irsend.space(565);
}
 
void send1()
{
  irsend.mark(560);
  irsend.space(1690);
}

